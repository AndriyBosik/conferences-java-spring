import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import LoginPage from './components/Login/LoginPage';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <App>
      <LoginPage />
    </App>
  </React.StrictMode>,
  document.getElementById('root')
);

reportWebVitals();
