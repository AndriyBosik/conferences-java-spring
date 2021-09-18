import { pages } from "./../constants/pages";
import { getUserRole } from "./StorageHandler";
import { matchPath } from "react-router-dom";

const permissions = {
    [pages.signUp]: ["guest"],
    [pages.home]: ["guest"],
    [pages.proposals]: ["speaker"],
    [pages.profile]: ["user", "speaker", "moderator"],
    [pages.allMeetings]: ["user", "speaker", "moderator"],
    [pages.meeting]: ["user", "speaker", "moderator"],
};

export const checkPermission = (url) => {
    const role = getUserRole();
    if (typeof permissions[url] !== "undefined") {
        return permissions[url].includes(role);
    }
    return tryBruteForce(url, role);
}

const tryBruteForce = (requestUrl, role) => {
    const urls = Object.keys(permissions);
    for (const url of urls) {
        const match = matchPath(requestUrl, {
            path: url,
            exact: true,
            strict: false
        });
        if (match != null) {
            return permissions[url].includes(role);
        }
    }
    return false;
}