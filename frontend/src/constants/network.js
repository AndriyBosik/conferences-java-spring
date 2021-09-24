export const SERVER = "http://localhost:8080";
export const API = SERVER + "/api";

export const GET_USER_EMAIL_URL = API + "/users/get-email";
export const GET_SPEAKERS_URL = API + "/users/speakers";
export const GET_AVAILABLE_SPEAKERS_FOR_TOPIC_URL = API + "/users/get-topic-available";
export const GET_SPEAKER_PROPOSALS_URL = API + "/users/get-topic-proposed";
export const GET_MEETING_USERS_URL = API + "/users/get-for-meeting";
export const UPDATE_PROFILE_URL = API + "/users/update-profile";
export const JOIN_USER_TO_MEETING_URL = API + "/users/join-to-meeting";
export const EDIT_USER_PRESENCE_URL = API + "/users/edit-presence";

export const LOGIN_URL = API + "/auth/login";

export const SIGN_UP_URL = API + "/sign-up";

export const SAVE_AVATAR_URL = API + "/files/save-avatar";

export const MEETINGS_BY_PAGE_URL = API + "/meetings/page/{page}/{items}";
export const SPEAKER_MEETINGS_BY_PAGE_URL = API + "/meetings/speaker/{page}/{items}";
export const MEETING_URL = API + "/meetings/{meetingId}";
export const CHECK_USER_JOINED_URL = API + "/meetings/check-user-joined";
export const CREATE_MEETING_URL = API + "/meetings/create";
export const EDIT_MEETING_URL = API + "/meetings/edit";

export const GET_TOPICS_WITH_SPEAKER_PROPOSALS_URL = API + "/proposals/speaker/{speakerId}";
export const GET_MODERATOR_PRPOSALS_URL = API + "/proposals/moderator/{speakerId}";

export const CREATE_TOPIC_PROPOSAL_URL = API + "/topic-proposals/create";
export const GET_TOPIC_PROPOSALS_URL = API + "/topic-proposals";
export const GET_TOPIC_PROPOSALS_COUNT_URL = API + "/topic-proposals/count";
export const REJECT_TOPIC_PROPOSAL_URL = API + "/topic-proposals/reject";

export const REJECT_MODERATOR_PROPOSAL_URL = API + "/moderator-proposals/reject";
export const ACCEPT_MODERATOR_PROPOSAL_URL = API + "/moderator-proposals/accept";
export const PROPOSE_TOPIC_FOR_SPEAKER_URL = API + "/moderator-proposals/propose";

export const MAKE_SPEAKER_PROPOSAL = API + "/speaker-proposals/propose";

export const GET_SPEAKER_PROPOSED_TOPIC_IDS_URL = API + "/topics/get-speaker-proposed-topic-ids";
export const CREATE_TOPIC_FROM_PROPOSAL_URL = API + "/topics/create-from-proposal";
export const CREATE_TOPIC_URL = API + "/topics/create";
export const EDIT_TOPIC_URL = API + "/topics/edit";
export const GET_TOPICS_BY_MEETING_ID_URL = API + "/topics/get-by-meeting";
export const SET_SPEAKER_FOR_TOPIC_URL = API + "/topics/set-speaker";