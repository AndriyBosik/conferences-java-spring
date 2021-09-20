import React, { useState } from "react";
import { useEffect } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { getTopicProposals } from "./../../services/ProposalService";
import MeetingProposals from "./MeetingProposals";
import CircularPreloader from "./../CircularPreloader/CircularPreloader";
import M from "materialize-css";
import { getMessage } from "./../../handler/MessageHanlder";

function ProposedTopicsPage() {
    const proposedTopicsMessage = useMessage("proposed_topics");
    const nothingFoundMessage = useMessage("nothing_found");

    const [proposals, setProposals] = useState([]);
    const [loading, setLoading] = useState(false);

    const removeTopicProposal = (previousProposals, proposalId, topicProposalId) => {
        const proposals = [];
        for (const proposal of previousProposals) {
            proposals.push(proposal);
            if (proposal.id === proposalId) {
                proposals[proposals.length - 1].topicProposals = proposals[proposals.length - 1].topicProposals.filter(topicProposal => topicProposal.id !== topicProposalId);
            }
        }
        return proposals;
    }

    useEffect(() => {
        const fetchProposedTopics = async () => {
            setLoading(true);
            setProposals(await getTopicProposals());
            setLoading(false);
        }

        fetchProposedTopics();
    }, []);

    return (
        <div className="container">
            <div className="row">
                {
                    countTopicProposals(proposals) > 0 ? (
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
                                        proposals
                                            .filter(proposal => proposal.topicProposals.length > 0)
                                            .map(proposal => <MeetingProposals key={proposal.id} proposal={proposal} 
                                                successAcceptanceCallback={
                                                    topicProposal => {
                                                        M.toast({
                                                            html: getMessage("topic_was_added")
                                                        });
                                                        setProposals(proposals => removeTopicProposal(proposals, proposal.id, topicProposal.id));
                                                    }
                                                }
                                                successRejectionCallback={
                                                    topicProposal => {
                                                        M.toast({
                                                            html: getMessage("topic_proposal_was_rejected")
                                                        });
                                                        setProposals(proposals => removeTopicProposal(proposals, proposal.id, topicProposal.id));
                                                    }
                                                }/>)
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

const countTopicProposals = proposals => proposals.map(proposal => proposal.topicProposals.length).reduce((previousValue, currentValue) => previousValue + currentValue, 0);