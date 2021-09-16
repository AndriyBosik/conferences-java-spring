import React from "react";
import { useLink } from "./../../../hooks/useLink";
import { pages } from "./../../../constants/pages";
import Message from "../../Message/Message";
import RoleController from "../../RoleController/RoleController";
import { Link } from "react-router-dom";

function LeftPanel() {
    return (
        <ul id="nav-mobile" className="left hide-on-med-and-down">
            <li>
                <Link to={useLink(pages.profile)} className="tooltipped" data-position="bottom" data-tooltip={<Message alias="profile" />}>
                    <i className="material-icons">person</i>
                </Link>
            </li>
            <li>
                <Link to={useLink(pages.allMeetings)} className="tooltipped" data-position="bottom" data-tooltip={<Message alias="profile" />}>
                    <i className="material-icons">people</i>
                </Link>
            </li>
            <RoleController allow={["speaker"]}>
                <li>
                    <Link to={useLink(pages.proposals)} className="tooltipped" data-position="bottom" data-tooltip={<Message alias="profile" />}>
                        <i className="material-icons">local_offer</i>
                    </Link>
                </li>
            </RoleController>
            <li>
                <Link to={useLink(pages.logout)} className="tooltipped" data-position="bottom" data-tooltip={<Message alias="profile" />}>
                    <i className="material-icons">exit_to_app</i>
                </Link>
            </li>
        </ul>
    );
}

export default LeftPanel;