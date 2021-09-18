import React from "react";
import Meeting from "./Meeting";

function MeetingsList({meetings}) {
    return (
        <div className="meetings-list-area col s12" style={{paddingLeft: 0, paddingRight: 0}}>
            {
                meetings.map(meeting => <Meeting key={meeting.id} meeting={meeting} />)
            }
        </div>
    );
}

export default MeetingsList;