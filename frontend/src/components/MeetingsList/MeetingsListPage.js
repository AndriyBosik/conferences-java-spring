import React from "react";
import { useTitle } from "../../hooks/useTitle";
import RoleController from "./../RoleController/RoleController";
import Message from "./../Message/Message";
import MeetingsList from "./MeetingsList";
import MeetingsFilters from "./MeetingsFilters";
import LinearPreloader from "./../LinearPreloader/LinearPreloader";
import { getAllMeetings } from "./../../services/MeetingService";
import { useEffect, useState } from "react/cjs/react.development";
import { useMessage } from "../../hooks/useMessage";
import Pagination from "../Pagination/Pagination";
import { defaultMeetingsFilter } from "../../constants/defaults";
import { initTooltips, initModals } from "../../handler/MaterializeInitializersHandler";
import CreateMeetingModal from "../shared/modals/CreateMeetingModal";

const MEETINGS_COUNT = 12;

function MeetingsListPage() {
    const pageNotFoundMessage = useMessage("page_not_found");
    useTitle("meetings");
    const [loading, setLoading] = useState(true);
    const [filters, setFilters] = useState(defaultMeetingsFilter);
    const [currentPage, setCurrentPage] = useState(1);
    const [meetings, setMeetings] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const fetchMeetings = async (page, items, filters) => {
        setLoading(true);
        const meetingsData = await getAllMeetings(page, items, filters);
        setMeetings(meetingsData.content);
        setTotalPages(meetingsData.totalPages);
        setLoading(false);
        initTooltips();
    };
    
    useEffect(initModals, []);

    useEffect(() => {
        fetchMeetings(currentPage, MEETINGS_COUNT, filters);
    }, [currentPage, filters]);
    
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

                    <MeetingsFilters handler={setFilters} />
                    {loading ? (
                        <LinearPreloader />
                    ) : (
                        meetings.length > 0 ? (
                            <>
                                <MeetingsList meetings={meetings} />
                                <Pagination
                                    totalPages={totalPages}
                                    currentPage={currentPage}
                                    paginationClass="col s12"
                                    pageClickHandler={setCurrentPage} />
                            </>
                        ) : (
                            <p className="center-align translucent large-text">
                                {pageNotFoundMessage}
                            </p>
                        )
                    )}
                </div>
            </div>
            <RoleController allow={["moderator"]}>
                <CreateMeetingModal id="meeting-form" />
            </RoleController>
        </>
    );
}

export default MeetingsListPage;