import React from "react";
import { useTitle } from "../../hooks/useTitle";
import LanguagesList from "./../Languages/LanguagesList";
import { isAuthorized } from "../../services/AuthService";
import SignUpForm from "./SignUpForm";
import { useLink } from "./../../hooks/useLink";
import { pages } from "./../../constants/pages";
import { Redirect } from "react-router";

function SignUpPage() {
    useTitle("sign_up");
    const profilePageLink = useLink(pages.profile);

    const isLoggedIn = isAuthorized();

    return (
        isLoggedIn ? (
            <Redirect to={profilePageLink} />
        ) : (
            <div className="yellow lighten-5 s-vflex-center">
                <div className="container">
                    <div className="row">
                        <div className="col s12 m8 push-m2 my20">
                            <LanguagesList additionalClasses="my10 s-hflex-end" />
                            <SignUpForm />
                        </div>
                    </div>
                </div>
            </div>
        )
    );
}

export default SignUpPage;