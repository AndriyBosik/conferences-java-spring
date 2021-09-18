import React from 'react';
import { Link } from 'react-router-dom';
import { formatDate } from '../../handler/DateHandler';
import { useLink } from "./../../hooks/useLink";
import { format } from "./../../handler/StringHandler";
import TopicProposal from './TopicProposal';

function MeetingProposals({proposal}) {
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
                            backgroundImage: `url('/shared/images/meetings/${proposal.imagePath}')`}}></div>
                    </div>
                    <div className="translucent s-hflex-end equal-flex">
                        <div className="s-vflex-center fs20">
                            {formatDate(proposal.date)}
                        </div>
                    </div>
                </Link>
            </li>

            {
                proposal.topicProposals.map(topic => <TopicProposal key={topic.id} topic={topic} />)
            }
        </>
    );
}

export default MeetingProposals;