import { checkMinLength } from "./Validator";
import { validate as validateMeetingUpdatableData } from "./MeetingUpdatableDataValidator";

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
    errors.push(...validateMeetingUpdatableData(meeting));

    return errors;
}