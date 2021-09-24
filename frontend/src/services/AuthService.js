import axios from "axios";
import { LOGIN_URL, SIGN_UP_URL } from "../constants/network";
import { saveData, getUserRole, clearData } from "../handler/StorageHandler";
import { validate } from "../validators/SignUpValidator"; 

export const loginUser = async data => {
    return axios.post(LOGIN_URL, data).then(response => {
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

export const logoutUser = () => {
    clearData();
}

export const isAuthorized = () => {
    return getUserRole() !== "guest";
}

export const signUpUser = async data => {
    const errors = validate(data);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: {}
        };
    }
    return axios.post(SIGN_UP_URL, data).then(response => {
        return {
            errors: [],
            data: response.data
        };
    }).catch(() => {
        return {
            errors: [],
            data: false
        };
    });
}