// import React from 'react';
// import './SignInPage.css';
// import wolframX from '../assets/wolframx.svg';
// import { useTranslation } from 'react-i18next';

// class SignInPage extends React.Component {
//     constructor(props) {
//         super(props);
//         this.state = {
//             email: '',
//             password: '',
//         };
//     }

//     handleInputChange = (event) => {
//         this.setState({ [event.target.name]: event.target.value });
//     };

//     handleSubmit = (event) => {
//         event.preventDefault();

//         const data = {
//             email: this.state.email,
//             password: this.state.password,
//         };

//         fetch('http://25.23.19.72:8080/signin', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//                 Accept: 'application/json',
//             },
//             body: JSON.stringify(data),
//         })
//             .then((response) => response.json())
//             .then((responseData) => {
//                 console.log('Ответ сервера:', responseData);
//                 alert('ПОЛЬЗОВАТЕЛЬ АВТОРИЗОВАН');
//                 // Дополнительные действия после успешной отправки данных
//             })
//             .catch((error) => {
//                 console.error('Ошибка при отправке данных:', error);
//             });

//         this.setState({ email: '', password: '' });
//     };

//     render() {
//         return (
//             <div className="authpage">
//                 <div className="wolframXdiv">
//                     <img className="wolframX" src={wolframX} alt="WolframX" />
//                 </div>
//                 <form type="formAuth" onSubmit={this.handleSubmit}>
//                     <label>
//                         <input
//                             type="signInPageInput"
//                             name="email"
//                             value={this.state.email}
//                             onChange={this.handleInputChange}
//                             placeholder="Email"
//                         />
//                     </label>
//                     <br />
//                     <label>
//                         <input
//                             type="signInPagePassword"
//                             name="password"
//                             value={this.state.password}
//                             onChange={this.handleInputChange}
//                             placeholder="Password"
//                         />
//                     </label>
//                     <br />
//                     <p type="signInPageP">
//                         <a type="signInPageRef1" href="/">
//                             Forgot password
//                         </a>
//                     </p>
//                     <button className="signInPageSubmit">Sign In</button>
//                     <p type="signInPageP2">
//                         Don't have an account?{' '}
//                         <a type="signInPageRef2" href="/signup">
//                             Sign Up
//                         </a>
//                     </p>
//                 </form>
//             </div>
//         );
//     }
// }

// // В signInPageRef1 поставить нужную ссылку на страницу для сброса пароля

// export default SignInPage;

import React, { useState } from 'react';
import './SignInPage.css';
import wolframX from '../assets/wolframx.svg';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';

const SignInPage = () => {
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
                alert('ПОЛЬЗОВАТЕЛЬ АВТОРИЗОВАН');
                // Дополнительные действия после успешной отправки данных
                const username = responseData.firstName;
                localStorage.setItem('username', username);
            })
            .catch((error) => {
                console.error('Ошибка при отправке данных:', error);
            });

        setEmail('');
        setPassword('');
    };

    const { t } = useTranslation();

    return (
        <div>
            <div style={{paddingLeft: '80%', paddingTop: '3%'}}>
            <LanguageSelector />
            </div>
        <div className="authpage">
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
        </div>
    );
};

export default SignInPage;
