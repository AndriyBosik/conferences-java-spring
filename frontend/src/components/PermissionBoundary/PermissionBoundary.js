import React, { useState } from "react";
import PageNotFound from "./../PageNotFound/PageNotFound";
import { observe } from "mobx";
import LinkStoreInstance from "../../stores/LinkStore";
import { useEffect } from "react/cjs/react.development";
import { checkPermission } from "./../../handler/PermissionsHandler";

function PermissionBoundary({children}) {
    const [allowed, setAllowed] = useState(true);

    useEffect(() => {
        console.log(LinkStoreInstance.urlLink);
        setAllowed(checkPermission(LinkStoreInstance.urlLink, "guest"));
        return () => {};
    }, []);

    useEffect(() => {
        const disposer = observe(LinkStoreInstance, change => {
            setAllowed(checkPermission(LinkStoreInstance.urlLink, "guest"));
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