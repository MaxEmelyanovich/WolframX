import React, { useState } from 'react';
import './TrapezoidalAndSimpson.css';

function Simpson() {

    const [func, setFunc] = useState('');
    const [start, setStart] = useState('');
    const [stop, setStop] = useState('');
    const [n, setN] = useState('');
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

    const sendDataToServer = async () => {
        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/integrals/simpson',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        function: func,
                        start: start,
                        stop: stop,
                        n: n
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
        <div className="trapezoid-container">
            <h1 className="trapHeader">Simpson Method</h1>
            <form onSubmit={handleSubmit}>
                <div>
                <p className="trapText">Function:</p>
                <input
                    className="funcInput"
                    value={func}
                    onChange={handleFuncChange}
                />
                </div>
                <div className="trap-subcontainer">
                <div>
                <p className="trapText1">Start:</p>
                <input
                    className="trapSubInput"            
                    value={start}
                    onChange={handleStartChange}
                />
                </div>
                <div>
                <p className="trapText1">Stop:</p>
                <input
                    className="trapSubInput"            
                    value={stop}
                    onChange={handleStopChange}
                />
                </div>
                <div>
                <p className="trapText1">Iterations:</p>
                <input
                    className="trapSubInput"            
                    value={n}
                    onChange={handleNChange}
                />
                </div>
                </div>
                <button type="submit" className="trapButton">Count</button>
                
            </form>
            <textarea
                    value={responseData}
                    className="trapezoid-textarea"
                    readOnly
            />
            </div>
    );
}

export default Simpson;