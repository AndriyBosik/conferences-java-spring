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
    /* TODO */
    console.log(topicId);
    return [];
}