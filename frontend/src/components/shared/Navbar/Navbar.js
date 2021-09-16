import React from "react";
import { Link } from "react-router-dom";
import { pages } from "../../../constants/pages";
import { useLink } from "../../../hooks/useLink";
import logo from "./../../../shared/images/logo.png";
import LeftPanel from "./LeftPanel";
import RightPanel from "./RightPanel";

function Navbar() {
    return (
        <header>
            <div className="navbar-fixed">
                <nav id="navbar">
                    <div className="nav-wrapper teal lighten-2">
                        <div className="container">
                            <div className="row">
                                <div className="col s12">
                                    <LeftPanel />

                                    <Link to={useLink(pages.profile)} className="brand-logo center full-height">
                                        <div className="full-height p10">
                                            <img src={logo} alt="Logo" className="full-height" />
                                        </div>
                                    </Link>

                                    <RightPanel />
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
        </header>
    );
}

export default Navbar;