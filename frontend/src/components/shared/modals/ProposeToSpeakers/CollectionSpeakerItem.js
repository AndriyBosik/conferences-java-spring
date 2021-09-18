import React from "react";
import { useMessage } from "../../../../hooks/useMessage";

function CollectionSpeakerItem({speaker}) {
    const handleSubmit = event => {
        event.preventDefault();
    }

    return (
        <li className="collection-item s-hflex">
            <div className="z-depth-1 user-avatar stretch-background">
                <img src={`/shared/images/avatars/${speaker.imagePath}`} alt="" className="circle full-width full-height" />
            </div>
            <span className="title weight-normal s-vflex-center mx10 equal-flex">
                {speaker.name} {speaker.surname}
            </span>
            <span className="secondary-content s-vflex-center">
                <form method="post" className="m0 proposalForm" onSubmit={handleSubmit}>
                    <button type="submit" className="btn waves-effect waves-light">
                        {useMessage("propose")}
                        <i className="material-icons right">check</i>
                    </button>
                </form>
            </span>
        </li>
    );
}

export default CollectionSpeakerItem;