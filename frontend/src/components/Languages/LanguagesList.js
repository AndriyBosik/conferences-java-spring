import React from "react";
import LanguageItem from "./LanguageItem";
import { localization } from "./../../constants/localization";
import { parseUrl } from "./../../handler/LinkHandler";

function LanguagesList({
    additionalClasses = "",
    initialClass = "teal-text text-lighten-1",
    activeClass = "red-text"
}) {
    const [lang, ] = parseUrl(window.location.pathname);
    const languages = localization.availableLanguages;
    const activeLanguage = lang;

    return (
        <ul className={additionalClasses + " uppercase weight-normal"}>
            {
                languages.map((language, index) => <LanguageItem href="" language={language} key={index} isActive={language === activeLanguage} initialClass={initialClass} activeClass={activeClass} />)
            }
        </ul>
    );
}

export default LanguagesList;