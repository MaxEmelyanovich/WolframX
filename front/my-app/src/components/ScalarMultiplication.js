import React, { useState } from 'react';
import './ScalarMultiplication.css';
import { toPng } from 'html-to-image';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';

function ScalarMultiplication() {
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
                'http://25.23.19.72:8080/calculations/matrices/multiplybyscalar',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        matrix1: inputData1,
                        scalar: inputData2,
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
        <div className="scalar-multiplication-container">
            <h1 className="h1ScalarMult">{t('scalarmult')}</h1>
            <form className="scalar-multiplication-form">
                <form className="scalar-multiplication-block">
                    <p className="scalar-multiplication-text1">
                        {t('matrix')}:
                    </p>
                    <input
                        value={inputData1}
                        onChange={handleInputChange1}
                        className="scalar-multiplication-input"
                        placeholder={t('enterthematrix')}
                    />
                    <p className="scalar-multiplication-text1">
                        {t('scalar')}:
                    </p>
                    <input
                        value={inputData2}
                        onChange={handleInputChange2}
                        className="scalar-multiplication-input-scalar"
                        placeholder={t('enterthescalar')}
                    />
                </form>
                <form className="scalar-multiplication-block">
                    <p className="scalar-multiplication-text-threads">
                        {t('threads')}:
                    </p>
                    <input
                        value={inputData3}
                        onChange={handleInputChange3}
                        className="scalar-multiplication-input-threads"
                        placeholder={t('threads')}
                    />
                </form>
            </form>

            <button
                onClick={handleSubmit}
                className="scalar-multiplication-button"
            >
                {isLoading ? t('loading') : t('getresult')}
            </button>
            <p className="scalar-multiplication-text2">{t('result')}:</p>
            <form
                className="scalar-multiplication-form"
                id="response-container"
            >
                {/* Форма для вывода текста с сервера */}
                <textarea
                    value={responseData}
                    className="scalar-multiplication-textarea"
                    readOnly
                />
                <LanguageSelector />
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

export default ScalarMultiplication;
