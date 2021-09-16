import { localization } from "../constants/localization";
import LinkStore from "./../stores/LinkStore";

export const generateUrl = (to, lang = null) => {
    if (lang == null) {
        return "/" + LinkStore.urlLanguage + to;
    }
    return "/" + lang + to;
}

export const parseUrl = (pathname) => {
    const parts = pathname.split("/").splice(1);
    const defaultLanguage = localization.defaultLanguage;
    
    if (parts.length === 1) {
        if (parts[0] === "") {
            return [defaultLanguage, "/"];
        } else {
            return [parts[0], "/"];
        }
    } else {
        return [parts[0], "/" + parts.splice(1).join("/")];
    }
}