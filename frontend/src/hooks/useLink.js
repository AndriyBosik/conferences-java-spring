import { useState } from "react"
import { history } from "./../handler/HistoryHandler";
import { useEffect } from "react/cjs/react.development";
import { parseUrl } from "./../handler/LinkHandler";

export const useLink = (to, language = null) => {
    const [lang, ] = parseUrl(window.location.pathname);
    const [link, setLink] = useState("/" + (language == null ? lang : language) + to);

    useEffect(() => {
        const disposer = history.listen(location => {
            let [lang, ] = parseUrl(location.pathname);
            lang = language == null ? lang : language;
            setLink("/" + lang + to);
        });

        return disposer;
    }, [language, to]);

    return link;
}