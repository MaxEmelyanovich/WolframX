import React from 'react';
import {
    BrowserRouter as Router,
    Link,
    Routes,
    Route,
    useLocation,
} from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './components/LanguageSelector';
import SignInPage from './components/SignInPage';
import SignUpPage from './components/SignUpPage';
import Calculations from './components/Calculations';
import MatrixTranspose from './components/MatrixTranspose';
import MatrixMultiplication from './components/MatrixMultiplication';
import MatrixAddition from './components/MatrixAddition';
import MatrixSubtraction from './components/MatrixSubtraction';
import ScalarMultiplication from './components/ScalarMultiplication';
import ChatRoom from './components/ChatRoom';
import Calculator from './components/Calculator';
import Trapezoidal from './components/Trapezoidal';
import Simpson from './components/Simpson';
import Romberg from './components/Romberg';
import Converter from './components/Converter';
import VectorPage from './components/VectorPage';
import VectorSecondPage from './components/VectorSecondPage';
import VectorModule from './components/VectorModule';
import Gauss from './components/Gauss';
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

    const { t } = useTranslation();

    return (
        <div>
            {isMainPage && (
                <nav className="main-nav">
                    <img className="logoSVG" src={logoSVG} alt="Logo" />
                    <Link to="/" className="about-button">
                        {t('about')}
                    </Link>
                    <Link to="/calculations" className="calculations-button">
                        {t('calculations')}
                    </Link>
                    <Link to="/signup" className="signup-button">
                        {t('signup')}
                    </Link>
                    <div className="startPageSelector">
                    <LanguageSelector />
                    </div>
                </nav>
            )}

            {/* <h1 className="mainLabel">Your personal calculating assistant</h1> */}

            {isMainPage && (
                <div className="secondary-buttons">
                    <Link to="/signin" className="signin-button">
                        {t('signin')}
                    </Link>
                </div>
            )}

            <Routes>
                <Route path="/signup" element={<SignUpPage />} />
                <Route path="/signin" element={<SignInPage />} />
                <Route path="/calculations" element={<Calculations />} />
                <Route path="/matrixtranspose" element={<MatrixTranspose />} />
                <Route
                    path="/matrixmultiplication"
                    element={<MatrixMultiplication />}
                />
                <Route path="/matrixaddition" element={<MatrixAddition />} />
                <Route
                    path="/matrixsubtraction"
                    element={<MatrixSubtraction />}
                />
                <Route
                    path="/scalarmultiplication"
                    element={<ScalarMultiplication />}
                />
                <Route path="/chatroom" element={<ChatRoom />}/>
                <Route path="/calculator" element={<Calculator />}/>
                <Route path="/trapezoidal" element={<Trapezoidal />}/>
                <Route path="/simpson" element={<Simpson />}/>
                <Route path="/romberg" element={<Romberg />}/>
                <Route path="/converter" element={<Converter />}/>
                <Route path="/vectorpage" element={<VectorPage />}/>
                <Route path="/vectorsecondpage" element={<VectorSecondPage />}/>
                <Route path="/vectormodule" element={<VectorModule />}/>
                <Route path="/gauss" element={<Gauss />}/>
            </Routes>
        </div>
    );
}

export default App;
