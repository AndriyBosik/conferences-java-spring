import React, { useEffect, useState } from "react";
import { useMessage } from "../../hooks/useMessage";
import { getSpeakerProposedTopicsIds } from "./../../services/TopicService";

function SpeakerProposalForm({meetingId, topic, userId}) {
    const youProposedYourselfMessage = useMessage("you_proposed_yourself");
    const proposeMeMessage = useMessage("propose_me");

    const [proposedTopicIds, setProposedTopicIds] = useState([]);

    useEffect(() => {
        const fetchProposedTopicIds = async () => {
            const ids = await getSpeakerProposedTopicsIds(userId, meetingId);
            setProposedTopicIds(ids);
        }

        fetchProposedTopicIds();
    }, [userId, meetingId]);

    const handleSubmit = event => {
        event.preventDefault();

        const data = {
            meetingId: meetingId,
            reportTopicId: topic.id
        };
    }

    return (
        (typeof topic !== "undefined" && proposedTopicIds.includes(topic.id)) ? (
            <span className="grey-text text-lighten-1 weight-normal">
                {youProposedYourselfMessage}
            </span>
        ) : (
            <form method="post" className="m0" onSubmit={handleSubmit}>
                <button type="submit" className="btn-floating orange tooltipped" data-position="left" data-tooltip={proposeMeMessage}>
                    <i className="material-icons">assignment_ind</i>
                </button>
            </form>
        )
    );
}

export default SpeakerProposalForm;