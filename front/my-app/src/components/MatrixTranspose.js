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

// import React, { useState } from 'react';
// import './MatrixTranspose.css';

// function MatrixTranspose() {
//   const [inputData, setInputData] = useState('');
//   const [responseData, setResponseData] = useState('');
//   const [isLoading, setIsLoading] = useState(false);

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

// ВНИЗУ: С ОТПРАВКОЙ НА СЕРВЕР И PNG

import React, { useState } from 'react';
import './MatrixTranspose.css';
import { toPng } from 'html-to-image';

function MatrixTranspose() {
  const [inputData, setInputData] = useState('');
  const [responseData, setResponseData] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [imageData, setImageData] = useState(null);

  const handleInputChange = (event) => {
    setInputData(event.target.value);
  };

  const sendDataToServer = async () => {
    try {
      const response = await fetch('http://25.23.19.72:8080/calculations/matrices/transpose', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ matrix: inputData }), // Отправка данных на сервер
      });

      const data = await response.json(); // Преобразование ответа в JSON формат

      setResponseData(data.result); // Установка полученного ответа в состояние responseData

      const image = await toPng(document.getElementById('response-container'));
      setImageData(image);

      setInputData('');

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

  return (
    <div className="matrix-transpose-container">
      <h1 className="h1Transpose">Matrix Transpose</h1>
      <p className="matrix-transpose-text1">Matrix:</p>
      <form className="matrix-transpose-form">
        {/* Форма для ввода данных */}
        <input
          value={inputData}
          onChange={handleInputChange}
          className="matrix-transpose-input"
         placeholder="Enter the matrix"
        />
      </form>
      <button onClick={handleSubmit} className="matrix-transpose-button">
        {isLoading ? 'Loading...' : 'Get Result'}
      </button>
      <p className="matrix-transpose-text2">Result:</p>
      <form className="matrix-transpose-form" id="response-container">
        {/* Форма для вывода текста с сервера */}
        <textarea
          value={responseData}
          className="matrix-transpose-textarea"
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

export default MatrixTranspose;