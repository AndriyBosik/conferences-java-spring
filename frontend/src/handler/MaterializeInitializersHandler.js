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

export const initDatePickers = () => {
    const elems = document.querySelectorAll('.datepicker');
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const day = today.getDate();
    M.Datepicker.init(elems, {
        format: "dd-mm-yyyy",
        firstDay: 1,
        maxDate: new Date(year + 1000, month, day)
    });
}

export const initTabs = () => {
    let tabs = document.querySelectorAll(".tabs");
    M.Tabs.init(tabs);
}