import React, { useState } from "react";
import { useMessage } from "./../../../../hooks/useMessage";
import { createTopicProposal } from "./../../../../services/ProposalService";
import M from "materialize-css";
import { getMessage } from "../../../../handler/MessageHanlder";
import Errors from "../../../Errors/Errors";

function CreateTopicProposalModal({meeting}) {
    const [topicTitle, setTopicTitle] = useState("");
    const [errors, setErrors] = useState([]);

    const handleProposal = async () => {
        const result = await createTopicProposal({
            meetingId: meeting.id,
            topicTitle: topicTitle
        });

        setErrors(result.errors);

        if (result.errors.length === 0) {
            if (result.data) {
                setTopicTitle("");
                const modal = M.Modal.getInstance(document.getElementById("topic-proposal"));
                modal.close();
                M.toast({
                    html: getMessage("topic_proposal_was_created")
                });
            } else {
                M.toast({
                    html: getMessage("error_happened")
                });
            }
        }
    }

    return (
        <div id="topic-proposal" className="modal">
            <div className="modal-content row full-width" style={{marginBottom: 0}}>
                <h5 className="col s12">{useMessage("propose_topic")}</h5>
                <Errors errors={errors} containerClass="col s12 py10" />
                <div className="input-field col s12">
                    <input id="topic-title" type="text" value={topicTitle} onChange={event => setTopicTitle(event.target.value)} />
                    <label htmlFor="topic-title">{useMessage("title")}</label>
                </div>
            </div>
            <div className="modal-footer equal-flex s-vflex-end">
                <div className="col px20">
                    <button type="button" className="btn waves-effect waves-light" onClick={handleProposal}>
                        {useMessage("propose")}
                        <i className="material-icons right">local_offer</i>
                    </button>
                </div>
            </div>
        </div>
    );
}

export default CreateTopicProposalModal;