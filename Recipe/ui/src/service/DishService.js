import axios from 'axios'

import Config from '../core/Config'

export const deleteDish = (dish) => {
  const url = `${Config}/dish/delete`;
  return axios.post(url, dish); 
}

export const getAllDishes = () => {
  const url = `${Config}/dish/getall`;
  return axios.post(url);
}

export const saveDish = (dish) => {
  const url = `${Config}/dish/save`;
  return axios.post(url, dish);
}
