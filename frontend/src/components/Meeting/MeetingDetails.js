import React from "react";
import { useMessage } from "./../../hooks/useMessage";
import { formatDate } from "./../../handler/DateHandler";

function MeetingDetails({meeting, usersPresence}) {
    return (
        <>
            <div className="z-depth-2 stretch-background" style={{
                height: 300,
                backgroundImage: `url('http://localhost:8080/api/images/meetings/${meeting.imagePath}')`
            }}></div>

            <div className="full-width py15 meeting-additional-data">
                <div className="meeting-where s-hflex my5">
                    <span className="weight-strong s-hflex">
                        <i className="material-icons pr5 float-left">location_on</i>
                        {useMessage("where")}:
                    </span>
                    <span className="translucent-3p px5">
                        {meeting.address}
                    </span>
                </div>
                <div className="meeting-when s-hflex my5">
                    <span className="weight-strong s-hflex">
                        <i className="material-icons pr5 float-left">access_time</i>
                        {useMessage("when")}:
                    </span>
                    <span className="translucent-3p px5">
                        {formatDate(meeting.date)}
                    </span>
                </div>
                <div className="meeting-where s-hflex my5">
                    <span className="weight-strong s-hflex">
                        <i className="material-icons pr5 float-left">title</i>
                        {useMessage("topics")}:
                    </span>
                    <span className="translucent-3p px5">
                        {meeting.reportTopics.length}
                    </span>
                </div>
                <div className="meeting-where s-hflex my5">
                    <span className="weight-strong s-hflex">
                        <i className="material-icons pr5 float-left">people</i>
                        {useMessage("participants")}:
                    </span>
                    <span className="translucent-3p px5">
                        {usersPresence.usersCount}
                    </span>
                </div>
            </div>
        </>
    );
}

export default MeetingDetails;