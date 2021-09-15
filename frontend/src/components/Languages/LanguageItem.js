import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { LinkContext } from "../..";
import { generateUrl } from "./../../handler/LinkHandler";

function LanguageItem({language, isActive, keyValue}) {
    const store = useContext(LinkContext);

    return (
        <li>
            <Link to={generateUrl(store.urlLink, language)} className={(isActive ? "red-text" : "teal-text text-lighten-1") + " px5"}>
                {language}
            </Link>
        </li>
    );
}

export default LanguageItem;