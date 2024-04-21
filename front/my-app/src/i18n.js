// i18n.js
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationEN from './locales/en.json';
import translationRU from './locales/ru.json';
import translationPO from './locales/po.json';
import translationBY from './locales/by.json';
import translationAR from './locales/ar.json';
import translationCN from './locales/cn.json';
import translationGR from './locales/gr.json';

const resources = {
    en: {
        translation: translationEN,
    },
    ru: {
        translation: translationRU,
    },
    po: {
        translation: translationPO,
    },
    by: {
        translation: translationBY,
    },
    ar: {
        translation: translationAR,
    },
    cn: {
        translation: translationCN,
    },
    gr: {
        translation: translationGR,
    },
};

i18n.use(initReactI18next).init({
    resources,
    lng: 'en', // язык по умолчанию
    fallbackLng: 'en', // язык, используемый при отсутствии перевода для текущего ключа
    interpolation: {
        escapeValue: false,
    },
});

export default i18n;
