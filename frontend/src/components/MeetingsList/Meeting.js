import React from "react";
import { useMessage } from "../../hooks/useMessage";
import RoleController from "../RoleController/RoleController";
import { format } from "./../../handler/StringHandler";
import { useLink } from "./../../hooks/useLink";
import { formatDate, isOutdated } from "./../../handler/DateHandler";

function Meeting({meeting}) {
    const editMessage = useMessage("edit");
    const meetingLink = useLink(format("/meetings/show/{id}", {id: meeting.id}));
    const meetingCardImageStyles = {
        backgroundImage: `url('/shared/images/meetings/${meeting.imagePath}')`
    }

    return (
        <div className="col s12 m6 l4 meetingContainer">
            <div className="card hoverable meeting-card">
                <div className="card-header s-hflex">
                    <h6 className="py5 px10 translucent truncate">
                        {meeting.address}
                    </h6>
                </div>
                <div className="card-image stretch-background" style={meetingCardImageStyles}>
                    <a href={meetingLink} className="tooltipped btn-floating halfway-fab waves-effect waves-light blue darken-3" data-position="right" data-tooltip={useMessage("view")}>
                        <i className="material-icons">arrow_forward</i>
                    </a>
                    <RoleController allow={["moderator"]}>
                        <div className="top-right-element s-hflex">
                            {
                                !isOutdated(meeting.date) ? (
                                    <div className="clickable tooltipped waves-light blue-text text-darken-3 text-hoverable modal-trigger" data-position="right" data-tooltip={editMessage} data-target="edit-meeting-modal">
                                        <i className="material-icons small">edit</i>
                                    </div>
                                ) : null
                            }
                            <div className="clickable tooltipped waves-light orange-text text-hoverable" data-position="right" data-tooltip={`${useMessage("joined_users")}: ${meeting.usersCount} <br /> ${useMessage("was_present")}: ${meeting.presentUsersCount}`}>
                                <i className="material-icons small">help</i>
                            </div>
                        </div>
                    </RoleController>
                </div>
                <div className="card-content">
                    <span className="card-title truncate" style={{fontWeight: 400}}>{meeting.title}</span>

                    <div className="s-vflex mb5">
                        <div className="s-hflex">
                            <span className="weight-strong">{useMessage("topics")}: </span>
                            <span className="px5">{meeting.topicsCount}</span>
                        </div>
                        <div className="s-hflex">
                            <span className="weight-strong">{useMessage("participants")}: </span>
                            <span className="px5">{meeting.usersCount}</span>
                        </div>
                    </div>

                    <p className="truncate-4 translucent-3p">
                        {meeting.description}
                    </p>
                    <hr className="date-divider" />
                    <div className="s-hflex-end">
                        <span className="translucent">
                            {formatDate(meeting.date)}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Meeting;