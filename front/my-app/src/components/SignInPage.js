import React from 'react';
import './SignInPage.css';

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
        // Ваша логика обработки формы авторизации
        console.log('Авторизация:', this.state.email, this.state.password);
        this.setState({ email: '', password: '' });
      };
    
      render() {
        return (
          <div className='authpage'>
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
                  type="signInPageInput"
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

// в signInPageRef1 поставить нужную ссылку на страницу для сброса пароля

export default SignInPage;