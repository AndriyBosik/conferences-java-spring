const STORAGE_KEY = "storageData";

export const getUserRole = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    if (data == null) {
        return "guest";
    }
    return data.userData.role;
}

export const saveData = (data) => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

export const getUser = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return data.userData;
}

export const getAccessToken = () => {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return data.accessToken;
}