import React, { useRef, useState } from "react";
import { useEffect } from "react/cjs/react.development";
import { initDatePickers } from "../../../../handler/MaterializeInitializersHandler";
import { useLink } from "../../../../hooks/useLink";
import { useMessage } from "../../../../hooks/useMessage";
import { useDatePickerLocalization } from "./../../../../hooks/useDatePickerLocalization";
import { createMeeting } from "./../../../../services/MeetingService";
import { showPopup } from "./../../../../handler/PopupHandler";
import Errors from "./../../../Errors/Errors";
import M from "materialize-css";

function CreateMeetingModal({id}) {
    const localization = useDatePickerLocalization();

    const fileField = useRef();
    const dateField = useRef();

    const [hours, setHours] = useState(0);
    const [minutes, setMinutes] = useState(0);
    const [title, setTitle] = useState("");
    const [address, setAddress] = useState("");
    const [description, setDescription] = useState("");
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        initDatePickers(localization);
    }, [localization]);

    const handleSubmit = async event => {
        event.preventDefault();

        const data = {
            date: dateField.current.value,
            hours, minutes, title, address, description
        }

        if (fileField.current.files.length === 0) {
            showPopup("choose_file");
            return;
        }

        const result = await createMeeting(fileField.current.files[0], data);
        
        if (result.errors.length === 0) {
            if (result.data) {
                showPopup("meeting_created");
                M.Modal.getInstance(document.getElementById(id)).close();
            } else {
                showPopup("error_happened");
            }
        } else {
            setErrors(result.errors);
        }
    };

    return (
        <div id={id} className="modal">
            <form action={useLink("/meetings/create")} method="post" encType="multipart/form-data" onSubmit={handleSubmit}>
                <div className="modal-content row">
                    <h5 className="col s12">{useMessage("create_meeting")}</h5>

                    <Errors errors={errors} containerClass="col s12" />

                    <div className="input-field col s12 m6">
                        <input id="meeting-title" type="text" value={title} onChange={event => setTitle(event.target.value)} />
                        <label htmlFor="meeting-title">{useMessage("title")}</label>
                    </div>

                    <div className="input-field col s12 m6">
                        <input id="meeting-address" type="text" value={address} onChange={event => setAddress(event.target.value)} />
                        <label htmlFor="meeting-address">{useMessage("address")}</label>
                    </div>

                    <div className="col s12 m6 s-hflex-center">
                        <input type="text" placeholder={useMessage("select_date")} className="datepicker" ref={dateField} />
                        <div className="col">
                            <input type="number" min="0" max="23" value={hours} onChange={event => setHours(event.target.value)} className="center-align" />
                        </div>
                        <span className="s-vflex-center weight-normal time-divider">:</span>
                        <div className="col">
                            <input type="number" min="0" max="59" value={minutes} onChange={event => setMinutes(event.target.value)} className="center-align" />
                        </div>
                    </div>

                    <div className="col s12 m6">
                        <div className="file-field input-field" style={{margin: 0}}>
                            <div className="btn">
                                <span>{useMessage("photo")}</span>
                                <input type="file" name="image_path" accept="image/*" ref={fileField} />
                            </div>
                            <div className="file-path-wrapper">
                                <input type="text" className="file-path validate" />
                            </div>
                        </div>
                    </div>

                    <div className="input-field col s12">
                        <textarea id="meeting-description" className="materialize-textarea" name="description" value={description} onChange={event => setDescription(event.target.value)}></textarea>
                        <label htmlFor="meeting-description">{useMessage("description")}</label>
                    </div>

                </div>
                <div className="modal-footer">
                    <div className="col px20">
                        <button type="submit" className="btn waves-effect waves-light">
                            {useMessage("confirm")}
                            <i className="material-icons right">check</i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default CreateMeetingModal;