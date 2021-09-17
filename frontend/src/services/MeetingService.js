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

export const getMeeting = async meetingId => {
    return axios.get(`http://localhost:8080/api/meetings/${meetingId}`, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const checkUserJoined = async (userId, meetingId) => {
    return axios.get("http://localhost:8080/api/meetings/check-user-joined", {
        params: {
            id: 0,
            present: true,
            userId: userId,
            meetingId: meetingId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}