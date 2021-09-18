import React from "react";
import { useMessage } from "../../hooks/useMessage";

function TopicProposal({topic}) {
    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`/shared/images/avatars/${topic.speaker.imagePath}`} alt="" className="circle full-width full-height" />
            </div>
            <div className="collection-content mx20 equal-flex s-vflex-center py10">
                <div className="s-hflex">
                    <span>{useMessage("speaker")}</span>
                    <span className="topic-title weight-normal px5">{topic.speaker.name} {topic.speaker.surname}</span>
                    <span className="lowercase">{useMessage("speaker_proposed_topic")}</span>
                    <span className="topic-title weight-normal px5">{topic.topicTitle}</span>
                </div>
            </div>
            <div className="secondary-content s-vflex-center">
                <div className="s-hflex">
                    <form  method="post" className="m0">
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
            </div>
        </li>
    );
}

export default TopicProposal;