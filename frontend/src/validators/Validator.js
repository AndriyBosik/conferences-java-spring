import moment from "moment";

const regex = /^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$/;

export const checkEmpty = (errors, value, data) => {
    if (value.trim().length === 0) {
        errors.push({
            error: "required",
            data: data
        });
    }
}

export const checkMinLength = (errors, value, data) => {
    if (value.trim().length < data.min) {
        errors.push({
            error: "required_min_length",
            data: data
        });
    }
}

export const checkEmail = (errors, value, data) => {
    if (!regex.test(value)) {
        errors.push({
            error: "invalid_email",
            data: data
        });
    }
}

export const checkMatch = (errors, firstValue, secondValue, data) => {
    if (firstValue !== secondValue) {
        errors.push({
            error: "fields_not_matches",
            data: data
        });
    }
}

export const checkFutureDate = (errors, date, data) => {
    if (moment().isAfter(date)) {
        errors.push({
            error: "invalid_future_date",
            data: data
        });
    }
}