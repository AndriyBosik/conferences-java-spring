import React from "react";
import { useMessage } from "../../hooks/useMessage";

function ResultMessage({resultMessage, containerClass = ""}) {
    const message = useMessage(resultMessage.message);

    return (
        resultMessage.status === "" ? null : (
            <div className={containerClass}>
                <blockquote className={resultMessage.status === "success" ? "teal-blockquote" : ""}>
                    {message}
                </blockquote>
            </div>
        )
    );
}

export default ResultMessage;