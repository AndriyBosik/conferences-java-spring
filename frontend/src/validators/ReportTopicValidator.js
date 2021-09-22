import { checkMinLength } from "./Validator";

export const validate = data => {
    const errors = [];

    checkMinLength(errors, data.title, {
        min: 5,
        field: "title"
    })

    return errors;
}