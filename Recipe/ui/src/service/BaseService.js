import axios from 'axios';

import Config from '../core/Config'

const service = axios.create({
    baseURL: Config
});

service.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (!token) {
            return config;
        }

        config.headers['Authorization'] = token;
        return config;
    }
);

service.interceptors.response.use(
    response => {
        const data = response.data;
        if (data.status !== 'OK') {
            return Promise.reject(data.body);
        } else {
            return Promise.resolve(data.body);
        }
    }, err => {
        return Promise.reject(err);
    }
);

export const get = (url, data) => {
    return service.get(url, {
        params: data
    });
}

export const post = (url, data, config) => {
    return service.post(url, data, config);
}

export default { get, post };