import React from "react";
import { useMessage } from "../../../../hooks/useMessage";
import SpeakerProposal from "./SpeakerProposal";
import M from "materialize-css";

function SpeakerProposalsModal({speakerProposals, onSpeakerSelected, topic}) {
    const speakerSelected = () => {
        M.Modal.getInstance(document.getElementById("topic-proposals-form")).close();
        onSpeakerSelected();
    }

    return (
        <div id="topic-proposals-form" className="modal bottom-sheet">
            <div className="modal-content row">
                <h4 className="col s12">{useMessage("proposals")}</h4>
                <div id="speakerProposals">
                    {
                        speakerProposals.map(speakerProposal => <SpeakerProposal key={speakerProposal.id} speakerProposal={speakerProposal} onSpeakerSelected={speakerSelected} topic={topic} />)
                    }
                </div>
            </div>
        </div>
    );
}

export default SpeakerProposalsModal;