import axios from "axios";
import { getAccessToken } from "./../handler/StorageHandler";

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