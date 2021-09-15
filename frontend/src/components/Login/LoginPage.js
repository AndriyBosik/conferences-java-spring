import React from "react";
import LanguagesList from "../Languages/LanguagesList";
import LoginForm from './LoginForm';
import './LoginPage.css';
import { useTitle } from "./../../hooks/useTitle";

function LoginPage() {
    useTitle("login");

    return (
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
    );
}

export default LoginPage;