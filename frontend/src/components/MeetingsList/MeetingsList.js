import React from "react";
import Meeting from "./Meeting";
import Pagination from "./../Pagination/Pagination";

function MeetingsList({meetings}) {
    return (
        <div className="meetings-list-area col s12" style={{paddingLeft: 0, paddingRight: 0}}>
            {
                meetings.map(meeting => <Meeting key={meeting.id} meeting={meeting} />)
            }
            <Pagination currentPage={2} pagesCount={3} />
        </div>
    );
}

export default MeetingsList;