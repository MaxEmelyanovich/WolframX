import React from 'react';
import './SignUpPage.css';
import { Link } from 'react-router-dom';

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

        // валидация полей
  
        // Создаем объект с данными для отправки на сервер
        const data = {
          firstname: this.state.firstname,
          lastname: this.state.lastname,
          email: this.state.email,
          password: this.state.password
        };

        // Отправляем данные на моковый сервер в виде JSON файла
        fetch('http://localhost:3000/users', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        })
          .then(response => response.json())
          .then(responseData => {
            // Обработка ответа от мокового сервера
            console.log('Ответ сервера:', responseData);
            // Дополнительные действия после успешной отправки данных
          })
          .catch(error => {
            // Обработка ошибок
            console.error('Ошибка при отправке данных:', error);
          });

        // Очищаем значения полей формы
        this.setState({
          firstname: '',
          lastname: '',
          email: '',
          password: ''
        });
      };
    
      render() {
        return (
          <div className='registration-page'>
            <form type="formReg" onSubmit={this.handleSubmit}>
              <label>
                <input
                  type="signUpPageInput"
                  name="firstname"
                  value={this.state.firstname}
                  onChange={this.handleInputChange}
                  placeholder="First Name"
                />
              </label>
              <label>
                <input
                  type="signUpPageInput"
                  name="lastname"
                  value={this.state.lastname}
                  onChange={this.handleInputChange}
                  placeholder="Last Name"
                />
              </label>
              <label>
                <input
                  type="signUpPageInput"
                  name="email"
                  value={this.state.email}
                  onChange={this.handleInputChange}
                  placeholder="Email"
                />
              </label>
              <label>
                <input
                  type="signUpPageInput"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleInputChange}
                  placeholder="Password"
                />
              </label>
              <button className="signUpPageSubmit">Sign Up</button>
              <p type="signUpPageP">
                Already have an account?{' '}
                <a type="signUpPageRef" href='/signin'>Sign In</a>
              </p>
            </form>
          </div>
        );
      }
}

export default SignUpPage;