import { useEffect, useState } from "react"
import { messages } from "../constants/messages";
import { history } from "../handler/HistoryHandler";
import { parseUrl } from "./../handler/LinkHandler";

export const useTitle = (alias) => {
    const [lang, ] = parseUrl(window.location.pathname);

    const [title, setTitle] = useState(messages[alias][lang]);

    useEffect(() => {
        const disposer = history.listen(location => {
            const [lang, ] = parseUrl(location.pathname);
            setTitle(messages[alias][lang]);
        });

        return disposer;
    }, [alias]);

    document.title = title;
}