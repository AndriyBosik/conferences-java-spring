import moment from "moment";
import { checkMinLength, checkFutureDate, checkEmpty } from "./Validator";

export const validate = meeting => {
    const errors = [];

    checkMinLength(errors, meeting.title, {
        field: "title",
        min: 5
    });
    checkMinLength(errors, meeting.description, {
        field: "description",
        min: 10
    });
    checkMinLength(errors, meeting.address, {
        field: "address",
        min: 5
    });
    if (meeting.date === "") {
        checkEmpty(errors, meeting.date, {
            field: "date"
        })
    } else {
        const dateParts = meeting.date.split("-");
        checkFutureDate(errors, moment([dateParts[2], dateParts[1], dateParts[0], meeting.hours, meeting.minutes], {
            field: "date"
        }));
    }

    return errors;
}