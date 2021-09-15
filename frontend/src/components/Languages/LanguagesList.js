import React, { useContext } from "react";
import { LinkContext } from "../..";
import LanguageItem from "./LanguageItem";
import { localization } from "./../../constants/localization";

function LanguagesList({additionalClasses = ""}) {
    const languages = localization.availableLanguages;
    const store = useContext(LinkContext);
    const activeLanguage = store.urlLanguage;

    return (
        <ul className={additionalClasses + " uppercase weight-normal"}>
            {
                languages.map((language, index) => <LanguageItem language={language} key={index} isActive={language === activeLanguage} />)
            }
        </ul>
    );
}

export default LanguagesList;