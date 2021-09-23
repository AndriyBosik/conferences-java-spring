import React from "react";
import { getUser } from "../../handler/StorageHandler";
import { useMessage } from "../../hooks/useMessage";
import { useTitle } from "../../hooks/useTitle";
import MeetingsInformation from "../MeetingsList/MeetingsInformation";
import { getAllMeetingsForSpeaker } from "./../../services/MeetingService";

function SpeakerMeetingsPage() {
    useTitle("meetings");

    const user = getUser();
    const meetingsFetcher = getAllMeetingsForSpeaker(user.id);

    return (
        <div className="container">
            <div className="row">
                <div className="col s12">
                    <h4 className="grey-text text-darken-2 mb0">{useMessage("my_meetings")}</h4>
                    <hr />
                </div>

                <MeetingsInformation meetingsFetcher={meetingsFetcher} />

            </div>
        </div>
    );
}

export default SpeakerMeetingsPage;