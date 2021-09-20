import React, { useState, useEffect } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { getMeeting } from "./../../services/MeetingService";
import RoleController from "./../RoleController/RoleController";
import UserJoining from "./UserJoining";
import TopicsList from "./TopicsList";
import { getUser } from "./../../handler/StorageHandler";
import MeetingDetails from "./MeetingDetails";
import { initModals, initSelects, initTooltips } from "./../../handler/MaterializeInitializersHandler";
import { isOutdated } from "../../handler/DateHandler";
import ModeratorModals from "./ModeratorModals";
import CreateTopicProposalModal from "./../shared/modals/CreateTopicProposal/CreateTopicProposalModal";
import CircularPreloader from "./../CircularPreloader/CircularPreloader";

function MeetingPage({meetingId}) {
    const user = getUser();

    const editMessage = useMessage("edit");
    const noTopicsMessage = useMessage("no_topics");
    const presentUsersMessage = useMessage("present_users");
    const joinedUsersMessage = useMessage("joined_users");
    const topicsMessage = useMessage("topics");
    const addMessage = useMessage("add");
    const proposeMessage = useMessage("propose");

    const [loading, setLoading] = useState(true);
    const [meeting, setMeeting] = useState({
        id: 0,
        date: [],
        reportTopics: []
    });
    
    const [usersPresence, setUsersPresence] = useState({});
    const [activeTopic, setActiveTopic] = useState({
        id: 0,
        speakerProposals: []
    });

    useEffect(() => {
        const fetchMeeting = async () => {
            setLoading(true);
            const meetingData = await getMeeting(meetingId);
            document.title = meetingData.meeting.title;
            setMeeting(meetingData.meeting);
            setUsersPresence(meetingData.usersPresence);
            initTooltips();
            initModals();
            initSelects();
            setLoading(false);
        };

        fetchMeeting();
    }, [meetingId]);

    return (
        <>
            <div className="container py20">
                <div className="row">
                    {
                        loading ? (
                            <div className="center-align pt50">
                                <CircularPreloader />
                            </div>
                        ) : (
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
                                            <div className="translucent s-hflex fs20 tooltipped" data-position="bottom" data-tooltip={presentUsersMessage + "/" + joinedUsersMessage}>
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
                                    <p className="meeting-title weight-slight m0 pt15 equal-flex">{topicsMessage}</p>

                                    <div className="s-vflex-end">
                                        <RoleController allow={["moderator"]}>
                                            <a href="#topic-form" className="btn waves-effect waves-light modal-trigger createTopicFormTrigger">
                                                {addMessage}
                                                <i className="material-icons right">add</i>
                                            </a>
                                        </RoleController>
                                        <RoleController allow={["speaker"]}>
                                            <a href="#topic-proposal" className="btn waves-effect waves-light modal-trigger">
                                                {proposeMessage}
                                                <i className="material-icons right">local_offer</i>
                                            </a>
                                        </RoleController>
                                    </div>
                                </div>
                                <div className="separator mb10 mt5"></div>

                                {meeting.reportTopics.length > 0 ? (
                                    <TopicsList topics={meeting.reportTopics} user={user} rowClickHandler={setActiveTopic} isOutdated={isOutdated(meeting.date)} />
                                ) : (
                                    <p className="center-align translucent large-text">
                                        {noTopicsMessage}
                                    </p>
                                )}
                            </div>
                        )
                    }
                </div>
            </div>

            <RoleController allow={["moderator"]}>
                <ModeratorModals meeting={meeting} topic={activeTopic} usersPresence={usersPresence} />
            </RoleController>

            <RoleController allow={["speaker"]}>
                <CreateTopicProposalModal meeting={meeting} />
            </RoleController>
        </>
    );
}

export default MeetingPage;