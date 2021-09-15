import { useContext } from "react";
import { localization } from "./../../constants/localization";
import { history } from "./../../handler/HistoryHandler";
import { LinkContext } from "../../index";

function LinkParser({children}) {
    const store = useContext(LinkContext);
    changeStoreLanguage(store, window.location.pathname);

    history.listen((location) => {
        changeStoreLanguage(store, location.pathname);
    });

    return children;
}

export default LinkParser;

function changeStoreLanguage(store, pathname) {
    const [language, link] = parseUrl(pathname);
    store.updateUrlLanguage(language);
    store.updateUrlLink(link);
}

function parseUrl(pathname) {
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