import React, { useContext, useEffect } from "react";
import { LinkContext } from "../..";
import LanguageItem from "./LanguageItem";
import { localization } from "./../../constants/localization";
import { history } from "../../handler/HistoryHandler";
import { parseUrl } from "./../../handler/LinkHandler";
import { useForceUpdate } from "./../../hooks/useForceUpdate";

function LanguagesList({additionalClasses = ""}) {
    const forceUpdate = useForceUpdate();
    const languages = localization.availableLanguages;
    const store = useContext(LinkContext);
    const activeLanguage = store.urlLanguage;

    useEffect(() => {
        const disposer = history.listen((location) => {
            forceUpdate();
        });

        return disposer;
    }, [forceUpdate]);

    return (
        <ul className={additionalClasses + " uppercase weight-normal"}>
            {
                languages.map((language, index) => <LanguageItem href={parseUrl(window.location.pathname)[1]} language={language} key={index} isActive={language === activeLanguage} />)
            }
        </ul>
    );
}

export default LanguagesList;