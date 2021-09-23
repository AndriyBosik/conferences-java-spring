import axios from "axios";
import { getAccessToken, getUser, getUserRole, refreshAccessToken, refreshUser } from "../handler/StorageHandler";

export const getUserEmail = async () => {
    return axios.get("http://localhost:8080/api/users/get-email", {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    })
}

export const getSpeakers = async () => {
    return axios.get("http://localhost:8080/api/users/speakers", {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const getAvailableSpeakers = async topicId => {
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

export const getSpeakerProposals = async topicId => {
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

export const getMeetingUsers = async meetingId => {
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

export const updateProfile = async userProfile => {
    return axios.post("http://localhost:8080/api/users/update-profile", {
        ...userProfile,
        role: getUserRole()
    }, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        if (response.data !== "") {
            const user = getUser();
            user.email = userProfile.email;
            user.login = userProfile.login;
            user.name = userProfile.name;
            user.surname = userProfile.surname;
            refreshUser(user);
            refreshAccessToken(response.data);
            return true;
        }
        return false;
    })
}

export const joinToMeeting = async userMeeting => {
    return axios.post("http://localhost:8080/api/users/join-to-meeting", userMeeting, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const editUserPresence = async userMeeting => {
    return axios.post("http://localhost:8080/api/users/edit-presence", userMeeting, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}

export const selectSpeakerForTopic = async reportTopicSpeaker => {
    return axios.post("http://localhost:8080/api/topics/set-speaker", reportTopicSpeaker, {
        headers: {
            "Authorization": "Bearer " + getAccessToken()
        }
    }).then(response => {
        return response.data;
    });
}