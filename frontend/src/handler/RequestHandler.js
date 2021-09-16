import axios from "axios";
import { saveData } from "./StorageHandler";

export const doPost = (url, data) => {
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