import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import M from "materialize-css";

import "./SignUpPage.css";
import { generateUrl } from "../../handler/LinkHandler";
import Message from "../Message/Message";

function SignUpForm() {
    let [role, setRole] = useState("user");
    let [login, setLogin] = useState("");
    let [email, setEmail] = useState("");
    let [name, setName] = useState("");
    let [surname, setSurname] = useState("");
    let [password, setPassword] = useState("");
    let [confirmPassword, setConfirmPassword] = useState("");

    useEffect(() => {
        M.updateTextFields();
        return () => {};
    }, []);

    const handleSubmit = event => {
        event.preventDefault();

        const data = {
            role: role,
            login: login,
            email: email,
            name: name,
            surname: surname,
            password: password,
            confirmPassword: confirmPassword
        };

        console.log(data);
    };

    return (
        <div className="px10 radius-4 z-depth-1 auto-height py10">
            <div className="sign-up-form-header s-hflex px10">
                <h4 className="equal-flex">{<Message alias="sign_up" />}</h4>
            </div>

            <form action="" method="post" onSubmit={handleSubmit}>
                <div className="full-width s-hflex px5 my10">
                    <label className="equal-flex">
                        <input type="radio" name="role" value="user" checked={role === "user"} onChange={event => setRole(event.target.value)} />
                        <span>{<Message alias="user" />}</span>
                    </label>
                    <label className="equal-flex">
                        <input type="radio" name="role" value="speaker" checked={role === "speaker"} onChange={event => setRole(event.target.value)} />
                        <span>{<Message alias="speaker" />}</span>
                    </label>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="login" type="text" name="login" value={login} onChange={(event) => setLogin(event.target.value)} />
                    <label htmlFor="login">{<Message alias="login" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="email" type="email" name="email" value={email} onChange={(event) => setEmail(event.target.value)} />
                    <label htmlFor="email">{<Message alias="email" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="name" type="text" name="name" value={name} onChange={(event) => setName(event.target.value)} />
                    <label htmlFor="name">{<Message alias="name" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="surname" type="text" name="surname" value={surname} onChange={(event) => setSurname(event.target.value)} />
                    <label htmlFor="surname">{<Message alias="surname" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="password" type="password" name="password" value={password} onChange={(event) => setPassword(event.target.value)} />
                    <label htmlFor="password">{<Message alias="password" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="confirm-password" type="password" name="confirm-password" value={confirmPassword} onChange={(event) => setConfirmPassword(event.target.value)} />
                    <label htmlFor="confirm-password">{<Message alias="confirm_password" />}</label>
                    <span className="helper-text">{<Message alias="required_field" />}</span>
                </div>
                <div className="full-width s-hflex-end">
                    <Link to={generateUrl("/")} className="btn waves-effect waves-light light-blue darken-4 mx10">
                        {<Message alias="login" />} <i className="material-icons right">send</i>
                    </Link>
                    <button type="submit" className="btn waves-effect waves-light">
                        {<Message alias="sign_up" />} <i className="material-icons right">supervisor_account</i>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default SignUpForm;