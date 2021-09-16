import React from "react";

function Footer() {
    return (
        <footer className="page-footer teal lighten-2" style={{paddingTop: 0}}>
            <div className="footer-copyright">
                <div className="container center-align">
                    &copy; {new Date().getFullYear()} Copyright <span className="weight-normal">Meetings.com</span>
                </div>
            </div>
        </footer>
    );
}

export default Footer;