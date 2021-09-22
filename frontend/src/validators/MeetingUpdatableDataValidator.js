import { checkMinLength, checkEmpty, checkFutureDate } from "./Validator";
import moment from "moment";

export const validate = meeting => {
    const errors = [];

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
        checkFutureDate(errors, moment([dateParts[2], dateParts[1] - 1, dateParts[0], meeting.hours, meeting.minutes]), {
            field: "date"
        });
    }

    return errors;
}