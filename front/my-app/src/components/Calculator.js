import React, { useState } from 'react';
import './Calculator.css';

function Calculator() {
    const [expression, setExpression] = useState('');

    const handleButtonClick = (value) => {
        setExpression(expression + value);
    };

    const handleDelete = () => {
        setExpression(expression.slice(0, -1));
    };

    const sendDataToServer = async () => {
        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/calculator',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        expression: expression
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

            setExpression(data.result); // Установка полученного ответа в состояние responseData
        } catch (error) {
            console.error('Ошибка:', error);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        sendDataToServer();
    };


    return (
        <div className="calculator-container">
          <div className="calculator-form">
            <input
              type="text"
              value={expression}
              onChange={(e) => setExpression(e.target.value)}
              className="calculator-input"
              placeholder="Enter expression"
            />
            
          </div>
          <div className="calculator-buttons">
            <div className="button-group">
              {[1, 2, 3, '+'].map((value) => (
                <button
                  key={value}
                  onClick={() => handleButtonClick(value.toString())}
                  className="calculator-button"
                >
                  {value}
                </button>
              ))}
              <button onClick={() => handleDelete()} className="calculator-button">
              ←
              </button>
            </div>
            <div className="button-group">
              {[4, 5, 6, '-', '('].map((value) => (
                <button
                  key={value}
                  onClick={() => handleButtonClick(value.toString())}
                  className="calculator-button"
                >
                  {value}
                </button>
              ))}
            </div>
            <div className="button-group">
              {[7, 8, 9, '*', ')'].map((value) => (
                <button
                  key={value}
                  onClick={() => handleButtonClick(value.toString())}
                  className="calculator-button"
                >
                  {value}
                </button>
              ))}
            </div>
            <div className="button-group">
              {['.', 0, '^', '/'].map((value) => (
                <button
                  key={value}
                  onClick={() => handleButtonClick(value.toString())}
                  className="calculator-button"
                >
                  {value}
                </button>
              ))}
              <button onClick={handleSubmit} className="calculate-button">
              =
              </button>
            </div>
          </div>
        </div>
    );
}

export default Calculator;