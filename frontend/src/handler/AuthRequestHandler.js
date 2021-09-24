import axios from "axios"
import { refreshToken } from "./TokenHandler";
import { getAccessToken } from "./StorageHandler";

export const doGet = async (url, params = {}, onSuccess = () => {}, onError = () => {}) => {
    try {
        await refreshToken();
        params = initParamsWithAuthorizationHeader(params);
        const result = await axios.get(url, params);
        return onSuccess(result);
    } catch(error) {
        return onError(error);
    }
}

export const doPost = async (url, data = {}, params = {}, onSuccess, onError) => {
    try {
        await refreshToken();
        params = initParamsWithAuthorizationHeader(params);
        const result = await axios.post(url, data, params);
        return onSuccess(result);
    } catch(error) {
        return onError(error);
    }
}

const initParamsWithAuthorizationHeader = params => {
    const authorizationHeaderValue = "Bearer " + getAccessToken();
    return {
        ...params,
        headers: {
            ...params.headers,
            "Authorization": authorizationHeaderValue
        }
    }
}