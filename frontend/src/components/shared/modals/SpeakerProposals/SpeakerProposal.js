import React from "react";
import { showPopup } from "../../../../handler/PopupHandler";
import { selectSpeakerForTopic } from "../../../../services/UserService";
import { getUserAvatar } from "./../../../../handler/ImageHandler";

function SpeakerProposal({speakerProposal, topic, onSpeakerSelected = () => {}}) {
    const selectSpeaker = async () => {
        const data = {
            speakerId: speakerProposal.id,
            reportTopicId: topic.id
        }
        
        const result = await selectSpeakerForTopic(data);
        
        if (result) {
            onSpeakerSelected();
        } else {
            showPopup("error_happened");
        }
    }

    return (
        <div className="col s6 m3 my5">
            <div className="proposal full-width clickable" onClick={selectSpeaker}>
                <div className="z-depth-1">
                    <div className="s-hflex">
                        <div className="proposal-avatar">
                            <img src={`${getUserAvatar(speakerProposal.imagePath)}`} alt="" className="full-width full-height" />
                        </div>
                        <span className="username s-vflex-center mx10 weight-strong">{speakerProposal.name} {speakerProposal.surname}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SpeakerProposal;