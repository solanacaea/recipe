import { post } from './BaseService';

export const getAll = () => {
    const url = `generate/getall`;
    return post(url);
}

export const deleteGenerator = (generator) => {
    const url = `generate/delete`;
    return post(url, generator);
}

export const generate = (obj) => {
    const url = `generate/generate`;
    return post(url, obj, {
        responseType: 'blob' // very very very important!!!
    });
}
