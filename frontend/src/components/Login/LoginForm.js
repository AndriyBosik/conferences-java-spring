import React, { useState, useEffect } from "react";
import { loginUser } from "./../../services/AuthService";
import { generateUrl } from "../../handler/LinkHandler";
import M from "materialize-css";
import { Redirect } from "react-router";
import { Link } from "react-router-dom";
import { useMessage } from "./../../hooks/useMessage";
import { useLink } from "./../../hooks/useLink";
import { pages } from "./../../constants/pages";

function LoginForm() {
    const signUpLink = useLink(pages.signUp);

    const loginMessage = useMessage("login");
    const passwordMessage = useMessage("password");
    const dontHaveAccountMessage = useMessage("dont_have_account");
    const signUpMessage = useMessage("sign_up");
    const invalidLoginOrPassword = useMessage("invalid_login_or_password");

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [error, setError] = useState(false);
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        M.updateTextFields();
    }, []);

    const handleSubmit = async event => {
        event.preventDefault();

        const result = await loginUser({
            login: login,
            password: password
        });

        if (result.error) {
            setError(true);
            return;
        }

        setIsLoggedIn(true);
    }

    if (isLoggedIn) {
        return (
            <Redirect to={generateUrl("/home/profile")} />
        );
    }

    return (
        <form onSubmit={handleSubmit} className="auto-height">
            <div className="input-field col s12">
                <i className="material-icons prefix">account_circle</i>
                <label htmlFor="login">{loginMessage} </label>
                <input id="login" type="text" value={login} onChange={(event) => setLogin(event.target.value)} />
            </div>
            <div className="input-field col s12">
                <i className="material-icons prefix">lock</i>
                <label htmlFor="password">{passwordMessage} </label>
                <input id="password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
            </div>
            {error !== "" ? (
                <div className="full-width col s12">
                    <p className="center-align pink-text text-lighten-1 weight-normal login-errorKey-message">
                        {error ? invalidLoginOrPassword : ""}
                    </p>
                </div>
             ) : null}
            <div className="col s12">
                <button className="full-width btn waves-effect waves-light" type="submit">
                    {loginMessage}
                </button>
            </div>
            <div className="col s12 my15 weight-normal center-align">
                <span className="translucent-3p">{dontHaveAccountMessage} </span>
                <Link to={signUpLink}>
                    {signUpMessage}
                </Link>
            </div>
        </form>
    );
}

export default LoginForm;