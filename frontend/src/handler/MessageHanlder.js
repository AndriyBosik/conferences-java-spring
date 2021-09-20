import { parseUrl } from "../handler/LinkHandler";
import { messages } from "../constants/messages";

export const getMessage = (alias, pathname = null) => {
    let [lang, ] = parseUrl(window.location.pathname);
    if (pathname != null) {
        [lang, ] = parseUrl(pathname);
    }

    if (typeof messages[alias] === "undefined" || typeof messages[alias][lang] === "undefined") {
        return alias;
    }
    return messages[alias][lang];
}