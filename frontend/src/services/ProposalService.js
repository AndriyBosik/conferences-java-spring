import { getUser } from "./../handler/StorageHandler";
import { validate } from "../validators/TopicProposalValidator";
import { ACCEPT_MODERATOR_PROPOSAL_URL, CREATE_TOPIC_PROPOSAL_URL, GET_MODERATOR_PRPOSALS_URL, GET_TOPICS_WITH_SPEAKER_PROPOSALS_URL, GET_TOPIC_PROPOSALS_COUNT_URL, GET_TOPIC_PROPOSALS_URL, MAKE_SPEAKER_PROPOSAL, PROPOSE_TOPIC_FOR_SPEAKER_URL, REJECT_TOPIC_PROPOSAL_URL } from "../constants/network";
import { format } from "./../handler/StringHandler";
import { doGet, doPost } from "../handler/AuthRequestHandler";

export const getSpeakerProposals = async (speakerId) => {
    const url = format(GET_TOPICS_WITH_SPEAKER_PROPOSALS_URL, {speakerId: speakerId});
    return doGet(url, {}, response => response.data, () => []);
}

export const getForSpeakerProposals = async (speakerId) => {
    const url = format(GET_MODERATOR_PRPOSALS_URL, {speakerId: speakerId});
    return doGet(url, {}, response => response.data, () => []);
}

export const createTopicProposal = async topicProposal => {
    const errors = validate(topicProposal);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    topicProposal.speakerId = getUser().id;
    return doPost(CREATE_TOPIC_PROPOSAL_URL, topicProposal, {}, response => ({
        errors: [],
        data: response.data
    }), () => ({
        errors: [],
        data: []
    }));
}

export const getTopicProposals = async () => {
    return doGet(GET_TOPIC_PROPOSALS_URL, {}, response => response.data, () => []);
}

export const getProposedTopicsCount = async () => {
    return doGet(GET_TOPIC_PROPOSALS_COUNT_URL, {}, response => response.data, () => 0);
}

export const rejectModeratorProposal = async proposalId => {
    return doPost(REJECT_TOPIC_PROPOSAL_URL, {id: proposalId}, {}, response => response.data, () => false);
}

export const acceptModeratorProposal = async reportTopic => {
    const moderatorProposal = {
        id: reportTopic.moderatorProposals[0].id,
        reportTopicId: reportTopic.id,
        speakerId: getUser().id
    };
    return doPost(ACCEPT_MODERATOR_PROPOSAL_URL, moderatorProposal, {}, response => response.data, () => false);
}

export const proposeSpeaker = async speakerProposal => {
    return doPost(MAKE_SPEAKER_PROPOSAL, speakerProposal, {}, response => response.data, () => false);
}

export const proposeTopicForSpeaker = async moderatorProposal => {
    return doPost(PROPOSE_TOPIC_FOR_SPEAKER_URL, moderatorProposal, {}, response => response.data, () => false);
}