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
                <h1 class="h1Calc">{t('calculations')}</h1>
                <LanguageSelector />
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
                    <h2 class="h2Calc">Form 2</h2>
                    <Link className="link" to="/calculator">
                        Calculator
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 2
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 3
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
                    <h2 class="h2Calc">Form 3</h2>
                    <Link className="link" to="/">
                        Link 1
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 2
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 3
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
                    <h2 class="h2Calc">Form 4</h2>
                    <Link className="link" to="/">
                        Link 1
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 2
                    </Link>
                    <br></br>
                    <Link className="link" to="/">
                        Link 3
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
