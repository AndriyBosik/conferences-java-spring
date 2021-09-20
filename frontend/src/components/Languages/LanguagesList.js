import React, { useEffect, useState } from "react";
import LanguageItem from "./LanguageItem";
import { localization } from "./../../constants/localization";
import { history } from "../../handler/HistoryHandler";
import { parseUrl } from "./../../handler/LinkHandler";
import { useForceUpdate } from "./../../hooks/useForceUpdate";

function LanguagesList({
    additionalClasses = "",
    initialClass = "teal-text text-lighten-1",
    activeClass = "red-text"
}) {
    const forceUpdate = useForceUpdate();
    const [lang, url] = parseUrl(window.location.pathname);
    const [link, setLink] = useState(url);
    const languages = localization.availableLanguages;
    const activeLanguage = lang;

    useEffect(() => {
        const disposer = history.listen((location) => {
            const [, url] = parseUrl(location.pathname);
            setLink(url);
            forceUpdate();
        });

        return disposer;
    }, [forceUpdate]);

    return (
        <ul className={additionalClasses + " uppercase weight-normal"}>
            {
                languages.map((language, index) => <LanguageItem href={link} language={language} key={index} isActive={language === activeLanguage} initialClass={initialClass} activeClass={activeClass} />)
            }
        </ul>
    );
}

export default LanguagesList;