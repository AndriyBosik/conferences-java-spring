import { useEffect, useState } from "react"
import LinkStoreInstance from "../stores/LinkStore";
import { messages } from "../constants/messages";
import { observe } from "mobx";

export const useTitle = (alias) => {
    const [title, setTitle] = useState(messages[alias][LinkStoreInstance.urlLanguage]);

    useEffect(() => {
        const disposer = observe(LinkStoreInstance, () => {
            setTitle(messages[alias][LinkStoreInstance.urlLanguage]);
        });

        return disposer;
    }, [alias]);

    document.title = title;
}