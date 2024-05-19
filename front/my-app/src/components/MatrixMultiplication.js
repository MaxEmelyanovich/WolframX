import React, { useState } from 'react';
import './MatrixMultiplication.css';
import { toPng } from 'html-to-image';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';

function MatrixMultiplication() {
    const [inputData1, setInputData1] = useState('');
    const [inputData2, setInputData2] = useState('');
    const [inputData3, setInputData3] = useState('');
    const [responseData, setResponseData] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [imageData, setImageData] = useState(null);

    const handleInputChange1 = (event) => {
        setInputData1(event.target.value);
    };
    const handleInputChange2 = (event) => {
        setInputData2(event.target.value);
    };
    const handleInputChange3 = (event) => {
        setInputData3(event.target.value);
    };

    const sendDataToServer = async () => {
        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/matrices/multiply',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        matrix1: inputData1,
                        matrix2: inputData2,
                        threads: inputData3,
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

            setResponseData(data.result); // Установка полученного ответа в состояние responseData

            // const image = await toPng(
            //     document.getElementById('response-container')
            // );
            // setImageData(image);

            setInputData1('');
            setInputData2('');
            setInputData3('');
        } catch (error) {
            console.error('Ошибка:', error);
        } finally {
            setIsLoading(false); // Установка isLoading обратно в false после получения ответа
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        setIsLoading(true);

        sendDataToServer();
    };

    const { t } = useTranslation();

    return (
        <div className="matrix-multiplication-container">
            <div style={{display: 'flex', flexDirection: 'row', paddingLeft: '80%', paddingTop: '2%', gap: '20%'}}>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
            </div>
            <h1 className="h1Multiplication">{t('multiplication')}</h1>
            <form className="matrix-multiplication-form">
                <form className="matrix-multiplication-block">
                    <p className="matrix-multiplication-text1">
                        {t('matrix')}:
                    </p>
                    <input
                        value={inputData1}
                        onChange={handleInputChange1}
                        className="matrix-multiplication-input"
                        placeholder={t('enterthematrix')}
                    />
                    <p className="matrix-multiplication-text1">
                        {t('secondmatrix')}:
                    </p>
                    <input
                        value={inputData2}
                        onChange={handleInputChange2}
                        className="matrix-multiplication-input"
                        placeholder={t('enterthesecondmatrix')}
                    />
                </form>
                <form className="matrix-multiplication-block">
                    <p className="matrix-multiplication-text-threads">
                        {t('threads')}:
                    </p>
                    <input
                        value={inputData3}
                        onChange={handleInputChange3}
                        className="matrix-multiplication-input-threads"
                        placeholder={t('threads')}
                    />
                </form>
            </form>

            <button
                onClick={handleSubmit}
                className="matrix-multiplication-button"
            >
                {isLoading ? t('loading') : t('getresult')}
            </button>
            <p className="matrix-multiplication-text2">{t('result')}:</p>
            <form
                className="matrix-multiplication-form"
                id="response-container"
            >
                {/* Форма для вывода текста с сервера */}
                <textarea
                    value={responseData}
                    className="matrix-multiplication-textarea"
                    readOnly
                />
            </form>
            {imageData && (
                <div>
                    <h2>Image Result:</h2>
                    <img src={imageData} alt="Result" />
                </div>
            )}
        </div>
    );
}

export default MatrixMultiplication;
