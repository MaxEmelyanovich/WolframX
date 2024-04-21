import React from 'react';
import { useTranslation } from 'react-i18next';
// eslint-disable-next-line no-unused-vars
import i18n from '../i18n';
import './LanguageSelector.css';

function LanguageSelector() {
    const { t, i18n } = useTranslation();

    const changeLanguage = (e) => {
        const selectedLanguage = e.target.value;
        i18n.changeLanguage(selectedLanguage);
    };

    return (
        <select className="selector" onChange={changeLanguage}>
            <option value="en">{t('English')}</option>
            <option value="by">{t('Беларуская')}</option>
            <option value="ru">{t('Русский')}</option>
            <option value="po">{t('Português')}</option>
            <option value="ar">{t('اللغة العربية')}</option>
            <option value="cn">{t('中文')}</option>
            <option value="gr">{t('Ελληνική')}</option>
        </select>
    );
}

export default LanguageSelector;
