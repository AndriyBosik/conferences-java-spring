import React from "react";
import { Route, Switch } from "react-router";
import App from "./../../App";
import LoginPage from "./../Login/LoginPage";
import SignUpPage from "./../SignUp/SignUpPage";

function AppRoutes() {
    return (
        <App>
            <Switch>
                <Route path="/" exact>
                    <LoginPage />
                </Route>
                <Route path="/users/sign-up">
                    <SignUpPage />
                </Route>
            </Switch>
        </App>
    );
}

export default AppRoutes;