import axios from "axios";
import { saveData, getUserRole, getUser } from "../handler/StorageHandler";

export const loginUser = (url, data) => {
    return axios.post(url, data).then(response => {
        saveData(response.data);
        return {
            error: false,
            data: response.data
        }
    }, error => {
        return {
            error: true,
            message: "Invalid login or password"
        };
    });
}

export const isAuthorized = () => {
    return getUserRole() !== "guest";
}