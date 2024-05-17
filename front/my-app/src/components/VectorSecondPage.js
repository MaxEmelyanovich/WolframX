import React, { useState } from 'react';
import './VectorPage.css';

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
                        number: num,
                        language: language,
                        operation: operation
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