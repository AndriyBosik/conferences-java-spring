import React, { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import { useMessage } from "../../../../hooks/useMessage";
import { getSpeakers } from "../../../../services/UserService";
import { createTopic } from "./../../../../services/TopicService";
import { showPopup } from "./../../../../handler/PopupHandler";
import M from "materialize-css";
import Errors from "../../../Errors/Errors";

function CreateTopicModal({
    meeting,
    modalId,
    onTopicAdded = () => {},
    topic = {
        id: 0
    }
}) {
    const [title, setTitle] = useState("");
    const [speakerId, setSpeakerId] = useState(0);
    const [speakers, setSpeakers] = useState([]);
    const [errors, setErrors] = useState([]);

    const create = async () => {
        const data = {
            meetingId: meeting.id,
            title: title,
            reportTopicSpeaker: speakerId*1 === 0 ? null : {
                speakerId: speakerId
            }
        }

        const result = await createTopic(data);
        setErrors(result.errors);
        if (result.data) {
            onTopicAdded();
            showPopup("topic_was_added");
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