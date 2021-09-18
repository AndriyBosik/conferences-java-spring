import React from "react";
import { useMessage } from "../../../../hooks/useMessage";
import UserPresenceItem from "./UserPresenceItem";

function PresenceEditorModal({meeting, usersPresence, meetingUsers}) {
    return (
        <div id="presence-editor" className="modal height-70">
            <div className="modal-content row full-width">
                <h5 className="col s12">{useMessage("edit_presence")}</h5>
                <div className="col s12">
                    <ul id="usersPresenceCollection" className="collection">
                        {
                            meetingUsers.map(meetingUser => <UserPresenceItem key={meetingUser.id} meetingUser={meetingUser} usersPresence={usersPresence} />)
                        }
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default PresenceEditorModal;