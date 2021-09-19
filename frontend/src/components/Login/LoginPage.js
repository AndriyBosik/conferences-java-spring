import React from "react";
import LanguagesList from "../Languages/LanguagesList";
import LoginForm from './LoginForm';
import { useTitle } from "./../../hooks/useTitle";
import { isAuthorized } from "../../services/AuthService";
import './LoginPage.css';
import { Redirect } from "react-router";
import { pages } from "../../constants/pages";
import { useLink } from "./../../hooks/useLink";

function LoginPage() {
    useTitle("login");
    const profilePageLink = useLink(pages.profile);

    const isLoggedIn = isAuthorized();

    return (
        isLoggedIn ? (
            <Redirect to={profilePageLink} />
        ) : (
            <div className="window-height window-width yellow lighten-5 s-vflex-center">
                <div className="container">
                    <div className="row">
                        <div className="col s12 m4 push-m4">
                            <LanguagesList additionalClasses="s-hflex-end my10" />
                            <div className="px10 radius-4 z-depth-1">
                                <LoginForm />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    );
}

export default LoginPage;