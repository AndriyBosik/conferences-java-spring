import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { pages } from '../../constants/pages';
import { getMessage } from '../../handler/MessageHanlder';

class ErrorBoundary extends Component {

    constructor(props) {
        super(props);

        this.state = {
            error: null,
            hasError: false
        }
    }

    static getDerivedStateFromError(error) {
        return {
            error: error,
            hasError: true
        };
    }

    render() {
        if (this.state.hasError) {
            return (
                <div className="full-width center-align">
                    <div className="window-height s-vflex-center">
                        <h1>{getMessage("error_happened")}</h1>
                        <div className="center-align">
                            <Link to={pages.home} className="btn amber darken-3">
                                Go Home
                            </Link>
                        </div>
                    </div>
                </div>
            );
        }
        return this.props.children;
    }
}

export default ErrorBoundary;