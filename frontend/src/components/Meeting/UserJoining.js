import React, { useState } from "react";
import { useEffect } from "react/cjs/react.development";
import { useMessage } from "../../hooks/useMessage";
import { isOutdated } from "./../../handler/DateHandler";
import { checkUserJoined } from "./../../services/MeetingService";

function UserJoining({user, meeting}) {
    const joinedMessage = useMessage("joined");
    const joinMessage = useMessage("join");

    const [isJoined, setIsJoined] = useState(false);

    useEffect(() => {
        const fetchIsJoined = async () => {
            if (typeof user.id !== "undefined" && typeof meeting.id !== "undefined") {
                setIsJoined(await checkUserJoined(user.id, meeting.id));
            }
        }

        fetchIsJoined();
    }, [meeting, user]);

    const handleSubmit = event => {
        event.preventDefault();

        const data = {
            userId: user.id,
            meetingId: meeting.id
        }
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
                            <button type="submit" class="btn waves-effect waves-light light-blue darken-4">
                                {joinMessage}
                                <i class="material-icons right">person_add</i>
                            </button>
                        </form>
                    )
                )
            }
        </div>
    );
}

export default UserJoining;