import React from "react";
import LanguageItem from "./LanguageItem";

function LanguagesList({
    additionalClasses = ""
}) {
    const languages = ["en", "ru", "uk"];
    const activeLanguage = "en";
    return (
        <ul className={additionalClasses + " uppercase weight-normal"}>
            {
                languages.map(language => <LanguageItem language={language} isActive={language === activeLanguage} />)
            }
        </ul>
    );
}

export default LanguagesList;