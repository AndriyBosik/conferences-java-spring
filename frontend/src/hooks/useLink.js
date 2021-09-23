import { useLocation } from "react-router";
import { parseUrl } from "./../handler/LinkHandler";

export const useLink = (to, language = null) => {
    const location = useLocation();

    const [lang, url] = parseUrl(location.pathname);
    
    return "/" + (language == null ? lang : language) + (to === "" ? url : to);
}