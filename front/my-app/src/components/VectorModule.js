import React, { useState } from 'react';
import './VectorPage.css';

function VectorModule() {
    const [vector1, setVector1] = useState('');
    const [language, setLanguage] = useState('Java');
    const [responseData, setResponseData] = useState('');

    const handleVector1Change = (event) => {
        setVector1(event.target.value);
    };
    

    const sendDataToServer = async () => {
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
                        language: language,
                        operation: 'abs'
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
                <h1 className="vectorPageHeader">Vector Module</h1>
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
        </div>
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '2%'}}>
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

export default VectorModule;