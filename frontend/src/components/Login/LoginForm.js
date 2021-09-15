import React, {useState, useEffect} from "react";

import M from "materialize-css";
import { Link } from "react-router-dom";

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
                <label htmlFor="login">Login: </label>
                <input id="login" type="text" value={login} onChange={(event) => setLogin(event.target.value)} />
            </div>
            <div className="input-field col s12">
                <i className="material-icons prefix">lock</i>
                <label htmlFor="password">Password: </label>
                <input id="password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
            </div>
            <div className="col s12">
                <button className="full-width btn waves-effect waves-light" type="submit">
                    Login
                </button>
            </div>
            <div className="col s12 my15 weight-normal center-align">
                <span className="translucent-3p">Don't have an account? </span>
                <Link to="/users/sign-up">
                    Sign Up
                </Link>
            </div>
        </form>
    );
}

export default LoginForm;