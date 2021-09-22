import axios from "axios";
import { getAccessToken, getUser } from "./../handler/StorageHandler";
import { validate } from "../validators/TopicProposalValidator";

export const getSpeakerProposals = async (speakerId) => {
    return axios.get(`http://localhost:8080/api/proposals/speaker/${speakerId}`, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getForSpeakerProposals = async (speakerId) => {
    return axios.get(`http://localhost:8080/api/proposals/moderator/${speakerId}`, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
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
    return axios.post("http://localhost:8080/api/topic-proposals/create", topicProposal, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return {
            errors: [],
            data: response.data
        }
    });
}

export const getTopicProposals = async () => {
    return axios.get(`http://localhost:8080/api/topic-proposals`, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getProposedTopicsCount = async () => {
    return axios.get(`http://localhost:8080/api/topic-proposals/count`, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const rejectModeratorProposal = async proposalId => {
    return axios.post("http://localhost:8080/api/moderator-proposals/reject", {id: proposalId}, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const acceptModeratorProposal = async reportTopic => {
    const moderatorProposal = {
        id: reportTopic.moderatorProposals[0].id,
        reportTopicId: reportTopic.id,
        speakerId: reportTopic.moderatorProposals[0].speakerId
    };
    return axios.post("http://localhost:8080/api/moderator-proposals/accept", moderatorProposal, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const proposeSpeaker = async speakerProposal => {
    return axios.post("http://localhost:8080/api/speaker-proposals/propose", speakerProposal, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}