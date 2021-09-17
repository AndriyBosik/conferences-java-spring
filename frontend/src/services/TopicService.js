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