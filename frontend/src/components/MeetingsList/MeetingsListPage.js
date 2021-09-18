import React from "react";
import { useTitle } from "../../hooks/useTitle";
import RoleController from "./../RoleController/RoleController";
import Message from "./../Message/Message";
import { useEffect } from "react/cjs/react.development";
import CreateMeetingModal from "../shared/modals/CreateMeetingModal";
import { initModals } from "../../handler/MaterializeInitializersHandler";
import MeetingsInformation from "./MeetingsInformation";
import { getAllMeetings } from "./../../services/MeetingService";

function MeetingsListPage() {
    useTitle("meetings");
    
    useEffect(initModals, []);
    
    return (
        <>
            <div className="container">
                <div className="row">
                    <div className="col s12">
                        <div className="s-hflex">
                            <div className="equal-flex">
                                <h4 className="grey-text text-darken-2 mb0"><Message alias="meetings" /></h4>
                            </div>
                            <RoleController allow={["moderator"]}>
                                <div className="s-vflex-end">
                                    <a href="#meeting-form" className="btn waves-effect waves-light modal-trigger">
                                        <Message alias="add" />
                                        <i className="material-icons right">add</i>
                                    </a>
                                </div>
                            </RoleController>
                        </div>
                        <hr />
                    </div>

                    <MeetingsInformation meetingsFetcher={getAllMeetings} />

                </div>
            </div>
            <RoleController allow={["moderator"]}>
                <CreateMeetingModal id="meeting-form" />
            </RoleController>
        </>
    );
}

export default MeetingsListPage;