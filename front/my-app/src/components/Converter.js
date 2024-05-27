import React, { useState } from 'react';
import './Converter.css';

function Converter() {

    const [num, setNum] = useState('');
    const [sourceBase, setSourceBase] = useState('');
    const [targetBase, setTargetBase] = useState('');
    const [responseData, setResponseData] = useState('');
    

    const handleNumChange = (event) => {
        setNum(event.target.value);
    };

    const handleSourceBaseChange = (event) => {
        setSourceBase(event.target.value);
    };

    const handleTargetBaseChange = (event) => {
        setTargetBase(event.target.value);
    };


    const sendDataToServer = async () => {
        let userEmail = null;
        if (localStorage.getItem('email')) {
            userEmail = localStorage.getItem('email');
        }

        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/converter',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        number: num,
                        sourceBase: sourceBase,
                        targetBase: targetBase,
                        email: userEmail
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

            setResponseData(data.convertedNumber); // Установка полученного ответа в состояние responseData
        } catch (error) {
            console.error('Ошибка:', error);
        } 
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        sendDataToServer();
    };

    return (
        <div className="conversion-container">
            <h1 className="conversionHeader">Converter</h1>
            <form onSubmit={handleSubmit}>
                <div>
                <p className="conversionText">Number:</p>
                <input
                    className="numInput"
                    value={num}
                    onChange={handleNumChange}
                />
                </div>
                <div className="conversion-subcontainer">
                <div>
                <p className="conversionText1">Source Base:</p>
                <input
                    className="conversionSubInput"            
                    value={sourceBase}
                    onChange={handleSourceBaseChange}
                />
                </div>
                <div>
                <p className="conversionText1">Target Base:</p>
                <input
                    className="conversionSubInput"            
                    value={targetBase}
                    onChange={handleTargetBaseChange}
                />
                </div>
                </div>
                <button type="submit" className="conversionButton">Count</button>
                
            </form>
            <textarea
                    value={responseData}
                    className="conversion-textarea"
                    readOnly
            />
            </div>
    );
}

export default Converter;