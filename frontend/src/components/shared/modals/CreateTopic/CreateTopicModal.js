import React, { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import { useMessage } from "../../../../hooks/useMessage";
import { getSpeakers } from "../../../../services/UserService";

function CreateTopicModal({
    meeting,
    topic = {
        id: 0
    }
}) {
    const [speakers, setSpeakers] = useState([]);

    const handleSubmit = event => {
        event.preventDefault();
    }

    useEffect(() => {
        const fetchSpeakers = async () => {
            setSpeakers(await getSpeakers());
        }

        fetchSpeakers();
    }, []);

    return (
        <div id="topic-form" className="modal height-70">

            <form id="new-topic-form" method="post" className="s-vflex full-height m0" onSubmit={handleSubmit}>
                <input type="hidden" name="report_topic_speaker_id" value="" />

                <div className="modal-content row full-width">
                    <h5 className="col s12">{useMessage("create_topic")}</h5>

                    <div className="input-field col m6 s12">
                        <input id="topic-title" name="title" type="text" />
                        <label htmlFor="topic-title">{useMessage("title")}</label>
                    </div>

                    <div className="input-field col m6 s12">
                        <select name="speaker_id" className="icons" defaultValue="default">
                            <option value="default">{useMessage("choose_speaker")}</option>
                            {
                                speakers.map(speaker => 
                                    <option key={speaker.id} value={speaker.id} data-icon={`/shared/images/avatars/${speaker.imagePath}`}>
                                        {speaker.name} {speaker.surname}
                                    </option>
                                )
                            }
                        </select>
                    </div>
                </div>
                <div className="modal-footer equal-flex s-vflex-end">
                    <div className="col px20">
                        <button type="submit" className="btn waves-effect waves-light">
                            {useMessage("confirm")}
                            <i className="material-icons right">check</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default CreateTopicModal;