import React, { useState } from "react";
import PageNotFound from "./../PageNotFound/PageNotFound";
import { useEffect } from "react/cjs/react.development";
import { checkPermission } from "./../../handler/PermissionsHandler";
import { history } from "./../../handler/HistoryHandler";
import { parseUrl } from "./../../handler/LinkHandler";

function PermissionBoundary({children}) {
    const [, url] = parseUrl(window.location.pathname);

    const [allowed, setAllowed] = useState(0);

    useEffect(() => {
        const result = checkPermission(url);
        setAllowed(result ? 1 : -1);
    }, [url]);

    useEffect(() => {
        const disposer = history.listen(location => {
            const [, url] = parseUrl(location.pathname);
            setAllowed(checkPermission(url) ? 1 : -1);
        });

        return disposer;
    }, []);

    if (allowed === 1) {
        return children;
    } else if (allowed === -1) {
        return <PageNotFound />;
    }
    return <div>Loading...</div>
}

export default PermissionBoundary;