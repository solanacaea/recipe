import axios from 'axios'

import Config from '../core/Config'

const BaseURL = Config == '.' ? '.' : `${Config}/dish`;

export const deleteDish = (dish) => {
    const url = `${BaseURL}/delete`;
    return axios.post(url, dish);
}

export const getAllDishes = () => {
    const url = `${BaseURL}/getall`;
    return axios.post(url);
}

export const saveDish = (dish) => {
    const url = `${BaseURL}/save`;
    return axios.post(url, dish);
}