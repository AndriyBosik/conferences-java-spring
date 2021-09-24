import { EDIT_USER_PRESENCE_URL, GET_AVAILABLE_SPEAKERS_FOR_TOPIC_URL, GET_MEETING_USERS_URL, GET_SPEAKERS_URL, GET_USER_EMAIL_URL, JOIN_USER_TO_MEETING_URL, SET_SPEAKER_FOR_TOPIC_URL, UPDATE_PROFILE_URL } from "../constants/network";
import { doGet, doPost } from "../handler/AuthRequestHandler";
import { getUser, getUserRole, refreshAccessToken, refreshUser } from "../handler/StorageHandler";

export const getUserEmail = async () => {
    return doGet(GET_USER_EMAIL_URL, {}, response => response.data);
}

export const getSpeakers = async () => {
    return doGet(GET_SPEAKERS_URL, {}, response => response.data);
}

export const getAvailableSpeakers = async topicId => {
    return doGet(GET_AVAILABLE_SPEAKERS_FOR_TOPIC_URL, {
        params: {
            topicId
        }
    }, response => response.data);
}

export const getSpeakerProposals = async topicId => {
    return doGet("http://localhost:8080/api/users/get-topic-proposed", {
        params: {
            topicId
        }
    }, response => response.data);
}

export const getMeetingUsers = async meetingId => {
    return doGet(GET_MEETING_USERS_URL, {
        params: {
            meetingId
        }
    }, response => response.data);
}

export const updateProfile = async userProfile => {
    return doPost(UPDATE_PROFILE_URL, {
        ...userProfile,
        role: getUserRole()
    }, {}, response => {
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
    userMeeting = {
        ...userMeeting,
        userId: getUser().id
    }
    return doPost(JOIN_USER_TO_MEETING_URL, userMeeting, {}, response => response.data);
}

export const editUserPresence = async userMeeting => {
    return doPost(EDIT_USER_PRESENCE_URL, userMeeting, {}, response => response.data);
}

export const selectSpeakerForTopic = async reportTopicSpeaker => {
    return doPost(SET_SPEAKER_FOR_TOPIC_URL, reportTopicSpeaker, {}, response => response.data);
}