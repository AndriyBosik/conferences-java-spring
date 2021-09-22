import React, { useState, useEffect } from "react";
import CreateTopicModal from "./../shared/modals/CreateTopic/CreateTopicModal";
import ProposeToSpeakersModal from "../shared/modals/ProposeToSpeakers/ProposeToSpeakersModal";
import SpeakerProposalsModal from "../shared/modals/SpeakerProposals/SpeakerProposalsModal";
import PresenceEditorModal from "./../shared/modals/PresenceEditor/PresenceEditorModal";
import { getSpeakerProposals, getMeetingUsers } from "./../../services/UserService";

function ModeratorModals({
    meeting,
    topic,
    usersPresence,
    onPresenceChanged = () => {},
    createTopicModalId,
    onTopicAdded = () => {},
    onTopicChanged = () => {}
}) {
    const [speakerProposals, setSpeakerProposals] = useState([]);
    const [meetingUsers, setMeetingUsers] = useState([]);

    useEffect(() => {
        const fetchMeetingUsers = async () => {
            if (typeof meeting.id !== "undefined") {
                setMeetingUsers(await getMeetingUsers(meeting.id));
            }
        }

        fetchMeetingUsers();
    }, [meeting]);

    useEffect(() => {
        const fetchSpeakerProposals = async () => {
            setSpeakerProposals(await getSpeakerProposals(topic.id));
        }

        fetchSpeakerProposals();
    }, [topic]);

    return (
        <>
            <CreateTopicModal modalId={createTopicModalId} meeting={meeting} onTopicAdded={onTopicAdded} onTopicChanged={onTopicChanged} topic={topic} />
            <ProposeToSpeakersModal topic={topic} />
            <SpeakerProposalsModal speakerProposals={speakerProposals} />
            {
                usersPresence.usersCount > 0 ? (
                    <PresenceEditorModal meetingUsers={meetingUsers} onPresenceChanged={onPresenceChanged} />
                ) : null
            }
        </>
    );
}

export default ModeratorModals;