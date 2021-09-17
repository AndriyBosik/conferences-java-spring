import React from "react";
import PageItem from "./PageItem";
import { eventPreventer } from "./../../handler/EventHandler";

function Pagination({
    paginationId = "",
    pageColor = "teal",
    totalPages,
    currentPage,
    paginationClass = "",
    previousPageClickHandler = () => {},
    nextPageClickHandler = () => {},
    pageClickHandler = () => {}
}) {
    return totalPages > 1 ? (
        <div id={paginationId} className={paginationClass + " pages-area"}>
            <ul className="pagination center-align">
                <li className={currentPage === 1 ? "disabled" : "waves-effect"}>
                    <a href="#!" onClick={event => {
                        eventPreventer(event);
                        pageClickHandler(currentPage - 1);
                    }} className={currentPage === 1 ? "deactivate" : ""}>
                        <i className="material-icons">chevron_left</i>
                    </a>
                </li>

                {
                    [...Array(totalPages)].map((_, index) => <PageItem key={index} page={index + 1} isActive={currentPage === index + 1} pageColor={pageColor} clickHandler={pageClickHandler} />)
                }

                <li className={currentPage === totalPages ? "disabled" : "waves-effect"}>
                    <a href="#!" onClick={event => {
                        eventPreventer(event);
                        pageClickHandler(currentPage + 1);
                    }} className={currentPage === totalPages ? "deactivate" : ""}>
                        <i className="material-icons">chevron_right</i>
                    </a>
                </li>
            </ul>
        </div>
    ) : null;
}

export default Pagination;