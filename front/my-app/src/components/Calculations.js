import React from 'react';
import { Link } from 'react-router-dom';
import './Calculations.css';

function Calculations() {
  return (
    <div className="calculations-container">
    
      
      <h1 class="h1Calc">Calculations</h1>

      <div className="form-container">
        <div className="rect">
          <h2 class="h2Calc">Matrices :</h2>
          <Link className="link" to="/matrixtranspose">Matrix Transpose</Link>
          <br></br>
          <Link className="link" to="/matrixmultiplication">Matrix Multiplication</Link>
          <br></br>
          <Link className="link" to="/matrixtranspose">Matrix Addition</Link>
          <br></br>
          <Link className="link" to="/matrixtranspose">Matrix Subtraction</Link>
          <br></br>
          <Link className="link" to="/matrixtranspose">Scalar Multiplication</Link>
        </div>

        <div className="rect">
          <h2 class="h2Calc">Form 2</h2>
          <Link className="link" to="/">Link 1</Link>
          <br></br>
          <Link className="link" to="/">Link 2</Link>
          <br></br>
          <Link className="link" to="/">Link 3</Link>
          <br></br>
          <Link className="link" to="/">Link 4</Link>
          <br></br>
          <Link className="link" to="/">Link 5</Link>
        </div>

        <div className="rect">
          <h2 class="h2Calc">Form 3</h2>
          <Link className="link" to="/">Link 1</Link>
          <br></br>
          <Link className="link" to="/">Link 2</Link>
          <br></br>
          <Link className="link" to="/">Link 3</Link>
          <br></br>
          <Link className="link" to="/">Link 4</Link>
          <br></br>
          <Link className="link" to="/">Link 5</Link>
        </div>

        <div className="rect">
          <h2 class="h2Calc">Form 4</h2>
            <Link className="link" to="/">Link 1</Link>
            <br></br>
            <Link className="link" to="/">Link 2</Link>
            <br></br>
            <Link className="link" to="/">Link 3</Link>
            <br></br>
            <Link className="link" to="/">Link 4</Link>
            <br></br>
            <Link className="link" to="/">Link 5</Link>
        </div>
      </div>
    </div>
  );
}

export default Calculations;