import { useState } from "react"
import LinkStoreInstance from "../stores/LinkStore";
import { observe } from "mobx";
import { useEffect } from "react/cjs/react.development";

export const useLink = (to, language = null) => {
    let newTo = to.length === 0 ? LinkStoreInstance.urlLink : to;
    const [link, setLink] = useState("/" + (language == null ? LinkStoreInstance.urlLanguage : language) + newTo);

    useEffect(() => {
        const disposer = observe(LinkStoreInstance, change => {
            let newTo = to.length === 0 ? LinkStoreInstance.urlLink : to;
            setLink("/" + (language == null ? LinkStoreInstance.urlLanguage : language) + newTo);
        });

        return disposer;
    });

    return link;
}