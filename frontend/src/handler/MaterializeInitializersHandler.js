import M from "materialize-css";

export const initInputs = () => {
    M.updateTextFields();
}

export const initSelects = () => {
    var elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);
}

export const initTooltips = () => {
    var elems = document.querySelectorAll('.tooltipped');
    M.Tooltip.init(elems);
}

export const initModals = () => {
    var elems = document.querySelectorAll('.modal');
    M.Modal.init(elems);
}

export const initDatePickers = (localeData) => {
    const elems = document.querySelectorAll('.datepicker');
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const day = today.getDate();
    const weekdaysShort = localeData.weekdays.map(weekday => weekday.substr(0, 3));
    M.Datepicker.init(elems, {
        format: "dd-mm-yyyy",
        firstDay: 1,
        maxDate: new Date(year + 1000, month, day),
        i18n: {
            months: localeData.months,
            monthsShort: localeData.months.map(month => month.substr(0, 3)),
            weekdays: localeData.weekdays,
            weekdaysShort: weekdaysShort,
            weekdaysAbbrev: weekdaysShort,
            cancel: localeData.cancel,
            clear: localeData.clear
        }
    });
}

export const initTabs = () => {
    let tabs = document.querySelectorAll(".tabs");
    M.Tabs.init(tabs);
}