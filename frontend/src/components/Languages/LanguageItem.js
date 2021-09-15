import React from "react";

function LanguageItem({language, isActive}) {
    return (
        <li>
            <a href={"/" + language} className={(isActive ? "red-text" : "teal-text text-lighten-1") + " px5"}>{language}</a>
        </li>
    );
}

export default LanguageItem;