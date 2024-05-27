import React, { useEffect, useState } from 'react';

const HistoryPage = () => {
  const [history, setHistory] = useState([]);

  useEffect(() => {
    // Функция для получения данных с сервера
    const fetchHistory = async () => {
      try {
        const response = await fetch('http://25.23.19.72:8080/');
        const data = await response.json();
        setHistory(data);
      } catch (error) {
        console.error('Ошибка при получении данных:', error);
      }
    };

    fetchHistory();
  }, []);

  return (
    <div>
      <h1>История вычислений</h1>
      <table>
        <thead>
          <tr>
            <th>Название операции</th>
            <th>Условие</th>
            <th>Результат</th>
          </tr>
        </thead>
        <tbody>
          {history.map((item, index) => (
            <tr key={index}>
              <td>{item['operationName']}</td>
              <td>{item['task']}</td>
              <td>{item['solution']}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default HistoryPage;