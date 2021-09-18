import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useLink } from "../../hooks/useLink";
import { useMessage } from "../../hooks/useMessage";
import { pages } from "./../../constants/pages";
import { getProposedTopicsCount } from "./../../services/ProposalService";

function ProposedTopicsButton() {
    const [proposedTopicsCount, setProposedTopicsCount] = useState(0);

    useEffect(() => {
        const fetchProposedTopicsCount = async () => {
            setProposedTopicsCount(await getProposedTopicsCount());
        }

        fetchProposedTopicsCount();
    }, []);

    return (
        <Link to={useLink(pages.proposedTopics)} className="btn waves-effect waves-light modal-trigger">
            <div className="s-hflex">
                <span>{useMessage("proposed_topics")}</span>
                {
                    proposedTopicsCount > 0 ? (
                        <div className="s-vflex-center">
                            <span className="new badge orange" data-badge-caption="" style={{
                                paddingLeft: 10,
                                paddingRight: 10,
                                minWidth: "auto"
                            }}>
                                
                                {proposedTopicsCount}

                            </span>
                        </div>
                    ) : null
                }
            </div>
        </Link>
    );
}

export default ProposedTopicsButton;