import React, { useState } from 'react';
import './SignInPage.css';
import wolframX from '../assets/wolframx.svg';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const SignInPage = ({ setIsLoggedIn }) => {

    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const handleInputChange = (event) => {
        if (event.target.name === 'email') {
            setEmail(event.target.value);
        } else if (event.target.name === 'password') {
            setPassword(event.target.value);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const data = {
            email: email,
            password: password,
        };

        fetch('http://25.23.19.72:8080/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then((response) => response.json())
            .then((responseData) => {
                console.log('Ответ сервера:', responseData);
                alert('HELLO, ' + responseData.firstName + ' ' + responseData.lastName);
                
                // Дополнительные действия после успешной отправки данных
                const username = responseData.firstName;
                localStorage.setItem('username', username);

                navigate('/');
                setIsLoggedIn(true);
                
            })
            .catch((error) => {
                console.error('Ошибка при отправке данных:', error);
            });

        setEmail('');
        setPassword('');
    };

    const { t } = useTranslation();

    return (
        <div className="authpage">
        <div style={{display: 'flex', flexDirection: 'row', paddingLeft: '70%', paddingTop: '3%', paddingBottom: '3%', gap: '50%'}}>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
            </div>
            <div className="wolframXdiv">
                <img className="wolframX" src={wolframX} alt="WolframX" />
            </div>
            <form type="formAuth" onSubmit={handleSubmit}>
                <label>
                    <input
                        type="signInPageInput"
                        name="email"
                        value={email}
                        onChange={handleInputChange}
                        placeholder={t('email')}
                    />
                </label>
                <br />
                <label>
                    <input
                        type="signInPagePassword"
                        name="password"
                        value={password}
                        onChange={handleInputChange}
                        placeholder={t('password')}
                    />
                </label>
                <br />
                <p type="signInPageP">
                    <a type="signInPageRef1" href="/">
                        {t('forgotpassword')}
                    </a>
                </p>
                <button className="signInPageSubmit">{t('signin')}</button>
                <p type="signInPageP2">
                    {t('donthave')}{' '}
                    <a type="signInPageRef2" href="/signup">
                        {t('signup')}
                    </a>
                </p>
            </form>
            
        </div>
    );
};

export default SignInPage;

