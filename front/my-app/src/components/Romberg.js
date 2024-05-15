import React, { useState } from 'react';
import './Romberg.css';

function Romberg() {

    const [func, setFunc] = useState('');
    const [start, setStart] = useState('');
    const [stop, setStop] = useState('');
    const [n, setN] = useState('');
    const [tolerance, setTolerance] = useState('');
    const [responseData, setResponseData] = useState('');
    

    const handleFuncChange = (event) => {
        setFunc(event.target.value);
    };

    const handleStartChange = (event) => {
        setStart(event.target.value);
    };

    const handleStopChange = (event) => {
        setStop(event.target.value);
    };

    const handleNChange = (event) => {
        setN(event.target.value);
    };

    const handleToleranceChange = (event) => {
        setTolerance(event.target.value);
    }

    const sendDataToServer = async () => {
        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/integrals/romberg',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        function: func,
                        start: start,
                        stop: stop,
                        n: n,
                        toleranceDegree: tolerance
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

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
        <div className="romberg-container">
            <h1 className="rombergHeader">Romberg Method</h1>
            <form onSubmit={handleSubmit}>
                <div>
                <p className="rombergText">Function:</p>
                <input
                    className="rombergFuncInput"
                    value={func}
                    onChange={handleFuncChange}
                />
                </div>
                <div className="romberg-subcontainer">
                <div>
                <p className="rombergText1">Start:</p>
                <input
                    className="rombergSubInput"            
                    value={start}
                    onChange={handleStartChange}
                />
                </div>
                <div>
                <p className="rombergText1">Stop:</p>
                <input
                    className="rombergSubInput"            
                    value={stop}
                    onChange={handleStopChange}
                />
                </div>
                <div>
                <p className="rombergText1">Iterations:</p>
                <input
                    className="rombergSubInput"            
                    value={n}
                    onChange={handleNChange}
                />
                </div>
                <div>
                    <p className="rombergText1">Tolerance:</p>
                    <div style={{display: 'flex', flexDirection: 'row'}}>
                        <p className="rombergText1">10 ^ - </p>
                        <input
                            className="rombergFuncInput"            
                            value={tolerance}
                            onChange={handleToleranceChange}
                        />
                    </div>
                </div>
                </div>
                <button type="submit" className="rombergButton">Count</button>
                
            </form>
            <textarea
                    value={responseData}
                    className="romberg-textarea"
                    readOnly
            />
            </div>
    );
}

export default Romberg;