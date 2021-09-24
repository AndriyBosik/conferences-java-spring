import axios from "axios"
import { getAccessToken } from "./StorageHandler"

export const doGet = (url, params = {}, onSuccess = () => {}, onError = () => {}) => {
    params = initParamsWithAuthorizationHeader(params);
    return axios.get(url, params).then(onSuccess).catch(onError);
}

export const doPost = (url, data = {}, params = {}, onSuccess, onError) => {
    params = initParamsWithAuthorizationHeader(params);
    return axios.post(url, data, params).then(onSuccess).catch(onError);
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