import React from "react";
import { Link } from "react-router-dom";
import { format } from "../../handler/StringHandler";
import { useLink } from "../../hooks/useLink";
import { formatDate } from "../../handler/DateHandler";

function MeetingProposal({proposal, proposalHandler}) {
    const meetingLink = useLink(format("/meetings/show/{id}", {id: proposal.id}));

    return (
        <>
            <li className="collection-item s-hflex">
                <Link to={meetingLink} className="full-width s-hflex grey-text text-darken-3">
                    <div className="s-vflex-center">
                        <span className="weight-normal" style={{fontSize: 22}}>{proposal.title}</span>
                    </div>
                    <div className="s-vflex-center mx10">
                        <div className="circle stretch-background user-avatar z-depth-1" style={{
                            backgroundImage: `url('/shared/images/meetings/${proposal.imagePath}`
                        }}></div>
                    </div>
                    <div className="translucent s-hflex-end equal-flex">
                        <div className="s-vflex-center fs20">
                            {formatDate(proposal.date)}
                        </div>
                    </div>
                </Link>
            </li>

            {
                proposal.reportTopics.map(proposalHandler)
            }
        </>
    );
}

export default MeetingProposal;