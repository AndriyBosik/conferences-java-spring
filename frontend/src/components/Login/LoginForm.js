import React, { useState, useEffect } from "react";

import { generateUrl } from "../../handler/LinkHandler";

import M from "materialize-css";
import { Link } from "react-router-dom";
import Message from "../Message/Message";

function LoginForm() {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        M.updateTextFields();
    }, []);

    const handleSubmit = event => {
        event.preventDefault();

        console.log(login, password);
    }

    return (
        <form onSubmit={handleSubmit} className="auto-height">
            <div className="input-field col s12">
                <i className="material-icons prefix">account_circle</i>
                <label htmlFor="login">{<Message alias="login" />} </label>
                <input id="login" type="text" value={login} onChange={(event) => setLogin(event.target.value)} />
            </div>
            <div className="input-field col s12">
                <i className="material-icons prefix">lock</i>
                <label htmlFor="password">{<Message alias="password" />} </label>
                <input id="password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
            </div>
            <div className="col s12">
                <button className="full-width btn waves-effect waves-light" type="submit">
                    {<Message alias="login" />}
                </button>
            </div>
            <div className="col s12 my15 weight-normal center-align">
                <span className="translucent-3p">{<Message alias="dont_have_account" />} </span>
                <Link to={generateUrl("/users/sign-up")}>
                    {<Message alias="sign_up" />}
                </Link>
            </div>
        </form>
    );
}

export default LoginForm;