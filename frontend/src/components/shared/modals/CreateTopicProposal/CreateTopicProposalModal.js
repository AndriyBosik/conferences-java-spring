import React from "react";
import { useMessage } from "./../../../../hooks/useMessage";

function CreateTopicProposalModal() {
    return (
        <div id="topic-proposal" className="modal">
            <form id="add-topic-proposal-form" method="post" className="s-vflex full-height m0">
                <div className="modal-content row full-width" style={{marginBotton: 0}}>
                    <h5 className="col s12">{useMessage("propose_topic")}</h5>

                    <div className="input-field col s12">
                        <input id="topic-title" name="topic_title" type="text" />
                        <label htmlFor="topic-title">{useMessage("title")}</label>
                    </div>
                </div>
                <div className="modal-footer equal-flex s-vflex-end">
                    <div className="col px20">
                        <button type="submit" className="btn waves-effect waves-light">
                            {useMessage("propose")}
                            <i className="material-icons right">local_offer</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default CreateTopicProposalModal;