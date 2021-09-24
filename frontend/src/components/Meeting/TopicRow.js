import React from "react";
import { useMessage } from "../../hooks/useMessage";
import RoleController from "../RoleController/RoleController";
import SpeakerProposalForm from "./SpeakerProposalForm";
import { getUserAvatar } from "./../../handler/ImageHandler";

function TopicRow({topic, order, user, rowClickHandler, isOutdated, topicModalId}) {
    const noSpeakerMessage = useMessage("no_speaker");
    const selectFromProposalsMessage = useMessage("select_from_proposals");
    const proposeToSpeakersMessage = useMessage("propose_to_speakers");

    return (
        <tr className="topic-item" onClick={() => {
            if (user.role === "moderator") {
                rowClickHandler(topic);
            }
        }}>
            <td className="center-align">{order}</td>
            <td>
                {
                    topic.reportTopicSpeaker == null ? (
                        <div className="red-text weight-strong s-hflex">
                            <span className="px10 s-vflex-center">
                                {noSpeakerMessage}
                            </span>

                            <RoleController allow={["speaker"]}>
                                <SpeakerProposalForm meetingId={topic.meetingId} userId={user.id} topic={topic} />
                            </RoleController>

                            <RoleController allow={["moderator"]}>
                                <a href="#topic-proposals-form" className="modal-trigger proposalsSearchTrigger weight-slight" onClick={() => rowClickHandler(topic)}>
                                    ({selectFromProposalsMessage})
                                </a>
                            </RoleController>
                        </div>
                    ) : (
                        <div className="s-hflex">
                            <div className="z-depth-1 user-avatar stretch-background">
                                <img src={`${getUserAvatar(topic.reportTopicSpeaker.speaker.imagePath)}`} alt="" className="full-width full-height" />
                            </div>
                            <div className="s-vflex-center px10 weight-normal">
                                {topic.reportTopicSpeaker.speaker.name} {topic.reportTopicSpeaker.speaker.surname}
                            </div>
                        </div>
                    )
                }
            </td>
            <td>
                {topic.title}
            </td>
            <RoleController allow={["moderator"]}>
                <td className="px20">
                    <div className="s-hflex-end">
                        {
                            (topic.reportTopicSpeaker == null && !isOutdated) ? (
                                <span className="clickable proposalTrigger modal-trigger tooltipped px5" data-target="propose-to-speakers-form" data-position="bottom" data-tooltip={proposeToSpeakersMessage} onClick={() => rowClickHandler(topic)}>
                                    <i className="material-icons orange-text">person_add</i>
                                </span>
                            ) : null
                        }
                        <span className="clickable topicTrigger modal-trigger blue-text text-darken-3 tooltipped" data-target={topicModalId} data-position="bottom" data-tooltip={useMessage("edit")}>
                            <i className="material-icons">edit</i>
                        </span>
                    </div>
                </td>
            </RoleController>
        </tr>
    );
}

export default TopicRow;