const STORAGE_KEY = "storageData";

export const getUserRole = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    if (data == null) {
        return "guest";
    }
    return data.user.role;
}

export const saveData = (data) => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

export const clearData = () => {
    localStorage.removeItem(STORAGE_KEY);
}

export const getUser = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return data == null ? null : data.user;
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