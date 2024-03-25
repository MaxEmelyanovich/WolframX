/*import React from 'react';
import { BrowserRouter as Router, Link, Routes, Route, useLocation, Navigate } from 'react-router-dom';
import SignInPage from './components/SignInPage';
import SignUpPage from './components/SignUpPage';
import './App.css';

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

function AppContent() {
  const location = useLocation();

  const showNavLinks = !['/signin', '/signup'].includes(location.pathname);

  return (
    <div>
      {showNavLinks && (
        <nav>
          <ul>
            <li>
              <button onClick={() => navigate('/signup')}>Sign Up</button>
            </li>
            <li>
              <button onClick={() => navigate('/signin')}>Sign In</button>
            </li>
          </ul>
        </nav>
      )}

      <Routes>
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
      </Routes>
    </div>
  );
}

export default App;
*/

import React from 'react';
import { BrowserRouter as Router, Link, Routes, Route, useLocation } from 'react-router-dom';
import SignInPage from './components/SignInPage';
import SignUpPage from './components/SignUpPage';
import './App.css'; 

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

function AppContent() {
  const location = useLocation();

  const showNavLinks = !['/signin', '/signup'].includes(location.pathname);

  return (
    <div>
      {showNavLinks && (
        <nav className="main-nav">
          <Link to="/signup" className="signup-button">
            Sign Up
          </Link>
        </nav>
      )}

      <div className="secondary-buttons">
        <Link to="/signin" className="signin-button">
          Sign In
        </Link>
      </div>

      <Routes>
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
      </Routes>
    </div>
  );
}

export default App;


