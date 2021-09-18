import M from "materialize-css";

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
    var elems = document.querySelectorAll('.datepicker');
    M.Datepicker.init(elems);
}

export const initTabs = () => {
    let tabs = document.querySelectorAll(".tabs");
    M.Tabs.init(tabs);
}