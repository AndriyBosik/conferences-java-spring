import { tokenStore } from "./../stores/TokenStore";

export const refreshAccessToken = newToken => {
    tokenStore.accessToken = newToken;
}

export const getUserRole = () => {
    const user = getUserFromToken(tokenStore.accessToken);
    if (user == null) {
        return "guest";
    }
    return user.role.title;
}

export const saveData = data => {
    tokenStore.accessToken = data.accessToken;
}

export const clearData = () => {
    tokenStore.accessToken = null;
}

export const getUser = () => {
    return tokenStore.accessToken == null ? null : getUserFromToken(tokenStore.accessToken);
}

export const getAccessToken = () => {
    return tokenStore == null ? null : tokenStore.accessToken;
}

export const isTokenValid = () => {
    return tokenStore.accessToken != null && (Date.now() / 1000) < getTokenExpiration(getAccessToken());
}

const getTokenExpiration = token => {
    if (token == null) {
        return null;
    }
    const payload = token.split(".")[1];
    const value = Buffer.from(payload, "base64");
    return JSON.parse(JSON.parse(value).exp);
}

const getUserFromToken = token => {
    if (token == null) {
        return null;
    }
    const payload = token.split(".")[1];
    const value = Buffer.from(payload, "base64");
    return JSON.parse(JSON.parse(value).sub);
}