import { post } from './BaseService';

export const deleteDish = (dish) => {
    const url = `dish/delete`;
    return post(url, dish);
}

export const getAllDishes = () => {
    const url = `dish/getall`;
    return post(url);
}

export const saveDish = (dish) => {
    const url = `dish/save`;
    return post(url, dish);
}