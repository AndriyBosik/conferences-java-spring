import React from "react";
import { Route, Switch } from "react-router";
import App from "./../../App";
import LoginPage from "./../Login/LoginPage";
import SignUpPage from "./../SignUp/SignUpPage";
import LinkParser from "../Link/LinkParser";
import { useLink } from "../../hooks/useLink";
import { pages } from "../../constants/pages";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import ProfilePage from "../Profile/ProfilePage";
import Navbar from "../shared/Navbar/Navbar";
import Footer from "../shared/Footer/Footer";
import MeetingsListPage from "../MeetingsList/MeetingsListPage";
import MeetingPage from "../Meeting/MeetingPage";
import ProposalsPage from "../Proposals/ProposalsPage";
import ProposedTopicsPage from "../ProposedTopics/ProposedTopicsPage";

function AppRoutes() {
    return (
        <LinkParser>
            <PermissionBoundary>
                <App>
                    <Switch>
                        <Route path={[useLink(pages.home), "/"]} exact>
                            <LoginPage />
                        </Route>
                        <Route path={useLink(pages.signUp)}>
                            <SignUpPage />
                        </Route>
                        <Route>
                            <Navbar />
                            <main className="flex-auto">
                                <Route path={useLink(pages.meeting)} exact>
                                    {
                                        ({match}) => match == null ? null : (
                                            <MeetingPage meetingId={match.params.id} />
                                        )
                                    }
                                </Route>
                                <Route path={useLink(pages.profile)}>
                                    <ProfilePage />
                                </Route>
                                <Route path={useLink(pages.allMeetings)}>
                                    <MeetingsListPage />
                                </Route>
                                <Route path={useLink(pages.proposals)}>
                                    <ProposalsPage />
                                </Route>
                                <Route path={useLink(pages.proposedTopics)}>
                                    <ProposedTopicsPage />
                                </Route>
                            </main>
                            <Footer />
                        </Route>
                    </Switch>
                </App>
            </PermissionBoundary>
        </LinkParser>
    );
}

export default AppRoutes;