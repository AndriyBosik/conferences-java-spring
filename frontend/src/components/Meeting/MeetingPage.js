import React, { useState, useEffect } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { getMeeting } from "./../../services/MeetingService";
import RoleController from "./../RoleController/RoleController";
import UserJoining from "./UserJoining";
import TopicsList from "./TopicsList";
import { getUser } from "./../../handler/StorageHandler";
import MeetingDetails from "./MeetingDetails";
import CreateTopicModal from "../shared/modals/CreateTopicModal";
import ProposeToSpeakersModal from "../shared/modals/ProposeToSpeakersModal/ProposeToSpeakersModal";
import SpeakerProposalsModal from "../shared/modals/SpeakerProposalsModal";
import { initModals, initSelects, initTooltips } from "./../../handler/MaterializeInitializersHandler";
import { isOutdated } from "../../handler/DateHandler";
import PresenceEditorModal from "../shared/modals/PresenceEditor/PresenceEditorModal";
import CreateTopicProposalModal from "../shared/modals/CreateTopicProposal/CreateTopicProposalModal";

function MeetingPage({meetingId}) {
    const user = getUser();

    const editMessage = useMessage("edit");
    const noTopicsMessage = useMessage("no_topics");

    const [meeting, setMeeting] = useState({
        date: [],
        reportTopics: []
    });
    const [usersPresence, setUsersPresence] = useState({});
    const [activeTopicId, setActiveTopicId] = useState(0);

    useEffect(() => {
        const fetchMeeting = async () => {
            const meetingData = await getMeeting(meetingId);
            document.title = meetingData.meeting.title;
            setMeeting(meetingData.meeting);
            setUsersPresence(meetingData.usersPresence);
            initTooltips();
            initModals();
            initSelects();
        };

        fetchMeeting();
    }, [meetingId]);

    return (
        <>
            <div className="container py20">
                <div className="row">
                    <div className="meeting-container s-vflex">
                        <div className="s-hflex full-width">
                            <p className="meeting-title weight-slight m0 equal-flex truncate">{meeting.title}</p>
                            <RoleController allow={["user"]}>
                                <UserJoining meeting={meeting} user={user} />
                            </RoleController>
                        </div>

                        <div className="separator mb10"></div>

                        <RoleController allow={["moderator"]}>
                            <div className="full-width s-vflex my15">
                                <div className="s-hflex-end">
                                    <div className="equal-flex s-hflex-start">
                                        {
                                            usersPresence.usersCount > 0 ? (
                                                <button type="button" className="btn btn-small waves-effect waves-light blue darken-3 modal-trigger" data-target="presence-editor">
                                                    {editMessage}
                                                    <i className="material-icons right">edit</i>
                                                </button>
                                            ) : null
                                        }
                                    </div>
                                    <div className="translucent s-hflex fs20 tooltipped" data-position="bottom" data-tooltip={useMessage("present_users") + "/" + useMessage("joined_users")}>
                                        <span className="s-vflex-end px5">
                                            <i className="material-icons fs30">people</i>
                                        </span>
                                        <span className="s-vflex-end">{usersPresence.presentUsersCount}/{usersPresence.usersCount}</span>
                                    </div>
                                </div>
                                <div className="progress">
                                    <div className="determinate blue darken-3" style={{
                                        width: (usersPresence.presentUsersCount / usersPresence.usersCount)*100 + "%"
                                    }}></div>
                                </div>
                            </div>
                        </RoleController>

                        <MeetingDetails meeting={meeting} usersPresence={usersPresence} />

                        <p className="meeting-description translucent-2p m0">
                            {meeting.description}
                        </p>

                        <div className="s-hflex">
                            <p className="meeting-title weight-slight m0 pt15 equal-flex">{useMessage("topics")}</p>

                            <div className="s-vflex-end">
                                <RoleController allow={["moderator"]}>
                                    <a href="#topic-form" className="btn waves-effect waves-light modal-trigger createTopicFormTrigger">
                                        {useMessage("add")}
                                        <i className="material-icons right">add</i>
                                    </a>
                                </RoleController>
                                <RoleController allow={["speaker"]}>
                                    <a href="#topic-proposal" className="btn waves-effect waves-light modal-trigger">
                                        {useMessage("propose")}
                                        <i className="material-icons right">local_offer</i>
                                    </a>
                                </RoleController>
                            </div>
                        </div>
                        <div className="separator mb10 mt5"></div>

                        {meeting.reportTopics.length > 0 ? (
                            <TopicsList topics={meeting.reportTopics} user={user} rowClickHandler={setActiveTopicId} isOutdated={isOutdated(meeting.date)} />
                        ) : (
                            <p className="center-align translucent large-text">
                                {noTopicsMessage}
                            </p>
                        )}
                    </div>
                </div>
            </div>

            <RoleController allow={["moderator"]}>
                <CreateTopicModal meeting={meeting} />
                <ProposeToSpeakersModal topicId={activeTopicId} />
                <SpeakerProposalsModal />
                {
                    usersPresence.usersCount > 0 ? (
                        <PresenceEditorModal />
                    ) : null
                }
            </RoleController>

            <RoleController allow={["speaker"]}>
                <CreateTopicProposalModal />
            </RoleController>
        </>
    );
}

export default MeetingPage;