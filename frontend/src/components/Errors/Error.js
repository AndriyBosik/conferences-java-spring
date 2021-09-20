import React from "react";
import { useMessage } from "../../hooks/useMessage";

function Error({error}) {
    return (
        <h6 className="weight-normal">{useMessage(error.error, error.data)}</h6>
    );
}

export default Error;