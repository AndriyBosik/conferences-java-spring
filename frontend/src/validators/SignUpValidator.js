import { checkMinLength, checkEmail, checkMatch } from "./Validator";

export const validate = (data) => {
    const errors = [];
    
    checkMinLength(errors, data.login, {
        field: "login",
        min: 4
    });
    checkMinLength(errors, data.surname, {
        field: "surname",
        min: 2
    });
    checkMinLength(errors, data.name, {
        field: "name",
        min: 2
    });
    checkMinLength(errors, data.password, {
        field: "password",
        min: 5
    });
    checkEmail(errors, data.email, {});
    checkMatch(errors, data.password, data.confirmPassword, {
        first: "password",
        second: "confirm_password"
    })

    return errors;
}