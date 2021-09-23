import { SERVER } from "../constants/network"

export const getUserAvatar = imagePath => {
    return SERVER + "/api/images/avatars/" + imagePath;
}

export const getMeetingImage = imagePath => {
    return SERVER + "/api/images/meetings/" + imagePath;
}