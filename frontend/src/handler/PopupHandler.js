import M from "materialize-css";
import { getMessage } from "./MessageHanlder";

export const showPopup = (alias) => {
    M.toast({
        html: getMessage(alias)
    });
}