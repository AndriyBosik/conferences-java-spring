import React, { useState } from "react";
import { useEffect } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { getTopicProposals } from "./../../services/ProposalService";
import MeetingProposals from "./MeetingProposals";
import CircularPreloader from "./../CircularPreloader/CircularPreloader";

function ProposedTopicsPage() {
    const proposedTopicsMessage = useMessage("proposed_topics");
    const nothingFoundMessage = useMessage("nothing_found");

    const [proposals, setProposals] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchProposedTopics = async () => {
            setLoading(true);
            setProposals(await getTopicProposals());
            //setLoading(false);
        }

        fetchProposedTopics();
    }, []);

    return (
        <div className="container">
            <div className="row">
                {
                    proposals.length > 0 ? (
                        <>
                            <div className="col s12">
                                <div className="s-hflex">
                                    <div className="equal-flex">
                                        <h4 className="grey-text text-darken-2 mb0">{proposedTopicsMessage}</h4>
                                    </div>
                                </div>
                                <hr />
                            </div>
                            <div className="col s12 py10">
                                <ul className="collection speaker-proposals-collection">
                                    {
                                        proposals.map(proposal => <MeetingProposals key={proposal.id} proposal={proposal} />)
                                    }
                                </ul>
                            </div>
                        </>
                    ) : (
                        loading ? (
                            <div className="mt50 full-width s-hflex-center">
                                <CircularPreloader />
                            </div>
                        ) : (
                            <div className="full-width full-height center-align">
                                <div className="s-vflex-center fs50 translucent my20">
                                    {nothingFoundMessage}
                                </div>
                            </div>
                        )
                    )
                }
            </div>
        </div>
    );
}

export default ProposedTopicsPage;