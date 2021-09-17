import moment from "moment";

export const formatDate = (dateArray, format = "DD-MM-YYYY HH:mm") => {
    const array = dateArray.slice(0);
    array[1]--;
    return moment(array).format(format)
}

export const isOutdated = (dateArray) => {
    const array = dateArray.slice(0);
    array[1]--;
    return moment().isAfter(moment(array));
}