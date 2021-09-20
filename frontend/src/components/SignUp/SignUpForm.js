import React, { useState, useEffect } from "react";
import { Link, Redirect } from "react-router-dom";
import M from "materialize-css";
import { signUpUser } from "../../services/AuthService";
import "./SignUpPage.css";
import { pages } from "./../../constants/pages";
import { generateUrl } from "../../handler/LinkHandler";
import Errors from "../Errors/Errors";
import { useLink } from "./../../hooks/useLink";
import { useMessage } from "../../hooks/useMessage";

function SignUpForm() {
    const homeLink = useLink(pages.home);

    const signUpMessage = useMessage("sign_up");
    const requiredFieldMessage = useMessage("required_field");
    const userMessage = useMessage("user");
    const speakerMessage = useMessage("speaker");
    const loginMessage = useMessage("login");
    const emailMessage = useMessage("email");
    const nameMessage = useMessage("name");
    const surnameMessage = useMessage("surname");
    const passwordMessage = useMessage("password");
    const confirmPasswordMessage = useMessage("confirm_password");

    const [role, setRole] = useState("user");
    const [login, setLogin] = useState("");
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errors, setErrors] = useState([]);
    const [signedUp, setSignedUp] = useState(false);

    useEffect(() => {
        M.updateTextFields();
        return () => {};
    }, []);

    const handleSubmit = async event => {
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

        const response = await signUpUser(data);
        setErrors(response.errors);
        if (response.errors.length === 0 && response.data) {
            setSignedUp(true);
        }
    };

    if (signedUp) {
        return (
            <Redirect to={homeLink + "?user=signed-up"} />
        );
    }

    return (
        <div className="px10 radius-4 z-depth-1 auto-height py10">
            <div className="sign-up-form-header s-hflex px10">
                <h4 className="equal-flex">{signUpMessage}</h4>
            </div>

            <Errors errors={errors} />

            <form action="" method="post" onSubmit={handleSubmit}>
                <div className="full-width s-hflex px5 my10">
                    <label className="equal-flex">
                        <input type="radio" name="role" value="user" checked={role === "user"} onChange={event => setRole(event.target.value)} />
                        <span>{userMessage}</span>
                    </label>
                    <label className="equal-flex">
                        <input type="radio" name="role" value="speaker" checked={role === "speaker"} onChange={event => setRole(event.target.value)} />
                        <span>{speakerMessage}</span>
                    </label>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="login" type="text" name="login" value={login} onChange={(event) => setLogin(event.target.value)} />
                    <label htmlFor="login">{loginMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="email" type="email" name="email" value={email} onChange={(event) => setEmail(event.target.value)} />
                    <label htmlFor="email">{emailMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="name" type="text" name="name" value={name} onChange={(event) => setName(event.target.value)} />
                    <label htmlFor="name">{nameMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="surname" type="text" name="surname" value={surname} onChange={(event) => setSurname(event.target.value)} />
                    <label htmlFor="surname">{surnameMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="password" type="password" name="password" value={password} onChange={(event) => setPassword(event.target.value)} />
                    <label htmlFor="password">{passwordMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width input-field col s12 m6">
                    <input id="confirm-password" type="password" name="confirm-password" value={confirmPassword} onChange={(event) => setConfirmPassword(event.target.value)} />
                    <label htmlFor="confirm-password">{confirmPasswordMessage}</label>
                    <span className="helper-text">{requiredFieldMessage}</span>
                </div>
                <div className="full-width s-hflex-end">
                    <Link to={generateUrl("/")} className="btn waves-effect waves-light light-blue darken-4 mx10">
                        {loginMessage} <i className="material-icons right">send</i>
                    </Link>
                    <button type="submit" className="btn waves-effect waves-light">
                        {signUpMessage} <i className="material-icons right">supervisor_account</i>
                    </button>
                </div>
            </form>
        </div>
    );
}

export default SignUpForm;