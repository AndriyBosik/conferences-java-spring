import { makeAutoObservable } from "mobx";

import { localization } from "../constants/localization";

class LinkStore {
    urlLanguage = localization.defaultLanguage;
    urlLink = "/";

    constructor() {
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