import { CREATE_TOPIC_FROM_PROPOSAL_URL, CREATE_TOPIC_URL, EDIT_TOPIC_URL, GET_SPEAKER_PROPOSED_TOPIC_IDS_URL, GET_TOPICS_BY_MEETING_ID_URL, REJECT_TOPIC_PROPOSAL_URL } from "../constants/network";
import { doGet, doPost } from "../handler/AuthRequestHandler";
import { validate } from "./../validators/ReportTopicValidator";

export const getSpeakerProposedTopicsIds = async (speakerId, meetingId) => {
    return doGet(GET_SPEAKER_PROPOSED_TOPIC_IDS_URL, {
        params: {
            speakerId: speakerId,
            meetingId: meetingId
        }
    }, response => response.data, () => []);
}

export const createFromProposal = async topicProposalId => {
    return doPost(CREATE_TOPIC_FROM_PROPOSAL_URL, {id: topicProposalId}, {}, response => response.data, () => false);
}

export const rejectTopicProposal = async topicProposalId => {
    return doPost(REJECT_TOPIC_PROPOSAL_URL, {id: topicProposalId}, {}, response => response.data, () => false);
}

export const createTopic = async topic => {
    const errors = validate(topic);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    return doPost(CREATE_TOPIC_URL, topic, {}, response => ({
        errors: [],
        data: response.data
    }), () => ({
        errors: [],
        data: false
    }));
}

export const editTopic = async topic => {
    const errors = validate(topic);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    return doPost(EDIT_TOPIC_URL, topic, {}, response => ({
        errors: [],
        data: response.data
    }), () => ({
        errors: [],
        data: false
    }));
}

export const getTopicsByMeetingId = async meetingId => {
    return doGet(GET_TOPICS_BY_MEETING_ID_URL, {
        params: {
            meetingId: meetingId
        }
    }, response => response.data, () => []);
}