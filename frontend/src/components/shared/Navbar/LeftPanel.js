import React, { useState } from "react";
import { useLink } from "./../../../hooks/useLink";
import { pages } from "./../../../constants/pages";
import RoleController from "../../RoleController/RoleController";
import { Link } from "react-router-dom";
import { useMessage } from "../../../hooks/useMessage";
import { logoutUser, isAuthorized } from "./../../../services/AuthService";
import { Redirect } from "react-router";

function LeftPanel() {
    const [isLoggedIn, setIsLoggedIn] = useState(isAuthorized());
    
    const loginPageLink = useLink(pages.home);
    const profileLink = useLink(pages.profile);
    const allMeetingsLink = useLink(pages.allMeetings);
    const proposalsLink = useLink(pages.proposals);

    const profileMessage = useMessage("profile");
    const meetingsMessage = useMessage("meetings");
    const proposalsMessage = useMessage("proposals");
    const logoutMessage = useMessage("logout");

    const handleLogout = event => {
        event.preventDefault();
        logoutUser();
        setIsLoggedIn(false);
    }

    return (
        isLoggedIn ? (
            <ul id="nav-mobile" className="left hide-on-small-and-down">
                <li>
                    <Link to={profileLink} className="tooltipped" data-position="bottom" data-tooltip={profileMessage}>
                        <i className="material-icons">person</i>
                    </Link>
                </li>
                <li>
                    <Link to={allMeetingsLink} className="tooltipped" data-position="bottom" data-tooltip={meetingsMessage}>
                        <i className="material-icons">people</i>
                    </Link>
                </li>
                <RoleController allow={["speaker"]}>
                    <li>
                        <Link to={proposalsLink} className="tooltipped" data-position="bottom" data-tooltip={proposalsMessage}>
                            <i className="material-icons">local_offer</i>
                        </Link>
                    </li>
                </RoleController>
                <li>
                    <Link to="#!" className="tooltipped" data-position="bottom" data-tooltip={logoutMessage} onClick={handleLogout}>
                        <i className="material-icons">exit_to_app</i>
                    </Link>
                </li>
            </ul>
        ) : (
            <Redirect to={loginPageLink} />
        )
    );
}

export default LeftPanel;