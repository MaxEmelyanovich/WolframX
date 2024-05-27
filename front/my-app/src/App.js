import React, { useState, useEffect } from 'react';
import {
    BrowserRouter as Router,
    Link,
    Routes,
    Route,
    useLocation,
    useNavigate,
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
import Newton from './components/Newton';
import HistoryPage from './components/HistoryPage';
import './App.css';
import logoSVG from './assets/logo.svg';

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const username = localStorage.getItem('username');
        if (username) {
            setIsLoggedIn(true);
        }
    }, []);

    return (
        <Router>
            <AppContent isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} /> 
        </Router>
    );
}

function AppContent({ isLoggedIn, setIsLoggedIn }) {
    const navigate = useNavigate();
    const location = useLocation();
    const isMainPage = location.pathname === '/';

    const { t } = useTranslation();

    const handleSignOut = () => {
        
        
         
        // отправка пост запроса на сервер
        fetch('http://25.23.19.72:8080/authorization/signout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                
            },
            credentials: 'include',
            body: JSON.stringify({
                email: localStorage.getItem('email')
            }),
        })
            .then((response) => response.json())
            .then(() => {
                console.log('OK');  
                localStorage.removeItem('username');
                localStorage.removeItem('email');
                setIsLoggedIn(false); 
                navigate('/');
            })
            .catch((error) => {
                console.error('Ошибка при отправке данных:', error);
            });
    };

    return (
        <div>
            {isMainPage && (
                <nav className="main-nav">
                    
                    <img className="logoSVG" src={logoSVG} alt="Logo" />
                    
                    
                    <Link to="/historypage" className="about-button">
                        {t('history')}
                    </Link>
                    <Link to="/calculations" className="calculations-button">
                        {t('calculations')}
                    </Link>
                    {isLoggedIn ? (
                        <button className="signup-button" onClick={handleSignOut}>
                            {t('signout')}
                        </button>
                    ) : (
                        <>
                        <Link to="/signup" className="signup-button">
                                {t('signup')}
                            </Link>                           
                        </>
                    )}
                    
                    <div className="startPageSelector">
                        <LanguageSelector />
                    </div>
                    
                    
                </nav>
            )}

            {/* <h1 className="mainLabel">Your personal calculating assistant</h1> */}

            {isMainPage && !isLoggedIn && (
                <div style={{marginTop: '28%'}}>
                <Link to="/signin" className="signin-button">
                {t('signin')}
                </Link>
                </div>
            )}

            <Routes>
                <Route path="/signup" element={<SignUpPage />} />
                <Route path="/signin" element={<SignInPage setIsLoggedIn={setIsLoggedIn} />} />
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
                <Route path="/chatroom" element={<ChatRoom />} />
                <Route path="/calculator" element={<Calculator />} />
                <Route path="/trapezoidal" element={<Trapezoidal />} />
                <Route path="/simpson" element={<Simpson />} />
                <Route path="/romberg" element={<Romberg />} />
                <Route path="/converter" element={<Converter />} />
                <Route path="/vectorpage" element={<VectorPage />} />
                <Route
                    path="/vectorsecondpage"
                    element={<VectorSecondPage />}
                />
                <Route path="/vectormodule" element={<VectorModule />} />
                <Route path="/gauss" element={<Gauss />} />
                <Route path="/newton" element={<Newton />} />
                <Route path="/historypage" element={<HistoryPage />} />
            </Routes>
        </div>
    );
}

export default App;