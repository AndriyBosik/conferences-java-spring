import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './components/AppRoutes/AppRoutes';

ReactDOM.render(
  	<React.StrictMode>
		<ErrorBoundary>
	  		<Router>
				<AppRoutes />
	  		</Router>
		</ErrorBoundary>
  	</React.StrictMode>,
  	document.getElementById('root')
);

reportWebVitals();
