import React, { useState, useEffect } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { useTitle } from "./../../hooks/useTitle";
import { initTabs } from "./../../handler/MaterializeInitializersHandler";
import { getUser } from "./../../handler/StorageHandler";
import { getForSpeakerProposals, getSpeakerProposals } from "./../../services/ProposalService";
import ProposalsTab from "./ProposalsTab";
import SpeakerProposalItem from "./SpeakerProposalItem";
import ModeratorProposalItem from "./ModeratorProposalItem";

function ProposalsPage() {
    useTitle("proposals");

    const user = getUser();

    const [myProposals, setMyProposals] = useState([]);
    const [forMeProposals, setForMeProposals] = useState([]);

    const removeReportTopicFromProposals = reportTopic => {
        setForMeProposals(previousForMeProposals => removeModeratorProposal(previousForMeProposals, reportTopic));
    }

    useEffect(() => {
        const fetchMyProposals = async () => {
            setMyProposals(await getSpeakerProposals(user.id));
        }
        
        const fetchForMeProposals = async () => {
            setForMeProposals(await getForSpeakerProposals(user.id));
        }

        fetchMyProposals();
        fetchForMeProposals();

        initTabs();
    }, []);

    return (
        <div className="container">
            <div className="row">
                <div className="col s12">
                    <h4 className="grey-text text-darken-2 mb0">{useMessage("proposals")}</h4>
                    <hr />
                </div>
                <div className="col s12">
                    <ul className="tabs m-blue m-darken-3">
                        <li className="tab col s6">
                            <a href="#my" className="blue-text text-darken-3">{useMessage("my")}</a>
                        </li>
                        <li className="tab col s6">
                            <a href="#for-me" className="blue-text text-darken-3">{useMessage("for_me")}</a>
                        </li>
                        <li className="indicator"></li>
                    </ul>
                </div>
                <ProposalsTab id="my" proposals={myProposals} proposalHandler={proposal => <SpeakerProposalItem key={proposal.id} proposal={proposal} />} />
                <ProposalsTab id="for-me" proposals={forMeProposals} proposalHandler={reportTopic => <ModeratorProposalItem key={reportTopic.id} reportTopic={reportTopic} successfulRejectionCallback={removeReportTopicFromProposals} successfulAcceptanceCallback={removeReportTopicFromProposals} />} />
            </div>
        </div>
    );
}

export default ProposalsPage;

const removeModeratorProposal = (previousProposals, reportTopic) => {
    const proposals = [];
    for (const proposal of previousProposals) {
        const reportTopics = proposal.reportTopics.filter(topic => topic.id !== reportTopic.id);
        if (reportTopics.length > 0) {
            proposal.reportTopics = reportTopics;
            proposals.push(proposal);
        }
    }
    return proposals;
}