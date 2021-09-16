import React from "react";
import { Link } from "react-router-dom";
import { useLink } from "../../hooks/useLink";

function LanguageItem({language, isActive, href}) {
    return (
        <li>
            <Link to={useLink(href, language)} className={(isActive ? "red-text" : "teal-text text-lighten-1") + " px5"}>
                {language}
            </Link>
        </li>
    );
}

export default LanguageItem;