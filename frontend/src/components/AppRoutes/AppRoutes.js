import React from "react";
import { Route, Switch } from "react-router";
import App from "./../../App";
import LoginPage from "./../Login/LoginPage";
import SignUpPage from "./../SignUp/SignUpPage";
import LinkParser from "../Link/LinkParser";
import { useLink } from "../../hooks/useLink";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";

function AppRoutes() {
    return (
        <LinkParser>
            <PermissionBoundary>
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
            </PermissionBoundary>
        </LinkParser>
    );
}

export default AppRoutes;