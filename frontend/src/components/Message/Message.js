import { useState } from "react";
import { messages } from "../../constants/messages";
import { observe } from "mobx";
import LinkStoreInstance from "../../stores/LinkStore";

function Message({alias}) {
    const [message, setMessage] = useState(messages[alias][LinkStoreInstance.urlLanguage]);

    observe(LinkStoreInstance, change => {
        setMessage(messages[alias][LinkStoreInstance.urlLanguage]);
    });

    return (
        message
    );
}

export default Message;