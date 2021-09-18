import React from "react";
import { useMessage } from "../../hooks/useMessage";

function ModeratorProposalItem({proposal}) {
    return (
        <li className="collection-item s-hflex">
            <div className="collection-content mx20 equal-flex s-vflex-center py10">
                <span className="topic-title weight-normal">{proposal.title}</span>
            </div>
            <span className="secondary-content s-vflex-center">
                <div className="s-hflex">
                    <form method="post" className="m0">
                        <button type="submit" className="btn waves-effect waves-light mx10">
                            <span>{useMessage("accept")}</span>
                            <i className="material-icons right">check</i>
                        </button>
                    </form>
                    <form method="post" className="m0">
                        <button type="submit" className="btn waves-effect waves-light red">
                            <span>{useMessage("reject")}</span>
                            <i className="material-icons right">clear</i>
                        </button>
                    </form>
                </div>
            </span>
        </li>
    );
}

export default ModeratorProposalItem;