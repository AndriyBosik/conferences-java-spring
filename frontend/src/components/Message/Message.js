import { useState, useEffect } from "react";
import { messages } from "../../constants/messages";
import { parseUrl } from "./../../handler/LinkHandler";
import { history } from "./../../handler/HistoryHandler";

function Message({alias}) {
    const [lang, ] = parseUrl(window.location.pathname);
    const [message, setMessage] = useState(messages[alias][lang]);

    useEffect(() => {
        const disposer = history.listen(location => {
            const [lang, ] = parseUrl(location.pathname);
            setMessage(messages[alias][lang]);
        });

        return disposer;
    }, [alias]);

    return (
        message
    );
}

export default Message;