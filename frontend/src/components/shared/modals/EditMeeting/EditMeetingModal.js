import React, { useState, useRef, useEffect } from "react";
import { editMeeting } from "../../../../services/MeetingService";
import { useMessage } from "./../../../../hooks/useMessage";
import { initDatePickers, initInputs } from "./../../../../handler/MaterializeInitializersHandler";
import { Redirect } from "react-router";
import { generateUrl } from "./../../../../handler/LinkHandler";
import { parseDateToParts } from "./../../../../handler/DateHandler";
import { format } from "./../../../../handler/StringHandler";
import Errors from "./../../../Errors/Errors";
import M from "materialize-css";
import { useDatePickerLocalization } from "../../../../hooks/useDatePickerLocalization";

function EditMeetingModal({meeting, id=""}) {
    const dateFieldId = "date-field";
    const localization = useDatePickerLocalization();

    const editMeetingMessage = useMessage("edit_meeting");
    const addressMessage = useMessage("address");
    const selectDateMessage = useMessage("select_date");
    const confirmMessage = useMessage("confirm");

    const [parsedDate, parsedHours, parsedMinutes] = parseDateToParts(meeting.date);

    const [address, setAddress] = useState(meeting.address);
    const [hours, setHours] = useState(parsedHours);
    const [minutes, setMinutes] = useState(parsedMinutes);
    const [meetingUpdated, setMeetingUpdated] = useState(false);
    const [meetingLink, setMeetingLink] = useState("");
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        initInputs();
        initDatePickers(localization);
        if (document.getElementById(dateFieldId) != null) {
            document.getElementById(dateFieldId).value = parsedDate;
        }
    }, [parsedDate, localization]);

    useEffect(() => {
        setAddress(meeting.address);
    }, [meeting]);

    useEffect(() => {
        setMeetingLink(generateUrl(format("/meetings/show/{id}", {id: meeting.id})));
        const [parsedDate, parsedHours, parsedMinutes] = parseDateToParts(meeting.date);
        setHours(parsedHours);
        setMinutes(parsedMinutes);
        if (document.getElementById(dateFieldId) != null) {
            document.getElementById(dateFieldId).value = parsedDate;
        }
        initInputs();
        initDatePickers(localization);
    }, [meeting, localization]);

    const edit = async () => {
        const data = {
            id: meeting.id,
            address,
            hours: hours + "",
            minutes: minutes + "",
            date: document.getElementById(dateFieldId).value
        };

        const result = await editMeeting(data);

        setErrors(result.errors);

        if (result.data) {
            const modal = M.Modal.getInstance(document.getElementById(id));
            modal.close();
            setMeetingUpdated(true);
        }
    }

    return (
        meetingUpdated ? (
            <Redirect to={meetingLink} />
        ) : (
            <div id={id} className="modal height-70">
                <div className="modal-content row">
                    <h5 className="col s12">{editMeetingMessage}</h5>

                    <Errors errors={errors} containerClass="col s12" />

                    <div className="input-field col s12">
                        <input id="address" type="text" value={address} onChange={event => setAddress(event.target.value)} />
                        <label htmlFor="address">{addressMessage}</label>
                    </div>

                    <div className="col s12 s-hflex-center">
                        <input id={dateFieldId} type="text" placeholder={selectDateMessage} className="datepicker" />
                        <div className="col">
                            <input type="number" min="0" max="23" className="center-align" value={hours} onChange={event => setHours(event.target.value)} />
                        </div>
                        <span className="s-vflex-center weight-normal time-divider">:</span>
                        <div className="col">
                            <input type="number" min="0" max="59" className="center-align" value={minutes} onChange={event => setMinutes(event.target.value)} />
                        </div>
                    </div>
                </div>

                <div className="modal-footer">
                    <div className="col px20">
                        <button type="button" className="btn waves-effect waves-light" onClick={edit}>
                            {confirmMessage}
                            <i className="material-icons right">check</i>
                        </button>
                    </div>
                </div>
            </div>
        )
    );
}

export default EditMeetingModal;