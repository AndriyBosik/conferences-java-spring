import { pages } from "./../constants/pages";
import { getUserRole } from "./StorageHandler";

const permissions = {
    [pages.signUp]: ["guest"],
    [pages.home]: ["guest"],
    [pages.profile]: ["user", "speaker", "moderator"],
    [pages.allMeetings]: ["user", "speaker", "moderator"]
};

export function checkPermission(url) {
    const role = getUserRole();
    if (typeof permissions[url] === "undefined") {
        return false;
    }
    return permissions[url].includes(role);
}