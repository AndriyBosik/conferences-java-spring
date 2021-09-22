import React, { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import { useMessage } from "../../../../hooks/useMessage";
import { getSpeakers } from "../../../../services/UserService";
import { createTopic, editTopic } from "./../../../../services/TopicService";
import { showPopup } from "./../../../../handler/PopupHandler";
import M from "materialize-css";
import Errors from "../../../Errors/Errors";
import { initInputs, initSelects } from "../../../../handler/MaterializeInitializersHandler";

function CreateTopicModal({
    meeting,
    modalId,
    onTopicAdded = () => {},
    onTopicChanged = () => {},
    topic = {
        id: 0,
        title: ""
    }
}) {
    const [title, setTitle] = useState(topic.title ? topic.title : "");
    const [speakerId, setSpeakerId] = useState(topic.reportTopicSpeaker ? topic.reportTopicSpeaker.speakerId : 0);
    const [speakers, setSpeakers] = useState([]);
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        setTitle(topic.title ? topic.title : "");
        setSpeakerId(topic.reportTopicSpeaker ? topic.reportTopicSpeaker.speakerId : 0);
    }, [topic]);

    useEffect(() => {
        initSelects();
        initInputs();
    }, [speakerId]);

    const create = async () => {
        const data = {
            id: topic.id*1,
            meetingId: meeting.id,
            title: title,
            reportTopicSpeaker: speakerId*1 === 0 ? null : {
                speakerId: speakerId
            }
        }

        const result = data.id === 0 ? await createTopic(data) : await editTopic(data);
        setErrors(result.errors);
        if (result.data) {
            if (data.id === 0) {
                onTopicAdded();
            } else {
                onTopicChanged();
            }
            showPopup(data.id === 0 ? "topic_was_added" : "topic_was_edited");
            M.Modal.getInstance(document.getElementById(modalId)).close();
        } else {
            showPopup("error_happened");
        }
    }

    useEffect(() => {
        const fetchSpeakers = async () => {
            setSpeakers(await getSpeakers());
        }

        fetchSpeakers();
    }, []);

    return (
        <div id={modalId} className="modal height-70">
            <div className="modal-content row full-width">
                <h5 className="col s12">{useMessage("create_topic")}</h5>

                <Errors errors={errors} containerClass="col s12" />

                <div className="input-field col m6 s12">
                    <input id="topic-title" type="text" value={title} onChange={event => setTitle(event.target.value)} />
                    <label htmlFor="topic-title">{useMessage("title")}</label>
                </div>

                <div className="input-field col m6 s12">
                    <select name="speaker_id" className="icons" value={speakerId} onChange={event => setSpeakerId(event.target.value)}>
                        <option value="0">{useMessage("choose_speaker")}</option>
                        {
                            speakers.map(speaker => 
                                <option key={speaker.id} value={speaker.id} data-icon={`http://localhost:8080/api/images/avatars/${speaker.imagePath}`}>
                                    {speaker.name} {speaker.surname}
                                </option>
                            )
                        }
                    </select>
                </div>
            </div>
            <div className="modal-footer equal-flex s-vflex-end">
                <div className="col px20">
                    <button type="button" className="btn waves-effect waves-light" onClick={create}>
                        {useMessage("confirm")}
                        <i className="material-icons right">check</i>
                    </button>
                </div>
            </div>
        </div>
    );
}

export default CreateTopicModal;