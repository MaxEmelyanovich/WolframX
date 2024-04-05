import React from 'react';
import './SignInPage.css';
import wolframX from './assets/wolframx.svg';

class SignInPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          email: '',
          password: ''
        };
      }
    
      handleInputChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
      };
    
      handleSubmit = (event) => {
        event.preventDefault();
        
        const data = {
          email: this.state.email,
          password: this.state.password
        };

        fetch('http://25.23.19.72:8080/signin', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          body: JSON.stringify(data)
        })
          .then(response => response.json())
          .then(responseData => {
            console.log('Ответ сервера:', responseData);
            alert('ПОЛЬЗОВАТЕЛЬ АВТОРИЗОВАН');
            // Дополнительные действия после успешной отправки данных
          })
          .catch(error => {
            console.error('Ошибка при отправке данных:', error);
          });
        
        this.setState({ email: '', password: '' });
      };
    
      render() {
        return (
          <div className='authpage'>
            <div className="wolframXdiv">
              <img className="wolframX" src={wolframX} alt="WolframX" />
            </div>
            <form type="formAuth" onSubmit={this.handleSubmit}>
              <label>
                <input
                  type="signInPageInput"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleInputChange}
                  placeholder="Email"
                />
              </label>
              <br />
              <label>
                <input
                  type="signInPagePassword"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleInputChange}
                  placeholder="Password"
                />
              </label>
              <br />
              <p type="signInPageP">
                <a type="signInPageRef1" href='/'>Forgot password</a> 
              </p>
              <button className="signInPageSubmit">Sign In</button>
              <p type="signInPageP2">
                Don't have an account?{' '}
                <a type="signInPageRef2" href='/signup'>Sign Up</a> 
              </p>
            </form>
          </div>
        );
      }
}

// В signInPageRef1 поставить нужную ссылку на страницу для сброса пароля

export default SignInPage;