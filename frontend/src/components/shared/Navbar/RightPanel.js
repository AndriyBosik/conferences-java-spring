import React from "react";
import LanguagesList from "../../Languages/LanguagesList";

function RightPanel() {
    return (
        <LanguagesList
            additionalClasses="right"
            initialClass="white-text"
            activeClass="light-blue-text text-darken-4 weight-strong" />
    );
}

export default RightPanel;