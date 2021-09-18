import axios from "axios";
import { getAccessToken } from "./../handler/StorageHandler";

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