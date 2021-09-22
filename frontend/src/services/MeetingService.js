import axios from "axios";
import { getAccessToken } from "./../handler/StorageHandler";
import { validate } from "./../validators/MeetingValidator";
import { validate as validateMeetingUpdatableData } from "./../validators/MeetingUpdatableDataValidator";

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

export const getAllMeetingsForSpeaker = speakerId => async (page, items, filters) => {
    return axios.get(`http://localhost:8080/api/meetings/speaker/${page}/${items}`, {
        params: {
            ...filters,
            speakerId
        },
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

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

export const createMeeting = (file, data) => {
    const errors = validate(data);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false
        };
    }
    const formData = new FormData();
    formData.append("file", file);
    formData.append("meeting", new Blob([JSON.stringify(data)], {
        type: "application/json"
    }));
    return axios.post("http://localhost:8080/api/meetings/create", formData, {
        headers: {
            "Authorization": "Bearer " + getAccessToken(),
            "Content-Type": "multipart/form-data"
        }
    }).then(response => {
        return {
            errors: [],
            data: response.data
        };
    })
}

export const editMeeting = async data => {
    const errors = validateMeetingUpdatableData(data);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false,
        };
    }
    return axios.post("http://localhost:8080/api/meetings/edit", data, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return {
            errors: [],
            data: response.data
        };
    });
}