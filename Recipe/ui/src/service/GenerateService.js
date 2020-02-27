import axios from 'axios'

import Config from '../core/Config'

const BaseURL = Config == '.' ? '.' : `${Config}/generate`;

export const getAll = () => {
    const url = `${BaseURL}/getall`;
    return axios.post(url);
}

export const deleteGenerator = (generator) => {
    const url = `${BaseURL}/delete`;
    return axios.post(url, generator);
}

export const generate = (obj) => {
    const url = `${BaseURL}/generate`;
    return axios.post(url, obj, {
        responseType: 'blob' // very very very important!!!
    });
}

