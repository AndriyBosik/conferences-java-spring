import React from "react";

function SpeakerProposal({speakerProposal}) {
    const handleSubmit = event => {
        event.preventDefault();
    }

    return (
        <form method="post" className="col s6 m3 my5" onSubmit={handleSubmit}>
            <div className="proposal full-width clickable">
                <div className="z-depth-1">
                    <div className="s-hflex">
                        <div className="proposal-avatar">
                            <img src={`/shared/images/avatars/${speakerProposal.imagePath}`} alt="" className="full-width full-height" />
                        </div>
                        <span className="username s-vflex-center mx10 weight-strong">{speakerProposal.name} {speakerProposal.surname}</span>
                    </div>
                </div>
            </div>
        </form>
    );
}

export default SpeakerProposal;