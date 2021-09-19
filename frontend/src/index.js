import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary';
import reportWebVitals from './reportWebVitals';
import { Router } from 'react-router-dom';
import AppRoutes from './components/AppRoutes/AppRoutes';
import { history } from "./handler/HistoryHandler";

ReactDOM.render(
	<React.StrictMode>
		<ErrorBoundary>
			<Router history={history}>
				<AppRoutes />
			</Router>
		</ErrorBoundary>
	</React.StrictMode>,
	document.getElementById('root')
);

reportWebVitals();
