import React from 'react';
import { Link } from 'react-router-dom';
import './Calculations.css';
import { useTranslation } from 'react-i18next';
import LanguageSelector from './LanguageSelector';

function Calculations() {
    const { t } = useTranslation();

    return (
        <div className="calculations-container">

            <div className="headerWithSelector">
                <div style={{display: 'flex', flexDirection: 'row', paddingLeft: '70%', paddingTop: '3%', gap: '50%'}}>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
                </div>
                <h1 class="h1Calc">{t('calculations')}</h1>
                
            </div>
            

            <div className="form-container">
                <div className="rect">
                    <h2 class="h2Calc">{t('matrices')} :</h2>
                    <Link className="link" to="/matrixtranspose">
                        {t('transpose')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/matrixmultiplication">
                        {t('multiplication')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/matrixaddition">
                        {t('addition')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/matrixsubtraction">
                        {t('subtraction')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/scalarmultiplication">
                        {t('scalarmult')}
                    </Link>
                </div>

                <div className="rect">
                    <h2 class="h2Calc">Arithmetics :</h2>
                    <Link className="link" to="/calculator">
                        Calculator
                    </Link>
                    <br></br>
                    <Link className="link" to="/converter">
                        Converter
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 3
                    </Link>
                    <br></br>
                    <Link className="link" to="/gauss">
                        Gauss
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 5
                    </Link>
                </div>

                <div className="rect">
                    <h2 class="h2Calc">Integrals :</h2>
                    <Link className="link" to="/trapezoidal">
                        Trapezoidal
                    </Link>
                    <br></br>
                    <Link className="link" to="/simpson">
                        Simpson
                    </Link>
                    <br></br>
                    <Link className="link" to="/romberg">
                        Romberg
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 4
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 5
                    </Link>
                </div>

                <div className="rect">
                    <h2 class="h2Calc">Vectors :</h2>
                    <Link className="link" to="/vectorpage">
                        Two Vectors
                    </Link>
                    <br></br>
                    <Link className="link" to="/vectorsecondpage">
                        One Vector
                    </Link>
                    <br></br>
                    <Link className="link" to="/vectormodule">
                        Vector Module
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 4
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 5
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Calculations;
