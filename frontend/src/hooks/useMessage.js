import { useState, useEffect } from "react";
import { messages } from "../constants/messages";
import { history } from "../handler/HistoryHandler";
import { parseUrl } from "../handler/LinkHandler";

export const useMessage = (alias) => {
    const [lang, ] = parseUrl(window.location.pathname);

    const [message, setMessage] = useState(messages[alias][lang]);

    useEffect(() => {
        const disposer = history.listen(location => {
            const [lang, ] = parseUrl(location.pathname);
            setMessage(messages[alias][lang]);
        });

        return disposer;
    }, [alias]);

    return message;
}