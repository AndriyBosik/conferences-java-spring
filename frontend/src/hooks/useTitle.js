import { useState } from "react"
import LinkStoreInstance from "../stores/LinkStore";
import { messages } from "../constants/messages";
import { observe } from "mobx";

export const useTitle = (alias) => {
    const [title, setTitle] = useState(messages[alias][LinkStoreInstance.urlLanguage]);

    observe(LinkStoreInstance, change => {
        setTitle(messages[alias][LinkStoreInstance.urlLanguage]);
    });

    document.title = title;
}