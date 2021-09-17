import axios from "axios";
import { getAccessToken } from "./../handler/StorageHandler";

export const getAllMeetings = async (page, items, filters) => {
    return axios.get(`http://localhost:8080/api/meetings/page/${page}/${items}`, {
        params: filters,
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
};