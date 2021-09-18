import React from "react";

function CircularPreloader({color = "blue"}) {
    return (
        <div className="preloader-wrapper big active">
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