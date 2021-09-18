import React from "react";
import MeetingProposal from "./MeetingProposal";

function ProposalsTab({id, proposals, proposalHandler}) {
    return (
        <div id={id} className="col s12">
            <div className="py10">
                {
                    proposals.length > 0 ? (
                        <ul className="collection speaker-proposals-collection">
                            {
                                proposals.map(proposal => <MeetingProposal key={proposal.id} proposal={proposal} proposalHandler={proposalHandler} />)
                            }
                        </ul>
                    ) : null
                }
            </div>
        </div>
    );
}

export default ProposalsTab;