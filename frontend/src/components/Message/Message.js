import { useState, useEffect } from "react";
import { messages } from "../../constants/messages";
import { observe } from "mobx";
import LinkStoreInstance from "../../stores/LinkStore";

function Message({alias}) {
    const [message, setMessage] = useState(messages[alias][LinkStoreInstance.urlLanguage]);

    useEffect(() => {
        const disposer = observe(LinkStoreInstance, () => {
            setMessage(messages[alias][LinkStoreInstance.urlLanguage]);
        });

        return disposer;
    }, [alias]);

    return (
        message
    );
}

export default Message;