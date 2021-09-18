import React from "react";
import { useMessage } from "../../hooks/useMessage";

function SpeakerProposalItem({proposal}) {
    return (
        <li className="collection-item s-hflex">
            <div className="collection-content mx20 equal-flex s-vflex-center py10">
                <span className="topic-title weight-normal">{proposal.title}</span>
            </div>
            <span className="secondary-content s-vflex-center">
                <i className="material-icons small blue-text text-darken-3 tooltipped" data-position="left" data-tooltip={useMessage("processing")}>hourglass_bottom</i>
            </span>
        </li>
    );
}

export default SpeakerProposalItem;