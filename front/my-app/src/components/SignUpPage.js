import React from 'react';
import './SignUpPage.css';

class SignUpPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            firstname: '',
            lastname : '',
            email: '',
            password: ''
        };
      }
    
      handleInputChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
      };
    
      handleSubmit = (event) => {
        event.preventDefault();
        // Ваша логика обработки формы регистрации
        console.log('Регистрация:', this.state.firstname, this.state.lastname, this.state.email, this.state.password);
        this.setState({ firstname: '', lastname: '', email: '', password: '' });
      };
    
      render() {
        return (
          <div className='registration-page'>
            <form onSubmit={this.handleSubmit}>
              <label>
                <input
                  type="text"
                  name="firstname"
                  value={this.state.firstname}
                  onChange={this.handleInputChange}
                  placeholder="First Name"
                />
              </label>
              <label>
                <input
                  type="text"
                  name="lastname"
                  value={this.state.lastname}
                  onChange={this.handleInputChange}
                  placeholder="Last Name"
                />
              </label>
              <label>
                <input
                  type="text"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleInputChange}
                  placeholder="Email"
                />
              </label>
              <label>
                <input
                  type="text"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleInputChange}
                  placeholder="Password"
                />
              </label>
              <button type="submit">Sign Up</button>
            </form>
          </div>
        );
      }
}

export default SignUpPage;