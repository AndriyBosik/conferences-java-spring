import React, { useEffect, useState } from "react";
import { useMessage } from "../../hooks/useMessage";
import { getSpeakerProposedTopicsIds } from "./../../services/TopicService";
import { proposeSpeaker } from "./../../services/ProposalService";
import { showPopup } from "../../handler/PopupHandler";
import CircularPreloader from "./../CircularPreloader/CircularPreloader";

function SpeakerProposalForm({
    meetingId,
    topic,
    userId
}) {
    const youProposedYourselfMessage = useMessage("you_proposed_yourself");
    const proposeMeMessage = useMessage("propose_me");

    const [proposedTopicIds, setProposedTopicIds] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchProposedTopicIds = async () => {
            setProposedTopicIds(await getSpeakerProposedTopicsIds(userId, meetingId));
        }

        fetchProposedTopicIds();
    }, [userId, meetingId]);

    const propose = async () => {
        setLoading(true);
        const data = {
            reportTopicId: topic.id
        };

        const result = await proposeSpeaker(data);

        if (!result) {
            showPopup("error_happened");
        } else {
            setProposedTopicIds(await getSpeakerProposedTopicsIds(userId, meetingId));
        }
        setLoading(false);
    }

    return (
        (typeof topic !== "undefined" && proposedTopicIds.includes(topic.id)) ? (
            <span className="grey-text text-lighten-1 weight-normal">
                {youProposedYourselfMessage}
            </span>
        ) : (
            loading ? (
                <CircularPreloader size="small" />
            ) : (
                <button type="button" className="btn-floating orange tooltipped" data-position="left" data-tooltip={proposeMeMessage} onClick={propose}>
                    <i className="material-icons">assignment_ind</i>
                </button>
            )
        )
    );
}

export default SpeakerProposalForm;