import axios from "axios";
import { getAccessToken } from "../handler/StorageHandler";

export const getSpeakers = async () => {
    return axios.get("http://localhost:8080/api/users/speakers", {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getAvailableSpeakers = async (topicId) => {
    return axios.get("http://localhost:8080/api/users/get-topic-available", {
        params: {
            topicId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getSpeakerProposals = async (topicId) => {
    return axios.get("http://localhost:8080/api/users/get-topic-proposed", {
        params: {
            topicId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getMeetingUsers = async (meetingId) => {
    return axios.get("http://localhost:8080/api/users/get-for-meeting", {
        params: {
            meetingId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}