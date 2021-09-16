import React from "react";
import { Link } from "react-router-dom";
import { useLink } from "../../hooks/useLink";

function LanguageItem({
    language,
    isActive,
    href,
    initialClass,
    activeClass
}) {
    return (
        <li>
            <Link to={useLink(href, language)} className={(isActive ? activeClass : initialClass) + " px5"}>
                {language}
            </Link>
        </li>
    );
}

export default LanguageItem;