// import React from 'react';
// import './SignUpPage.css';
// import wolframX from '../assets/wolframx.svg';

// class SignUpPage extends React.Component {
//     constructor(props) {
//         super(props);
//         this.state = {
//             firstName: '',
//             lastName: '',
//             email: '',
//             password: '',
//         };
//     }

//     handleInputChange = (event) => {
//         this.setState({ [event.target.name]: event.target.value });
//     };

//     handleSubmit = (event) => {
//         event.preventDefault();

//         // Валидация полей

//         const data = {
//             firstName: this.state.firstName,
//             lastName: this.state.lastName,
//             email: this.state.email,
//             password: this.state.password,
//         };

//         // http://25.23.19.72:8080/signup
//         fetch('http://25.23.19.72:8080/signup', {
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
//                 alert('ПОЛЬЗОВАТЕЛЬ ЗАРЕГИСТРИРОВАН');
//                 // Дополнительные действия после успешной отправки данных
//             })
//             .catch((error) => {
//                 console.error('Ошибка при отправке данных:', error);
//             });

//         this.setState({
//             firstName: '',
//             lastName: '',
//             email: '',
//             password: '',
//         });
//     };

//     render() {
//         return (
//             <div className="registration-page">
//                 <div className="wolframXdiv">
//                     <img className="wolframX" src={wolframX} alt="WolframX" />
//                 </div>
//                 <form type="formReg" onSubmit={this.handleSubmit}>
//                     <label>
//                         <input
//                             type="signUpPageInput"
//                             name="firstName"
//                             value={this.state.firstName}
//                             onChange={this.handleInputChange}
//                             placeholder="First Name"
//                         />
//                     </label>
//                     <label>
//                         <input
//                             type="signUpPageInput"
//                             name="lastName"
//                             value={this.state.lastName}
//                             onChange={this.handleInputChange}
//                             placeholder="Last Name"
//                         />
//                     </label>
//                     <label>
//                         <input
//                             type="signUpPageInput"
//                             name="email"
//                             value={this.state.email}
//                             onChange={this.handleInputChange}
//                             placeholder="Email"
//                         />
//                     </label>
//                     <label>
//                         <input
//                             type="signUpPagePassword"
//                             name="password"
//                             value={this.state.password}
//                             onChange={this.handleInputChange}
//                             placeholder="Password"
//                         />
//                     </label>
//                     <button className="signUpPageSubmit">Sign Up</button>
//                     <p type="signUpPageP">
//                         Already have an account?{' '}
//                         <a type="signUpPageRef" href="/signin">
//                             Sign In
//                         </a>
//                     </p>
//                 </form>
//             </div>
//         );
//     }
// }

// export default SignUpPage;

import React, { useState } from 'react';
import './SignUpPage.css';
import wolframX from '../assets/wolframx.svg';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';

const SignUpPage = () => {
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
                alert('ПОЛЬЗОВАТЕЛЬ ЗАРЕГИСТРИРОВАН');
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
                        placeholder="First Name"
                    />
                </label>
                <label>
                    <input
                        type="signUpPageInput"
                        name="lastName"
                        value={lastName}
                        onChange={handleInputChange}
                        placeholder="Last Name"
                    />
                </label>
                <label>
                    <input
                        type="signUpPageInput"
                        name="email"
                        value={email}
                        onChange={handleInputChange}
                        placeholder="Email"
                    />
                </label>
                <label>
                    <input
                        type="signUpPagePassword"
                        name="password"
                        value={password}
                        onChange={handleInputChange}
                        placeholder="Password"
                    />
                </label>
                <button className="signUpPageSubmit">Sign Up</button>
                <p type="signUpPageP">
                    Already have an account?{' '}
                    <a type="signUpPageRef" href="/signin">
                        {t('signin')}
                    </a>
                </p>
            </form>
        </div>

    );
};

export default SignUpPage;