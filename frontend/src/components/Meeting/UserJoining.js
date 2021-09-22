import React, { useState } from "react";
import { useEffect } from "react/cjs/react.development";
import { useMessage } from "../../hooks/useMessage";
import { isOutdated } from "./../../handler/DateHandler";
import { checkUserJoined } from "./../../services/MeetingService";
import { joinToMeeting } from "./../../services/UserService";
import { getMessage } from "./../../handler/MessageHanlder";
import M from "materialize-css";

function UserJoining({user, meeting}) {
    const joinedMessage = useMessage("joined");
    const joinMessage = useMessage("join");

    const [isJoined, setIsJoined] = useState(false);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchIsJoined = async () => {
            if (typeof user.id !== "undefined" && typeof meeting.id !== "undefined") {
                setIsJoined(await checkUserJoined(user.id, meeting.id));
            }
        }

        fetchIsJoined();
    }, [meeting, user]);

    const handleSubmit = async event => {
        setLoading(true);
        event.preventDefault();

        const data = {
            userId: user.id,
            meetingId: meeting.id
        }

        const result = await joinToMeeting(data);
        if (result) {
            setIsJoined(true);
        } else {
            M.toast({
                html: getMessage("error_happened")
            });
        }
        setLoading(false);
    }

    return (
        <div className="s-hflex-end">
            {
                isJoined ? (
                    <div className="weight-strong translucent uppercase s-hflex uppercase">
                        <i className="material-icons s-vflex-center px5 weight-strong">check</i>
                        <span className="s-vflex-center">{joinedMessage}</span>
                    </div>
                ) : (
                    isOutdated(meeting.date) ? null : (
                        <form className="m0" method="post" onSubmit={handleSubmit}>
                            <button type="submit" className="btn waves-effect waves-light light-blue darken-4" disabled={loading ? "disabled" : ""}>
                                {joinMessage}
                                <i className="material-icons right">person_add</i>
                            </button>
                        </form>
                    )
                )
            }
        </div>
    );
}

export default UserJoining;