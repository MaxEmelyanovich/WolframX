import React, { useState } from 'react';
import './VectorPage.css';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';

function VectorSecondPage() {
    const [vector1, setVector1] = useState('');
    const [num, setNum] = useState('');
    const [language, setLanguage] = useState('Java');
    const [operation, setOperation] = useState('');
    const [responseData, setResponseData] = useState('');

    const handleVector1Change = (event) => {
        setVector1(event.target.value);
    };

    const handleNumChange = (event) => {
        setNum(event.target.value);
    };
    

    const sendDataToServer = async () => {
        let userEmail = null;
        if (localStorage.getItem('email')) {
            userEmail = localStorage.getItem('email');
        }

        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/vectors/oneVector',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        vector1: '{'+vector1+'}',
                        number: num,
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
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center', paddingLeft: '70%', paddingTop: '2%', gap: '20%'}}>
                <Link to="/chatroom" style={{color: '#fff', fontFamily: 'Cascadia Mono', fontWeight: 'lighter', textDecoration: 'none'}}>
                    Chat
                </Link>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
            </div>
            <div className="vectorHeaderContainer">
                <h1 className="vectorPageHeader">One Vector</h1>
            </div>
        <div className="vectorPageButtons">
            <button onClick={() => setOperation('numberMul')} className={operation === 'numberMul' ? 'vector-button active' : 'vector-button'}>Number Multiplication</button>
            <button onClick={() => setOperation('numberDiv')} className={operation === 'numberDiv' ? 'vector-button active' : 'vector-button'}>Number Division</button>
        </div>
        <div className="vectorPageInputs">
        <div style={{width: '25%', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
        <p className="vectorPageP">Vector:</p>
        <input
            value={vector1}
            onChange={handleVector1Change}
            className="vectInput"
        />
        </div>
        <div style={{width: '25%', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
        <p className="vectorPageP">Number:</p>
        <input           
            value={num}
            onChange={handleNumChange}
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

export default VectorSecondPage;