import React from "react";
import LanguagesList from "../Languages/LanguagesList";
import LoginForm from './LoginForm';
import { useTitle } from "./../../hooks/useTitle";
import { isAuthorized } from "../../services/AuthService";
import './LoginPage.css';
import M from "materialize-css";
import { Redirect } from "react-router";
import { pages } from "../../constants/pages";
import { useLink } from "./../../hooks/useLink";
import { getMessage } from "./../../handler/MessageHanlder";

function LoginPage() {
    useTitle("login");
    const profilePageLink = useLink(pages.profile);

    const isLoggedIn = isAuthorized();
    const isSignedUp = new URLSearchParams(window.location.search).get("user") === "signed-up";
    if (isSignedUp) {
        const toasts = document.querySelectorAll(".toast");
        if (toasts.length === 0) {
            M.toast({
                html: getMessage("successfully_signed_up")
            });
        }
    }

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