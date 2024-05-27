import React, { useState } from 'react';
import './SignUpPage.css';
import wolframX from '../assets/wolframx.svg';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const SignUpPage = () => {

    const navigate = useNavigate();

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        switch (name) {
            case 'firstName':
                setFirstName(value);
                break;
            case 'lastName':
                setLastName(value);
                break;
            case 'email':
                setEmail(value);
                break;
            case 'password':
                setPassword(value);
                break;
            default:
                break;
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        alert('CHECK YOUR EMAIL FOR CONFIRMATION');
        navigate('/');


        // Валидация полей

        const data = {
            firstName,
            lastName,
            email,
            password,
        };

        // http://25.23.19.72:8080/signup
        fetch('http://25.23.19.72:8080/signup', {
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
                
                // Дополнительные действия после успешной отправки данных
                
            })
            .catch((error) => {
                console.error('Ошибка при отправке данных:', error);
            });

        setFirstName('');
        setLastName('');
        setEmail('');
        setPassword('');
    };

    const { t } = useTranslation();

    return (
        <div className="registration-page">
            <div style={{display: 'flex', flexDirection: 'row', paddingLeft: '70%', paddingTop: '3%', paddingBottom: '3%', gap: '50%'}}>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
            </div>
            <div className="wolframXdiv">
                <img className="wolframX" src={wolframX} alt="WolframX" />
            </div>
            <form type="formReg" onSubmit={handleSubmit}>
                <label>
                    <input
                        type="signUpPageInput"
                        name="firstName"
                        value={firstName}
                        onChange={handleInputChange}
                        placeholder={t('firstname')}
                    />
                </label>
                <label>
                    <input
                        type="signUpPageInput"
                        name="lastName"
                        value={lastName}
                        onChange={handleInputChange}
                        placeholder={t('lastname')}
                    />
                </label>
                <label>
                    <input
                        type="signUpPageInput"
                        name="email"
                        value={email}
                        onChange={handleInputChange}
                        placeholder={t('email')}
                    />
                </label>
                <label>
                    <input
                        type="signUpPagePassword"
                        name="password"
                        value={password}
                        onChange={handleInputChange}
                        placeholder={t('password')}
                    />
                </label>
                <button className="signUpPageSubmit">{t('signup')}</button>
                <p type="signUpPageP">
                    {t('alreadyhave')}{' '}
                    <a type="signUpPageRef" href="/signin">
                        {t('signin')}
                    </a>
                </p>
            </form>
        </div>

    );
};

export default SignUpPage;