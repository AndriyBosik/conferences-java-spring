import React, { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import { useMessage } from "../../../../hooks/useMessage";
import { getAvailableSpeakers } from "../../../../services/UserService";
import CollectionSpeakerItem from "./CollectionSpeakerItem";

function ProposeToSpeakersModal({topicId}) {
    const [availableSpeakers, setAvailableSpeakers] = useState([]);

    useEffect(() => {
        const fetchAvailableSpeakers = async () => {
            setAvailableSpeakers(await getAvailableSpeakers(topicId));
        }

        fetchAvailableSpeakers();
    }, [topicId]);

    return (
        <div id="propose-to-speakers-form" className="modal height-70">

            <div className="proposalSendingPreloader progress hidden">
                <div className="indeterminate"></div>
            </div>

            <div className="modal-content row full-width">
                <h5 className="col s12">{useMessage("propose_to_speakers")}</h5>
                <div className="col s12">
                    <ul id="availableSpeakersCollection" className="collection">
                        {
                            availableSpeakers.map(speaker => <CollectionSpeakerItem />)
                        }
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default ProposeToSpeakersModal;