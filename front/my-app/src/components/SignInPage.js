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
            <form onSubmit={this.handleSubmit}>
              <label>
                <input
                  type="email"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleInputChange}
                  placeholder="Email"
                />
              </label>
              <br />
              <label>
                <input
                  type="password"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleInputChange}
                  placeholder="Password"
                />
              </label>
              <br />
              <button type="submit">Sign In</button>
            </form>
          </div>
        );
      }
}

export default SignInPage;