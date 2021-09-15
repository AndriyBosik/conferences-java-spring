import { useState } from "react"
import LinkStoreInstance from "../stores/LinkStore";
import { observe } from "mobx";

export const useLink = (to) => {
    const [link, setLink] = useState("/" + LinkStoreInstance.urlLanguage + to);

    observe(LinkStoreInstance, change => {
        setLink("/" + LinkStoreInstance.urlLanguage + to);
    });

    return link;
}