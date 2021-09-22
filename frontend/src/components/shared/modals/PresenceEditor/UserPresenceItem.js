import React, { useState } from "react";
import { getMessage } from "../../../../handler/MessageHanlder";
import { useMessage } from "../../../../hooks/useMessage";
import { editUserPresence } from "./../../../../services/UserService";
import CircularPreloader from "./../../../CircularPreloader/CircularPreloader";
import M from "materialize-css";
import { useEffect } from "react/cjs/react.development";

function UserPresenceItem({meetingUser, onPresenceChanged = () => {}}) {
    const isNotPresentMessage = useMessage("is_not_present");
    const isPresentMessage = useMessage("is_present");

    const [loading, setLoading] = useState(false);
    const [present, setPresent] = useState(meetingUser.present);

    useEffect(() => {
        if (meetingUser.present !== present) {
            meetingUser.present = present;
            onPresenceChanged(meetingUser);
        }
    }, [present, meetingUser, onPresenceChanged]);

    const presence = async present => {
        setLoading(true);
        const data = {
            id: meetingUser.id,
            userId: meetingUser.user.id,
            meetingId: meetingUser.meetingId,
            present: present
        }

        const result = await editUserPresence(data);
        if (result) {
            setPresent(present => present === false);
        } else {
            M.toast({
                html: getMessage("error_happened")
            });
        }
        setLoading(false);
    }

    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`http://localhost:8080/api/images/avatars/${meetingUser.user.imagePath}`} alt="" className="circle full-width full-height" />
            </div>
            <span className="title weight-normal s-vflex-center mx10 equal-flex">
                {meetingUser.user.name} {meetingUser.user.surname}
            </span>
            <span className="secondary-content s-vflex-center col s12 m3">
                <div className="s-hflex-end">
                    {
                        loading ? (
                            <CircularPreloader size="" />
                        ) : (
                            present ? (
                                <div className="clickable" onClick={() => presence(false)}>
                                    <i className="material-icons weight-strong fs30 red-text tooltipped" data-position="right" data-tooltip={isNotPresentMessage}>remove</i>
                                </div>
                            ) : (
                                <div className="clickable" onClick={() => presence(true)}>
                                    <i className="material-icons weight-strong fs30 tooltipped" data-position="right" data-tooltip={isPresentMessage}>add</i>
                                </div>
                            )
                        )
                    }
                </div>
            </span>
        </li>
    );
}

export default UserPresenceItem;