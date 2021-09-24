import axios from "axios";
import { REFRESH_TOKEN_URL } from "../constants/network";
import { tokenStore } from "../stores/TokenStore";
import { isTokenValid } from "./../handler/StorageHandler";

export const refreshToken = async () => {
    if (isTokenValid()) {
        return true;
    }
    try {
        const response = await axios.post(REFRESH_TOKEN_URL, {}, {
            withCredentials: true
        });
        tokenStore.accessToken = response.data;
        return true;
    } catch(error) {
        return false;
    }
}