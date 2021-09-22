import React from "react";

function CircularPreloader({color = "blue", size = "big"}) {
    return (
        <div className={`preloader-wrapper ${size} active`}>
            <div className={`spinner-layer spinner-${color}-only`}>
                <div className="circle-clipper left">
                    <div className="circle"></div>
                </div>
                <div className="gap-patch">
                    <div className="circle"></div>
                </div>
                <div className="circle-clipper right">
                    <div className="circle"></div>
                </div>
            </div>
        </div>
    );
}

export default CircularPreloader;