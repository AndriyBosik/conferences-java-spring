import React from "react";
import { useMessage } from "../../../../hooks/useMessage";
import SpeakerProposal from "./SpeakerProposal";

function SpeakerProposalsModal({speakerProposals}) {
    return (
        <div id="topic-proposals-form" className="modal bottom-sheet">
            <div className="modal-content row">
                <h4 className="col s12">{useMessage("proposals")}</h4>
                <div id="speakerProposals">
                    {
                        speakerProposals.map(speakerProposal => <SpeakerProposal key={speakerProposal.id} speakerProposal={speakerProposal} />)
                    }
                </div>
            </div>
        </div>
    );
}

export default SpeakerProposalsModal;