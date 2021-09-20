import React from "react";
import { useMessage } from "../../hooks/useMessage";
import { acceptModeratorProposal, rejectModeratorProposal } from "./../../services/ProposalService";
import M from "materialize-css";
import { getMessage } from "../../handler/MessageHanlder";

function ModeratorProposalItem({
    reportTopic,
    successfulRejectionCallback = () => {},
    successfulAcceptanceCallback = () => {}
}) {
    const reject = async () => {
        const result = await rejectModeratorProposal(reportTopic.moderatorProposals[0].id);
        processResult(result, "proposal_was_rejected", "error_happened", successfulRejectionCallback, reportTopic)
    }

    const accept = async () => {
        const result = await acceptModeratorProposal(reportTopic);
        processResult(result, "proposal_was_accepted", "error_happened", successfulAcceptanceCallback, reportTopic);
    }

    return (
        <li className="collection-item s-hflex">
            <div className="collection-content mx20 equal-flex s-vflex-center py10">
                <span className="topic-title weight-normal">{reportTopic.title}</span>
            </div>
            <span className="secondary-content s-vflex-center">
                <div className="s-hflex">
                    <button type="button" className="btn waves-effect waves-light mx10" onClick={accept}>
                        <span>{useMessage("accept")}</span>
                        <i className="material-icons right">check</i>
                    </button>
                    <button type="button" className="btn waves-effect waves-light red" onClick={reject}>
                        <span>{useMessage("reject")}</span>
                        <i className="material-icons right">clear</i>
                    </button>
                </div>
            </span>
        </li>
    );
}

export default ModeratorProposalItem;

const processResult = (result, successMessageAlias, errorMessageAlias, callback, reportTopic) => {
    if (result) {
        M.toast({
            html: getMessage(successMessageAlias)
        });

        callback(reportTopic);
    } else {
        M.toast({
            html: getMessage(errorMessageAlias)
        });
    }
}