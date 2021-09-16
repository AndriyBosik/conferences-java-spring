const permissions = {
    "/users/sign-up": ["guest"],
    "/": ["guest"],
    "/profile": ["user", "speaker", "moderator"]
};

export function checkPermission(url, role) {
    if (typeof permissions[url] === "undefined") {
        return false;
    }
    return permissions[url].includes(role);
}