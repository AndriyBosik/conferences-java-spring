import React, { useEffect, useState } from "react";
import { Route, Switch } from "react-router";
import App from "./../../App";
import LoginPage from "./../Login/LoginPage";
import SignUpPage from "./../SignUp/SignUpPage";
import { useLink } from "../../hooks/useLink";
import { pages } from "../../constants/pages";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import Navbar from "../shared/Navbar/Navbar";
import MeetingPage from "../Meeting/MeetingPage";
import ProfilePage from "../Profile/ProfilePage";
import MeetingsListPage from "../MeetingsList/MeetingsListPage";
import ProposalsPage from "../Proposals/ProposalsPage";
import ProposedTopicsPage from "../ProposedTopics/ProposedTopicsPage";
import SpeakerMeetingsPage from "../SpeakerMeetings/SpeakerMeetingsPage";
import Footer from "../shared/Footer/Footer";
import { refreshToken } from "../../handler/TokenHandler";
import './../../App.css';
import 'materialize-css/dist/css/materialize.min.css';
import './../../shared/css/common.css';
import './../../shared/css/indentations.css';
import './../../shared/css/materialize-extensions.css';
import './../../shared/css/site.css';

function AppRoutes() {
    const homePageLink = useLink(pages.home);
    const signUpPageLink = useLink(pages.signUp);
    const meetingPageLink = useLink(pages.meeting);
    const profilePageLink = useLink(pages.profile);
    const allMeetingsPageLink = useLink(pages.allMeetings);
    const proposalsPageLink = useLink(pages.proposals);
    const proposedTopicsPageLink = useLink(pages.proposedTopics);
    const speakerMeetingsPageLink = useLink(pages.speakerMeetings);
    
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const tokenFetcher = async () => {
            await refreshToken();
            setLoading(false);
        }

        tokenFetcher()
    }, []);

    return (
        loading ? (
            null
        ) : (
            <PermissionBoundary>
                <App>
                    <Switch>
                        <Route path={[homePageLink, "/"]} exact>
                            <LoginPage />
                        </Route>
                        <Route path={signUpPageLink}>
                            <SignUpPage />
                        </Route>
                        <Route>
                            <Navbar />
                            <main className="flex-auto">
                                <Route path={meetingPageLink} exact>
                                    {
                                        ({match}) => match == null ? null : (
                                            <MeetingPage meetingId={match.params.id} />
                                        )
                                    }
                                </Route>
                                <Route path={profilePageLink}>
                                    <ProfilePage />
                                </Route>
                                <Route path={allMeetingsPageLink}>
                                    <MeetingsListPage />
                                </Route>
                                <Route path={proposalsPageLink}>
                                    <ProposalsPage />
                                </Route>
                                <Route path={proposedTopicsPageLink}>
                                    <ProposedTopicsPage />
                                </Route>
                                <Route path={speakerMeetingsPageLink}>
                                    <SpeakerMeetingsPage />
                                </Route>
                            </main>
                            <Footer />
                        </Route>
                    </Switch>
                </App>
            </PermissionBoundary>
        )
    );
}

export default AppRoutes;