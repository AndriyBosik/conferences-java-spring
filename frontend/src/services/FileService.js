import axios from "axios"
import { getAccessToken, refreshAccessToken } from "../handler/StorageHandler"

export const saveAvatar = avatar => {
    const formData = new FormData();
    formData.set("file", avatar);
    return axios.post("http://localhost:8080/api/files/save-avatar", formData, {
        headers: {
            "Authorization": "Bearer " + getAccessToken(),
            "Content-Type": "multipart/form-data"
        }
    }).then(response => {
        refreshAccessToken(response.data);
        return {
            status: "success",
            data: response.data
        };
    })
}