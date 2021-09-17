import React from "react";
import { eventPreventer } from "./../../handler/EventHandler";

function PageItem({
    isActive,
    page,
    clickHandler,
    pageColor
}) {
    return (
        <li className={isActive ? ("active " + pageColor) : "waves-effect"}>
            <a href="#!" className="cancel-click" onClick={(event) => {
                eventPreventer(event);
                clickHandler(page);
            }}>
                {page}
            </a>
        </li>
    );
}

export default PageItem;