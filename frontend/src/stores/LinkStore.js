import { makeAutoObservable } from "mobx";
import { parseUrl } from "../handler/LinkHandler";

class LinkStore {
    urlLanguage;
    urlLink;

    constructor() {
        [this.urlLanguage, this.urlLink] = parseUrl(window.location.pathname);
        makeAutoObservable(this);
    }

    updateUrlLanguage(urlLanguage) {
        this.urlLanguage = urlLanguage;
    }

    updateUrlLink(urlLink) {
        this.urlLink = urlLink;
    }
}

const LinkStoreInstance = new LinkStore();

export default LinkStoreInstance;