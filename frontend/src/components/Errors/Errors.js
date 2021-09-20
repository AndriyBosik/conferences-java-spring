import React from "react";
import Error from "./Error";

function Errors({errors, containerClass = ""}) {
    return (
        errors.length > 0 ? (
            <div className={containerClass}>
                <blockquote className="red-text">
                    {
                        errors.map((error, index) => <Error key={index} error={error} />)
                    }
                </blockquote>
            </div>
        ) : null
    );
}

export default Errors;