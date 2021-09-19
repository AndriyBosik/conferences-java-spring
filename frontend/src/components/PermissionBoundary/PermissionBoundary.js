import React, { useState } from "react";
import PageNotFound from "./../PageNotFound/PageNotFound";
import { observe } from "mobx";
import LinkStoreInstance from "../../stores/LinkStore";
import { useEffect } from "react/cjs/react.development";
import { checkPermission } from "./../../handler/PermissionsHandler";
import { history } from "./../../handler/HistoryHandler";

function PermissionBoundary({children}) {
    const [allowed, setAllowed] = useState(true);

    useEffect(() => {
        setAllowed(checkPermission(LinkStoreInstance.urlLink));
        return () => {};
    }, []);

    useEffect(() => {
        history.listen(() => {
            checkPermission(LinkStoreInstance.urlLink);
        });
        const disposer = observe(LinkStoreInstance, change => {
            setAllowed(checkPermission(LinkStoreInstance.urlLink));
            return () => {};
        });

        return disposer;
    }, []);

    if (allowed) {
        return children;
    }
    return <PageNotFound />;
}

export default PermissionBoundary;