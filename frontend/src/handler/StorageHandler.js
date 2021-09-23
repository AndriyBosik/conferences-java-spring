const STORAGE_KEY = "storageData";

export const refreshAccessToken = newToken => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    data.accessToken = newToken;
    saveData(data);
}

export const getUserRole = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    if (data == null) {
        return "guest";
    }
    const user = getUserFromToken(data.accessToken);
    return user.role.title;
}

export const saveData = (data) => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

export const clearData = () => {
    localStorage.removeItem(STORAGE_KEY);
}

export const getUser = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return data == null ? null : getUserFromToken(data.accessToken);
}

export const getAccessToken = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return data == null ? null : data.accessToken;
}

export const refreshUser = newUser => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    data.user = newUser;
    saveData(data);
}

const getUserFromToken = token => {
    const payload = token.split(".")[1];
    const value = Buffer.from(payload, "base64");
    return JSON.parse(JSON.parse(value).sub);
}