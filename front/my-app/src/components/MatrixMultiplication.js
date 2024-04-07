import React, { useState } from 'react';
import './MatrixMultiplication.css';
import { toPng } from 'html-to-image';

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
    // переписать для двух форм ввода
    // try {
    //   const response = await fetch('http://25.23.19.72:8080/calculations/matrices/transpose', {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({ matrix: inputData }), // Отправка данных на сервер
    //   });

    //   const data = await response.json(); // Преобразование ответа в JSON формат

    //   setResponseData(data.result); // Установка полученного ответа в состояние responseData

    //   const image = await toPng(document.getElementById('response-container'));
    //   setImageData(image);

    //   setInputData1('');

    // } catch (error) {
    //   console.error('Ошибка:', error);
    // } finally {
    //   setIsLoading(false); // Установка isLoading обратно в false после получения ответа
    // }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setIsLoading(true);

    sendDataToServer();
  };

  return (
    <div className="matrix-multiplication-container">
      <h1 className="h1Multiplication">Matrix Multiplication</h1>
      <div className="rowWithText">
        <p className="matrix-multiplication-text1">First matrix:</p>
        <p className="matrix-multiplication-text-threads">Threads:</p>
      </div>
      <form className="matrix-multiplication-form">
        {/* Форма для ввода данных */}
        <input
          value={inputData1}
          onChange={handleInputChange1}
          className="matrix-multiplication-input"
         placeholder="Enter the first matrix"
        />
        <input
          value={inputData3}
          onChange={handleInputChange3}
          className="matrix-multiplication-input-threads"
         placeholder="Threads"
        />
      </form>
      <p className="matrix-multiplication-text2">Second matrix:</p>
      <form className="matrix-multiplication-form">
        {/* Форма для ввода данных */}
        <input
          value={inputData2}
          onChange={handleInputChange2}
          className="matrix-multiplication-input"
         placeholder="Enter the second matrix"
        />
      </form>
      <button onClick={handleSubmit} className="matrix-multiplication-button">
        {isLoading ? 'Loading...' : 'Get Result'}
      </button>
      <p className="matrix-multiplication-text2">Result:</p>
      <form className="matrix-multiplication-form" id="response-container">
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