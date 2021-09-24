import React from "react";
import { useTitle } from "../../hooks/useTitle";

function PageNotFound() {
    useTitle("error");

    return (
        <div>Page not found</div>
    );
}

export default PageNotFound;