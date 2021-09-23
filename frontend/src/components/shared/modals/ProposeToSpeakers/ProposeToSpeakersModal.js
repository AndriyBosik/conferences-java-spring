import React, { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import { useMessage } from "../../../../hooks/useMessage";
import { getAvailableSpeakers } from "../../../../services/UserService";
import CollectionSpeakerItem from "./CollectionSpeakerItem";

function ProposeToSpeakersModal({topic}) {
    const [availableSpeakers, setAvailableSpeakers] = useState([]);

    useEffect(() => {
        const fetchAvailableSpeakers = async () => {
            setAvailableSpeakers(await getAvailableSpeakers(topic.id));
        }

        fetchAvailableSpeakers();
    }, [topic]);

    const onSpeakerProposed = proposedSpeaker => {
        setAvailableSpeakers(availableSpeakers => {
            return availableSpeakers.filter(speaker => speaker.id*1 !== proposedSpeaker.id);
        });
    }

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
                            availableSpeakers.map(speaker => <CollectionSpeakerItem key={speaker.id} speaker={speaker} topic={topic} onSpeakerProposed={onSpeakerProposed} />)
                        }
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default ProposeToSpeakersModal;