import React, { useEffect, useState } from "react";
import { useLink } from "./../../hooks/useLink";
import CircularPreloader from "./../CircularPreloader/CircularPreloader";
import { pages } from "./../../constants/pages";
import { getUserEmail, updateProfile } from "./../../services/UserService";
import { getMessage } from "./../../handler/MessageHanlder";
import { useMessage } from "./../../hooks/useMessage";
import { initInputs } from "./../../handler/MaterializeInitializersHandler";
import M from "materialize-css";

function UserDataForm({user}) {
    const updateProfileLink = useLink(pages.profile);

    const loginMessage = useMessage("login");
    const nameMessage = useMessage("name");
    const surnameMessage = useMessage("surname");
    const emailMessage = useMessage("email");
    const oldPasswordMessage = useMessage("old_password");
    const newPasswordMessage = useMessage("new_password");
    const confirmPasswordMessage = useMessage("confirm_password");
    const confirmChangesMessage = useMessage("confirm_changes");

    const [loading, setLoading] = useState(false);
    const [login, setLogin] = useState(user.login);
    const [email, setEmail] = useState("");
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");

    useEffect(() => {
        setLoading(true);
        const fetchUserEmail = async () => {
            setEmail(await getUserEmail());
            setLoading(false);
        }

        fetchUserEmail();
    }, []);

    useEffect(() => {
        initInputs();
    }, [email, loading]);

    const handleSubmit = async event => {
        event.preventDefault();

        const result = await updateProfile({
            login,
            email,
            name,
            surname,
            oldPassword: password,
            newPassword,
            confirmPassword: confirmNewPassword
        });

        if (result) {
            setPassword("");
            setNewPassword("");
            setConfirmNewPassword("");
            M.toast({
                html: getMessage("profile_updated")
            });
        } else {
            M.toast({
                html: getMessage("error_happened")
            });
        }
    }

    return (
        loading ? (
            <div className="col s12 center-align mt50">
                <CircularPreloader />
            </div>
        ) : (
            <form action={updateProfileLink} method="post" className="col s12" onSubmit={handleSubmit}>
                <div className="full-width input-field">
                    <input id="login" type="text" name="login" value={login} onChange={event => setLogin(event.target.value)} />
                    <label htmlFor="login">{loginMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="surname" type="text" name="surname" value={surname} onChange={event => setSurname(event.target.value)} />
                    <label htmlFor="surname">{surnameMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="name" type="text" name="name" value={name} onChange={event => setName(event.target.value)} />
                    <label htmlFor="name">{nameMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="email" type="email" name="email" value={email} onChange={event => setEmail(event.target.value)} />
                    <label htmlFor="email">{emailMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="password" type="password" name="password" value={password} onChange={event => setPassword(event.target.value)} />
                    <label htmlFor="password">{oldPasswordMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="new-password" type="password" name="new-password" value={newPassword} onChange={event => setNewPassword(event.target.value)} />
                    <label htmlFor="new-password">{newPasswordMessage}</label>
                </div>
                <div className="full-width input-field">
                    <input id="confirm-password" type="password" name="confirm-password" value={confirmNewPassword} onChange={event => setConfirmNewPassword(event.target.value)} />
                    <label htmlFor="confirm-password">{confirmPasswordMessage}</label>
                </div>
                <div className="full-width s-hflex-end">
                    <button className="btn waves-effect waves-light" type="submit" name="action">
                        {confirmChangesMessage}
                        <i className="material-icons right">check</i>
                    </button>
                </div>
            </form>
        )
    );
}

export default UserDataForm;