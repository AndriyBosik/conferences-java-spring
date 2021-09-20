import React from "react";
import { useMessage } from "../../hooks/useMessage";
import { createFromProposal, rejectTopicProposal } from "./../../services/TopicService";

function TopicProposal({
    topicProposal,
    successAcceptanceCallback = () => {},
    successRejectionCallback = () => {}
}) {
    const acceptProposal = async (event, topicProposal) => {
        event.preventDefault();
        
        const status = await createFromProposal(topicProposal.id);
        if (status) {
            successAcceptanceCallback(topicProposal);
        }
    }

    const rejectProposal = async topicProposal => {
        const status = await rejectTopicProposal(topicProposal.id);
        if (status) {
            successRejectionCallback(topicProposal);
        }
    }

    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`/shared/images/avatars/${topicProposal.speaker.imagePath}`} alt="" className="circle full-width full-height" />
            </div>
            <div className="collection-content mx20 equal-flex s-vflex-center py10">
                <div className="s-hflex">
                    <span>{useMessage("speaker")}</span>
                    <span className="topic-title weight-normal px5">{topicProposal.speaker.name} {topicProposal.speaker.surname}</span>
                    <span className="lowercase">{useMessage("speaker_proposed_topic")}</span>
                    <span className="topic-title weight-normal px5">{topicProposal.topicTitle}</span>
                </div>
            </div>
            <div className="secondary-content s-vflex-center">
                <div className="s-hflex">
                    <form method="post" onSubmit={event => acceptProposal(event, topicProposal)}>
                        <button type="submit" className="btn waves-effect waves-light mx10">
                            <span>{useMessage("accept")}</span>
                            <i className="material-icons right">check</i>
                        </button>
                    </form>
                    <button type="button" className="btn waves-effect waves-light red" onClick={() => rejectProposal(topicProposal)}>
                        <span>{useMessage("reject")}</span>
                        <i className="material-icons right">clear</i>
                    </button>
                </div>
            </div>
        </li>
    );
}

export default TopicProposal;