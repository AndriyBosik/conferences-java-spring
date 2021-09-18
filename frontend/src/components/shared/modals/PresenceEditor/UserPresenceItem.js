import React from "react";
import { useMessage } from "../../../../hooks/useMessage";

function UserPresenceItem({meetingUser, usersPresence}) {
    const isNotPresentMessage = useMessage("is_not_present");
    const isPresentMessage = useMessage("is_present");

    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`/shared/images/avatars/${meetingUser.user.imagePath}`} alt="" className="circle full-width full-height" />
            </div>
            <span className="title weight-normal s-vflex-center mx10 equal-flex">
                {meetingUser.user.name} {meetingUser.user.surname}
            </span>
            <span className="secondary-content s-vflex-center col s12 m3">
                <div className="s-hflex-end">
                    {
                        meetingUser.present ? (
                            <div className="clickable">
                                <i className="material-icons weight-strong fs30 red-text tooltipped" data-position="right" data-tooltip={isNotPresentMessage}>remove</i>
                            </div>
                        ) : (
                            <div className="clickable">
                                <i className="material-icons weight-strong fs30 tooltipped" data-position="right" data-tooltip={isPresentMessage}>add</i>
                            </div>
                        )
                    }
                </div>
            </span>
        </li>
    );
}

export default UserPresenceItem;