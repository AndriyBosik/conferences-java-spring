import React, { useState } from "react";
import PageNotFound from "./../PageNotFound/PageNotFound";
import { useEffect } from "react/cjs/react.development";
import { checkPermission } from "./../../handler/PermissionsHandler";
import { history } from "./../../handler/HistoryHandler";
import { parseUrl } from "./../../handler/LinkHandler";

function PermissionBoundary({children}) {
    const [, url] = parseUrl(window.location.pathname);

    const [allowed, setAllowed] = useState(false);

    useEffect(() => {
        const result = checkPermission(url);
        setAllowed(result);
    }, [url]);

    useEffect(() => {
        const disposer = history.listen(location => {
            const [, url] = parseUrl(location.pathname);
            setAllowed(checkPermission(url));
        });

        return disposer;
    }, []);

    if (allowed) {
        return children;
    }
    return <PageNotFound />;
}

export default PermissionBoundary;