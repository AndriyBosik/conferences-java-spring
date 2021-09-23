import React, { useState } from "react";
import { useTitle } from "../../hooks/useTitle";
import RoleController from "./../RoleController/RoleController";
import { useMessage } from "./../../hooks/useMessage";
import { useEffect } from "react/cjs/react.development";
import CreateMeetingModal from "../shared/modals/CreateMeeting/CreateMeetingModal";
import EditMeetingModal from "../shared/modals/EditMeeting/EditMeetingModal";
import { initModals } from "../../handler/MaterializeInitializersHandler";
import MeetingsInformation from "./MeetingsInformation";
import { getAllMeetings } from "./../../services/MeetingService";

function MeetingsListPage() {
    useTitle("meetings");
    
    const [activeMeeting, setActiveMeeting] = useState({});

    const changeActiveMeeting = meeting => {
        setActiveMeeting(meeting);
        initModals();
    }

    useEffect(initModals, []);
    
    return (
        <>
            <div className="container">
                <div className="row">
                    <div className="col s12">
                        <div className="s-hflex">
                            <div className="equal-flex">
                                <h4 className="grey-text text-darken-2 mb0">{useMessage("meetings")}</h4>
                            </div>
                            <RoleController allow={["moderator"]}>
                                <div className="s-vflex-end">
                                    <a href="#meeting-form" className="btn waves-effect waves-light modal-trigger">
                                        {useMessage("add")}
                                        <i className="material-icons right">add</i>
                                    </a>
                                </div>
                            </RoleController>
                        </div>
                        <hr />
                    </div>

                    <MeetingsInformation meetingsFetcher={getAllMeetings} editCallback={changeActiveMeeting} />

                </div>
            </div>
            <RoleController allow={["moderator"]}>
                <CreateMeetingModal id="meeting-form" />
                <EditMeetingModal id="edit-meeting-modal" meeting={activeMeeting} />
            </RoleController>
        </>
    );
}

export default MeetingsListPage;