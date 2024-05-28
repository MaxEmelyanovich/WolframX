// import React, { useState } from 'react';
// import './MatrixTranspose.css';

// function MatrixTranspose() {
//   const [inputData, setInputData] = useState('');
//   const [responseData, setResponseData] = useState('');

//   const handleInputChange = (event) => {
//     setInputData(event.target.value);
//   };

//   const handleSubmit = (event) => {
//     event.preventDefault();
//     // Отправка данных на сервер и получение ответа
//     // Здесь можно использовать fetch или другие методы для отправки данных на сервер
//     // и установки полученного ответа в состояние responseData
//   };

//   return (
//     <div className="matrix-transpose-container">
//       <h1 class="h1Transpose">Matrix Transpose</h1>
//       <p className="matrix-transpose-text1">Matrix:</p>
//       <form className="matrix-transpose-form">
//         {/* Форма для ввода данных */}
//         <input
//           value={inputData}
//           onChange={handleInputChange}
//           className="matrix-transpose-input"
//           placeholder="Enter the matrix"
//         />
//       </form>
//       <button onClick={handleSubmit} className="matrix-transpose-button">
//         Get Result
//       </button>
//       <p className="matrix-transpose-text2">Result:</p>
//       <form className="matrix-transpose-form">
//         {/* Форма для вывода текста с сервера */}
//         <textarea
//           value={responseData}
//           className="matrix-transpose-textarea"
//           readOnly
//         />
//       </form>
//     </div>
//   );
// }

// export default MatrixTranspose;

// ВВЕРХУ: БЕЗ ОТПРАВКИ НА СЕРВЕР
// ВНИЗУ: С ОТПРАВКОЙ НА СЕРВЕР, НО БЕЗ PNG

import React, { useState } from 'react';
import './MatrixTranspose.css';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';
import { Link } from 'react-router-dom';

function MatrixTranspose() {
    const [inputData, setInputData] = useState('');
    const [inputData2, setInputData2] = useState('');
    const [responseData, setResponseData] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [elapsedTime, setElapsedTime] = useState('');

    const handleInputChange = (event) => {
        setInputData(event.target.value);
    };

    const handleInputChange2 = (event) => {
        setInputData2(event.target.value);
    };

    const sendDataToServer = async () => {
        let userEmail = null;
        if (localStorage.getItem('email')) {
            userEmail = localStorage.getItem('email');
        } 

        try {
            const response = await fetch(
                'http://25.23.19.72:8080/calculations/matrices/transpose',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        matrix1: inputData,
                        threads: inputData2,
                        email: userEmail
                    }), // Отправка данных на сервер
                }
            );

            const data = await response.json(); // Преобразование ответа в JSON формат

            setResponseData(data.result); // Установка полученного ответа в состояние responseData

            setElapsedTime(data.elapsedTime + 'ms');
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
        <div className="matrix-transpose-container">
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center', paddingLeft: '70%', paddingTop: '2%', gap: '20%'}}>
                <Link to="/chatroom" style={{color: '#fff', fontFamily: 'CAscadia Mono', fontWeight: 'lighter', textDecoration: 'none'}}>
                    Chat
                </Link>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
            </div>
            <h1 className="h1Transpose">{t('transpose')}</h1>
            <form className="matrix-transpose-form">
                <form className="matrix-transpose-block">
                    <p className="matrix-transpose-text1">{t('matrix')}:</p>
                    <input
                        value={inputData}
                        onChange={handleInputChange}
                        className="matrix-transpose-input"
                        placeholder={t('enterthematrix')}
                    />
                </form>
                <form className="matrix-transpose-block">
                    <p className="matrix-transpose-text-threads">
                        {t('threads')}:
                    </p>
                    <input
                        value={inputData2}
                        onChange={handleInputChange2}
                        className="matrix-transpose-input-threads"
                        placeholder={t('threads')}
                    />
                </form>
            </form>
            <button onClick={handleSubmit} className="matrix-transpose-button">
                {isLoading ? t('loading') : t('getresult')}
            </button>
            <p className="matrix-transpose-text2">{t('result')}:</p>
            <form className="matrix-transpose-form">
                {/* Форма для вывода текста с сервера */}
                <textarea
                    value={responseData}
                    className="matrix-transpose-textarea"
                    readOnly
                />
                <textarea
                    value={elapsedTime}
                    style={{paddingTop: '1%', paddingBottom: '1%', border: '1px solid #c0c0c0',
                        borderRadius: '10px', color: '#fff', backgroundColor: 'rgba(0,0,0,0)',
                        marginLeft: '3%', fontSize: '120%'}}
                    readOnly
                />
            </form>
        </div>
    );
}

export default MatrixTranspose;

// ВНИЗУ: С ОТПРАВКОЙ НА СЕРВЕР И PNG

// import React, { useState } from 'react';
// import './MatrixTranspose.css';
// import { toPng } from 'html-to-image';

// function MatrixTranspose() {
//   const [inputData, setInputData] = useState('');
//   const [responseData, setResponseData] = useState('');
//   const [isLoading, setIsLoading] = useState(false);
//   const [imageData, setImageData] = useState(null);

//   const handleInputChange = (event) => {
//     setInputData(event.target.value);
//   };

//   const sendDataToServer = async () => {
//     try {
//       const response = await fetch('http://25.23.19.72:8080/calculations/matrices/transpose', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ matrix: inputData }), // Отправка данных на сервер
//       });

//       const data = await response.json(); // Преобразование ответа в JSON формат

//       setResponseData(data.result); // Установка полученного ответа в состояние responseData

//       const image = await toPng(document.getElementById('response-container'));
//       setImageData(image);

//       setInputData('');

//     } catch (error) {
//       console.error('Ошибка:', error);
//     } finally {
//       setIsLoading(false); // Установка isLoading обратно в false после получения ответа
//     }
//   };

//   const handleSubmit = (event) => {
//     event.preventDefault();
//     setIsLoading(true);

//     sendDataToServer();
//   };

//   return (
//     <div className="matrix-transpose-container">
//       <h1 className="h1Transpose">Matrix Transpose</h1>
//       <p className="matrix-transpose-text1">Matrix:</p>
//       <form className="matrix-transpose-form">
//         {/* Форма для ввода данных */}
//         <input
//           value={inputData}
//           onChange={handleInputChange}
//           className="matrix-transpose-input"
//          placeholder="Enter the matrix"
//         />
//       </form>
//       <button onClick={handleSubmit} className="matrix-transpose-button">
//         {isLoading ? 'Loading...' : 'Get Result'}
//       </button>
//       <p className="matrix-transpose-text2">Result:</p>
//       <form className="matrix-transpose-form" id="response-container">
//         {/* Форма для вывода текста с сервера */}
//         <textarea
//           value={responseData}
//           className="matrix-transpose-textarea"
//           readOnly
//         />
//       </form>
//       {imageData && (
//         <div>
//           <h2>Image Result:</h2>
//           <img src={imageData} alt="Result" />
//         </div>
//       )}
//     </div>
//   );
// }

// export default MatrixTranspose;
