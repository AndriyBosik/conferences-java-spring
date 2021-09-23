import { useMessage } from "./useMessage";

export const useDatePickerLocalization = () => {
    return {
        months: [
            useMessage("january"),
            useMessage("february"),
            useMessage("march"),
            useMessage("april"),
            useMessage("may"),
            useMessage("june"),
            useMessage("july"),
            useMessage("august"),
            useMessage("september"),
            useMessage("october"),
            useMessage("november"),
            useMessage("december"),
        ],
        weekdays: [
            useMessage("sunday"),
            useMessage("monday"),
            useMessage("tuesday"),
            useMessage("wednesday"),
            useMessage("thursday"),
            useMessage("friday"),
            useMessage("saturday"),
        ],
        cancel: useMessage("cancel"),
        clear: useMessage("clear")
    };
}