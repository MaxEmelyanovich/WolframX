import React, { useState } from 'react';
import './VectorPage.css';

function VectorPage() {
    const [vector1, setVector1] = useState('');
    const [vector2, setVector2] = useState('');
    const [language, setLanguage] = useState('Java');
    const [operation, setOperation] = useState('');
    const [responseData, setResponseData] = useState('');

    const handleVector1Change = (event) => {
        setVector1(event.target.value);
    };

    const handleVector2Change = (event) => {
        setVector2(event.target.value);
    };
    

    const sendDataToServer = async () => {
        let userEmail = null;
        if (localStorage.getItem('email')) {
            userEmail = localStorage.getItem('email');
        }

        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/vectors/twoVectors',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        vector1: '{'+vector1+'}',
                        vector2: '{'+vector2+'}',
                        language: language,
                        operation: operation,
                        email: userEmail
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

            // console.log(data.result);
            setResponseData(data.result); // Установка полученного ответа в состояние responseData
        } catch (error) {
            console.error('Ошибка:', error);
        } 
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        sendDataToServer();
    };

  

    return (
        <div className="vectorPageContainer">
            <div className="vectorHeaderContainer">
                <h1 className="vectorPageHeader">Two Vectors</h1>
            </div>
        <div className="vectorPageButtons">
            <button onClick={() => setOperation('sum')} className={operation === 'sum' ? 'vector-button active' : 'vector-button'}>Vector Sum</button>
            <button onClick={() => setOperation('sub')} className={operation === 'sub' ? 'vector-button active' : 'vector-button'}>Vector Subtraction</button>
            <button onClick={() => setOperation('scalarMul')} className={operation === 'scalarMul' ? 'vector-button active' : 'vector-button'}>Scalar Multiplication</button>
            <button onClick={() => setOperation('vectorMul')} className={operation === 'vectorMul' ? 'vector-button active' : 'vector-button'}>Vector Multiplication</button>
            <button onClick={() => setOperation('angle')} className={operation === 'angle' ? 'vector-button active' : 'vector-button'}>Angle</button>
        </div>
        <div className="vectorPageInputs">
        <div style={{width: '25%', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
        <p className="vectorPageP">Vector 1:</p>
        <input
            value={vector1}
            onChange={handleVector1Change}
            className="vectInput"
        />
        </div>
        <div style={{width: '25%', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
        <p className="vectorPageP">Vector 2:</p>
        <input           
            value={vector2}
            onChange={handleVector2Change}
            className="vectInput"
        />
        </div>
        </div>
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
        <p className="vectorPageP">Lib:</p>
        <select
            value={language}
            onChange={event => setLanguage(event.target.value)}
            className="vectorLibSelector"
        >
            <option value="Java" style={{backgroundColor: '#403c4c'}}>Java</option>
            <option value="C++" style={{backgroundColor: '#403c4c'}}>C++</option>
        </select>
        <button onClick={handleSubmit} className="vectorPageButton">Count</button>
        
            <textarea
                        value={responseData}
                        className="vectorPageTextArea"
                        readOnly
            />
        </div>
        </div>
    );
}

export default VectorPage;