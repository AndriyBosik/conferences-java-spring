import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary';
import reportWebVitals from './reportWebVitals';
import { Router } from 'react-router-dom';
import AppRoutes from './components/AppRoutes/AppRoutes';
import { history } from "./handler/HistoryHandler";
import LinkStoreInstance from "./stores/LinkStore";

export const LanguageContext = React.createContext();
export const LinkContext = React.createContext();

ReactDOM.render(
	<React.StrictMode>
		<ErrorBoundary>
			<Router history={history}>
				<LinkContext.Provider value={LinkStoreInstance}>
					<AppRoutes />
				</LinkContext.Provider>
			</Router>
		</ErrorBoundary>
	</React.StrictMode>,
	document.getElementById('root')
);

reportWebVitals();
