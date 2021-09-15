import React from "react";
import { Route, Switch } from "react-router";
import App from "./../../App";
import LoginPage from "./../Login/LoginPage";
import SignUpPage from "./../SignUp/SignUpPage";
import LinkParser from "../Link/LinkParser";
import { useLink } from "../../hooks/useLink";

function AppRoutes() {
    return (
        <LinkParser>
            <App>
                <Switch>
                    <Route path={[useLink("/"), "/"]} exact>
                        <LoginPage />
                    </Route>
                    <Route path={useLink("/users/sign-up")}>
                        <SignUpPage />
                    </Route>
                </Switch>
            </App>
        </LinkParser>
    );
}

export default AppRoutes;