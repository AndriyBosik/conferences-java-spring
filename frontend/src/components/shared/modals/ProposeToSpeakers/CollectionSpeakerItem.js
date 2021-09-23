import React, { useState } from "react";
import { useMessage } from "../../../../hooks/useMessage";
import { showPopup } from "./../../../../handler/PopupHandler";
import { proposeTopicForSpeaker } from "./../../../../services/ProposalService";
import CircularPreloader from "./../../../CircularPreloader/CircularPreloader";
import { getUserAvatar } from "./../../../../handler/ImageHandler";

function CollectionSpeakerItem({speaker, topic, onSpeakerProposed = () => {}}) {
    const proposeMessage = useMessage("propose");

    const [loading, setLoading] = useState(false);

    const propose = async () => {
        setLoading(true);
        const data = {
            speakerId: speaker.id,
            reportTopicId: topic.id
        };

        const result = await proposeTopicForSpeaker(data);

        if (result) {
            onSpeakerProposed(speaker);
        } else {
            showPopup("error_happened");
        }
        setLoading(false);
    }

    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`${getUserAvatar(speaker.imagePath)}`} alt="" className="circle full-width full-height" />
            </div>
            <span className="title weight-normal s-vflex-center mx10 equal-flex">
                {speaker.name} {speaker.surname}
            </span>
            <span className="secondary-content s-vflex-center">
                {
                    loading ? (
                        <CircularPreloader size="small" />
                    ) : (
                        <button type="submit" className="btn waves-effect waves-light" onClick={propose}>
                            {proposeMessage}
                            <i className="material-icons right">check</i>
                        </button>
                    )
                }
            </span>
        </li>
    );
}

export default CollectionSpeakerItem;