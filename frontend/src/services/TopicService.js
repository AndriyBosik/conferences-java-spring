import axios from "axios";
import { getAccessToken } from "./../handler/StorageHandler";
import { validate } from "./../validators/ReportTopicValidator";

export const getSpeakerProposedTopicsIds = async (speakerId, meetingId) => {
    return axios.get("http://localhost:8080/api/topics/get-speaker-proposed-topic-ids", {
        params: {
            speakerId: speakerId,
            meetingId: meetingId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const createFromProposal = async topicProposalId => {
    return axios.post("http://localhost:8080/api/topics/create-from-proposal", {id: topicProposalId}, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const rejectTopicProposal = async topicProposalId => {
    return axios.post("http://localhost:8080/api/topic-proposals/reject", {id: topicProposalId}, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const createTopic = async topic => {
    const errors = validate(topic);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    return axios.post("http://localhost:8080/api/topics/create", topic, {
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

export const editTopic = async topic => {
    const errors = validate(topic);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    return axios.post("http://localhost:8080/api/topics/edit", topic, {
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

export const getTopicsByMeetingId = async meetingId => {
    return axios.get("http://localhost:8080/api/topics/get-by-meeting", {
        params: {
            meetingId: meetingId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data
    });
}