import moment from "moment";

export const formatDate = (dateArray, format = "DD-MM-YYYY HH:mm") => {
    if (dateArray.length <= 0) {
        return "";
    }
    const array = dateArray.slice(0);
    array[1]--;
    return moment(array).format(format)
}

export const isOutdated = (dateArray) => {
    if (dateArray.length <= 0) {
        return true;
    }
    const array = dateArray.slice(0);
    array[1]--;
    return moment().isAfter(moment(array));
}

export const parseDateToParts = (fullDate) => {
    if (typeof fullDate === "undefined" || fullDate == null || fullDate.length < 5) {
        return ["00-00-0000", "00", "00"];
    }
    return [
        addZeroToBegin(fullDate[2]) + "-" + addZeroToBegin(fullDate[1]) + "-" + fullDate[0],
        fullDate[3],
        fullDate[4]
    ]
}

const addZeroToBegin = (value) => {
    value += "";
    if (value.length < 2) {
        return "0" + value;
    }
    return value;
}