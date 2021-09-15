import LinkStore from "./../stores/LinkStore";

export const generateUrl = (to, lang = null) => {
    if (lang == null) {
        return "/" + LinkStore.urlLanguage + to;
    }
    return "/" + lang + to;
}