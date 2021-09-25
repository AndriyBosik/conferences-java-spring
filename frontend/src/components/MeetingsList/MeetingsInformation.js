import React, { useEffect, useState } from "react";
import MeetingsFilters from "./MeetingsFilters";
import LinearPreloader from "../LinearPreloader/LinearPreloader";
import MeetingsList from "./MeetingsList";
import Pagination from "../Pagination/Pagination";
import { useMessage } from "../../hooks/useMessage";
import { initTooltips } from "../../handler/MaterializeInitializersHandler";
import { DEFAULT_MEETINGS_FILTER, MEETINGS_COUNT } from "../../constants/defaults";
import { showPopup } from "./../../handler/PopupHandler";

function MeetingsInformation({meetingsFetcher, editCallback = () => {}}) {
    const pageNotFoundMessage = useMessage("page_not_found");
    const [loading, setLoading] = useState(true);
    const [filters, setFilters] = useState(DEFAULT_MEETINGS_FILTER);
    const [currentPage, setCurrentPage] = useState(1);
    const [meetings, setMeetings] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const fetchMeetings = async (page, items, filters) => {
            setLoading(true);
            const meetingsData = await meetingsFetcher(page, items, filters);
            if (meetingsData == null) {
                showPopup("error_happened");
                return;
            }
            setMeetings(meetingsData.content);
            setTotalPages(meetingsData.totalPages);
            setLoading(false);
            initTooltips();
        };

        fetchMeetings(currentPage, MEETINGS_COUNT, filters);
    }, [currentPage, filters, meetingsFetcher]);

    return (
        <>
            <MeetingsFilters handler={setFilters} />
            {
                loading ? (
                    <LinearPreloader />
                ) : (
                    meetings.length > 0 ? (
                        <>
                            <MeetingsList meetings={meetings} editCallback={editCallback} />
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
                )
            }
        </>
    );
}

export default MeetingsInformation;