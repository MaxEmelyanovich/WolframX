import React from 'react';
import { BrowserRouter as Router, Link, Routes, Route, useLocation } from 'react-router-dom';
import SignInPage from './components/SignInPage';
import SignUpPage from './components/SignUpPage';
import Calculations from './components/Calculations';
import MatrixTranspose from './components/MatrixTranspose';
import './App.css'; 
import logoSVG from './assets/logo.svg';

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

function AppContent() {
  const location = useLocation();
  const isMainPage = location.pathname === '/';

  return (
    <div>
      {isMainPage && (
        <nav className="main-nav">
          <img className="logoSVG" src={logoSVG} alt="Logo" />
          <Link to="/" className="about-button">
            About
          </Link>
          <Link to="/calculations" className="calculations-button">
            Calculations
          </Link>
          <Link to="/signup" className="signup-button">
            Sign Up
          </Link>
        </nav>
      )}

      {isMainPage && (
        <div className="secondary-buttons">
          <Link to="/signin" className="signin-button">
            Sign In
          </Link>
        </div>
      )}

      <Routes>
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
        <Route path="/calculations" element={<Calculations />} />
        <Route path="/matrixtranspose" element={<MatrixTranspose />} />
      </Routes>
    </div>
  );
}

export default App;
