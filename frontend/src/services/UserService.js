import axios from "axios";
import { getAccessToken, getUser, getUserRole, refreshAccessToken, refreshUser } from "../handler/StorageHandler";

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

export const updateProfile = (data) => {
    return axios.post("http://localhost:8080/api/users/update-profile", {
        ...data,
        role: getUserRole()
    }, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        if (response.data !== "") {
            const user = getUser();
            user.email = data.email;
            user.login = data.login;
            user.name = data.name;
            user.surname = data.surname;
            refreshUser(user);
            refreshAccessToken(response.data);
            return true;
        }
        return false;
    })
}

export const joinToMeeting = data => {
    return axios.post("http://localhost:8080/api/users/join-to-meeting", data, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}