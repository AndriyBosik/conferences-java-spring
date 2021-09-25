import { SAVE_AVATAR_URL } from "../constants/network";
import { doPost } from "../handler/AuthRequestHandler";
import { refreshAccessToken } from "../handler/StorageHandler"

export const saveAvatar = async avatar => {
    const formData = new FormData();
    formData.set("file", avatar);
    return doPost(SAVE_AVATAR_URL, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }, response => {
        refreshAccessToken(response.data);
        return {
            status: "success",
            data: response.data
        };
    }, () => ({
        status: "error",
        data: null
    }));
}