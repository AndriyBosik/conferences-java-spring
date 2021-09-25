import { validate } from "./../validators/MeetingValidator";
import { validate as validateMeetingUpdatableData } from "./../validators/MeetingUpdatableDataValidator";
import { doGet, doPost } from "../handler/AuthRequestHandler";
import { CHECK_USER_JOINED_URL, CREATE_MEETING_URL, EDIT_MEETING_URL, MEETINGS_BY_PAGE_URL, MEETING_URL, SPEAKER_MEETINGS_BY_PAGE_URL } from "../constants/network";
import { format } from "./../handler/StringHandler";

export const getAllMeetings = async (page, items, filters) => {
    const url = format(MEETINGS_BY_PAGE_URL, {page: page, items: items});
    return doGet(url, {
        params: filters
    }, response => response.data, () => null);
};

export const getAllMeetingsForSpeaker = speakerId => async (page, items, filters) => {
    const url = format(SPEAKER_MEETINGS_BY_PAGE_URL, {page: page, items: items});
    return doGet(url, {
        params: {
            ...filters,
            speakerId
        }
    }, response => response.data, () => null);
}

export const getMeeting = async meetingId => {
    const url = format(MEETING_URL, {meetingId: meetingId});
    return doGet(url, {}, response => response.data, () => null);
}

export const checkUserJoined = async (userId, meetingId) => {
    return doGet(CHECK_USER_JOINED_URL, {
        params: {
            userId: userId,
            meetingId: meetingId
        }
    }, response => response.data, () => false);
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
    return doPost(CREATE_MEETING_URL, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }, response => ({
        errors: [],
        data: response.data
    }), () => ({
        errors: [],
        data: false
    }))
}

export const editMeeting = async data => {
    const errors = validateMeetingUpdatableData(data);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: false,
        };
    }
    return doPost(EDIT_MEETING_URL, data, {}, response => ({
        errors: [],
        data: response.data
    }), () => ({
        errors: [],
        data: false
    }));
}