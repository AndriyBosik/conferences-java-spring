import { useContext, useEffect } from "react";
import { parseUrl } from "../../handler/LinkHandler";
import { history } from "./../../handler/HistoryHandler";
import { LinkContext } from "../../index";

function LinkParser({children}) {
    const store = useContext(LinkContext);
    useEffect(() => {
        changeStoreLanguage(store, window.location.pathname);
    }, [store]);

    useEffect(() => {
        history.listen((location) => {
            changeStoreLanguage(store, location.pathname);
        });
    }, [store]);

    return children;
}

export default LinkParser;

function changeStoreLanguage(store, pathname) {
    const [language, link] = parseUrl(pathname);
    store.updateUrlLanguage(language);
    store.updateUrlLink(link);
}