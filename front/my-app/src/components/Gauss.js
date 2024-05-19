import React, { useState } from 'react';
import './Gauss.css';

function Gauss() {
  const [dimension, setDimension] = useState(0);
  const [threads, setThreads] = useState(1);
  const [language, setLanguage] = useState('Java');
  const [coefficients, setCoefficients] = useState([]);
  const [constants, setConstants] = useState([]);
  const [responseData, setResponseData] = useState('');

  const handleDimensionChange = (e) => {
    const n = parseInt(e.target.value);
    setDimension(n);
    setCoefficients(new Array(n).fill(0).map(() => new Array(n).fill(0)));
    setConstants(new Array(n).fill(0));
  };

  const handleThreadsChange = (e) => {
    setThreads(e.target.value);
  };

  const handleLanguageChange = (e) => {
    setLanguage(e.target.value);
  };

  const handleCoefficientChange = (rowIndex, colIndex, value) => {
    const newCoefficients = [...coefficients];
    newCoefficients[rowIndex][colIndex] = parseFloat(value);
    setCoefficients(newCoefficients);
  };

  const handleConstantChange = (rowIndex, value) => {
    const newConstants = [...constants];
    newConstants[rowIndex] = parseFloat(value);
    setConstants(newConstants);
  };

  const handleSubmit = () => {
    const coefficientsString = coefficients.map((row) => `{${row.join(',')}}`).join(',');
    const constantsString = `{${constants.join(',')}}`;

    fetch('http://25.23.19.72:8080/calculations/equations/gauss', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        coefficients: coefficientsString,
        constants: constantsString,
        threads,
        language,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        // Handle the response from the server
        //console.log(data);
        setResponseData(data.solution);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleSubmit();
    }
  };

  return (
    <div style={{}}>
      <h1 className="gaussPageHeader">Linear Equation System Solver</h1>
      <div>
        <label htmlFor="dimension">Dimension (n):</label>
        <input
          type="number"
          id="dimension"
          value={dimension}
          onChange={handleDimensionChange}
        //   min="1"
          onKeyDown={handleKeyDown}
        />
      </div>
      <div>
        <label htmlFor="threads">Threads:</label>
        <input
          type="text"
          id="threads"
          value={threads}
          onChange={handleThreadsChange}
        //   min="1"
        />
      </div>
      <div>
        <label htmlFor="language">Language:</label>
        <select id="language" value={language} onChange={handleLanguageChange}>
          <option value="Java">Java</option>
          <option value="C++">C++</option>
        </select>
      </div>
      {dimension > 0 && (
        <table>
          <thead>
            <tr>
              {Array.from({ length: dimension }, (_, i) => i + 1).map((col) => (
                <th key={col}>x{col}</th>
              ))}
              <th>Constant</th>
            </tr>
          </thead>
          <tbody>
            {Array.from({ length: dimension }, (_, i) => i + 1).map((row) => (
              <tr key={row}>
                {Array.from({ length: dimension }, (_, j) => j + 1).map((col) => (
                  <td key={`${row}-${col}`}>
                    <input
                      type="number"
                      value={coefficients[row - 1][col - 1]}
                      onChange={(e) =>
                        handleCoefficientChange(row - 1, col - 1, e.target.value)
                      }
                    />
                  </td>
                ))}
                <td>
                  <input
                    type="number"
                    value={constants[row - 1]}
                    onChange={(e) => handleConstantChange(row - 1, e.target.value)}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      <div style={{display: 'flex', flexDirection: 'column', marginLeft: '5%'}}>
      <button onClick={handleSubmit} className="gaussPageButton">Count</button>
      <textarea
                        value={responseData}
                        className="gaussPageTextArea"
                        readOnly
            />
            </div>
    </div>
  );
}

export default Gauss;