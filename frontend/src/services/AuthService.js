import axios from "axios";
import { saveData, getUserRole, clearData } from "../handler/StorageHandler";
import { validate } from "../validators/SignUpValidator"; 

export const loginUser = (data) => {
    return axios.post("http://localhost:8080/api/auth/login", data).then(response => {
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

export const signUpUser = (data) => {
    const errors = validate(data);
    if (errors.length > 0) {
        return {
            errors: errors,
            data: {}
        };
    }
    return axios.post("http://localhost:8080/api/sign-up", data).then(response => {
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