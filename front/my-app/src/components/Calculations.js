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
                <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center', paddingLeft: '50%', paddingTop: '3%', gap: '50%'}}>
                <Link to="/chatroom" style={{color: '#fff', fontFamily: 'Cascadia Mono', fontWeight: 'lighter', textDecoration: 'none'}}>
                    Chat
                </Link>
                <Link to="/" style={{backgroundColor: 'rgba(0,0,0,0)', color: '#fff', border: 'none', fontSize: '120%', textDecoration: 'none'}}>             
                    🏠︎
                </Link>
                <LanguageSelector />
                </div>
                <h1 class="h1Calc">{t('calculations')}</h1>
                
            </div>
            

            <div className="form-container">              

                <div className="rect">
                    <h2 class="h2Calc">{t('vectors')} :</h2>
                    <Link className="link" to="/vectorpage">
                        {t('twovectors')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/vectorsecondpage">
                        {t('onevector')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/vectormodule">
                        {t('vectormod')}
                    </Link>
                </div>

                <div className="rect">
                    <h2 class="h2Calc">{t('arithmetics')} :</h2>
                    <Link className="link" to="/calculator">
                        {t('calculator')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/converter">
                        {t('converter')}
                    </Link>
                    <br></br>
                    <h2 class="h2Calc">{t('equations')} :</h2>
                    <Link className="link" to="/gauss">
                        {t('gauss')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/newton">
                        {t('newton')}
                    </Link>
                </div>

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
                    <h2 class="h2Calc">{t('integrals')} :</h2>
                    <Link className="link" to="/trapezoidal">
                        {t('trap')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/simpson">
                        {t('simpson')}
                    </Link>
                    <br></br>
                    <Link className="link" to="/romberg">
                        {t('romberg')}
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Calculations;
