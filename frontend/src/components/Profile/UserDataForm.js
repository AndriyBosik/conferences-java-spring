import React, { useState } from "react";
import { useLink } from "./../../hooks/useLink";
import Message from "../Message/Message";
import { pages } from "./../../constants/pages";

function UserDataForm({user}) {
    const [login, setLogin] = useState(user.login);
    const [email, setEmail] = useState(user.email);
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");

    const handleSubmit = event => {
        event.preventDefault();
    }

    return (
        <form action={useLink(pages.updateProfile)} method="post" className="col s12" onSubmit={handleSubmit}>
            <div className="full-width input-field">
                <input id="login" type="text" name="login" value={login} onChange={event => setLogin(event.target.value)} />
                <label htmlFor="login"><Message alias="login" /></label>
            </div>
            <div className="full-width input-field">
                <input id="surname" type="text" name="surname" value={surname} onChange={event => setSurname(event.target.value)} />
                <label htmlFor="surname"><Message alias="surname" /></label>
            </div>
            <div className="full-width input-field">
                <input id="name" type="text" name="name" value={name} onChange={event => setName(event.target.value)} />
                <label htmlFor="name"><Message alias="name" /></label>
            </div>
            <div className="full-width input-field">
                <input id="email" type="email" name="email" value={email} onChange={event => setEmail(event.target.value)} />
                <label htmlFor="email"><Message alias="email" /></label>
            </div>
            <div className="full-width input-field">
                <input id="password" type="password" name="password" value={password} onChange={event => setPassword(event.target.value)} />
                <label htmlFor="password"><Message alias="old_password" /></label>
            </div>
            <div className="full-width input-field">
                <input id="new-password" type="password" name="new-password" value={newPassword} onChange={event => setNewPassword(event.target.value)} />
                <label htmlFor="new-password"><Message alias="new_password" /></label>
            </div>
            <div className="full-width input-field">
                <input id="confirm-password" type="password" name="confirm-password" value={confirmNewPassword} onChange={event => setConfirmNewPassword(event.target.value)} />
                <label htmlFor="confirm-password"><Message alias="confirm_password" /></label>
            </div>
            <div className="full-width s-hflex-end">
                <button className="btn waves-effect waves-light" type="submit" name="action">
                    <Message alias="confirm_changes" />
                    <i className="material-icons right">check</i>
                </button>
            </div>
        </form>
    );
}

export default UserDataForm;