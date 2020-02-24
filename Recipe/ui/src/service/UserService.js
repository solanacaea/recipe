import axios from 'axios'

import Config from '../core/Config'

export const login = (user) => {
  const url = `${Config}/user/login`;
  return axios.post(url, user);
}

export const register = (user) => {
  const url = `${Config}/user/register`;
  return axios.post(url, user);
}

export const getCaptcha = (phone) => {
  const url = `${Config}/user/captcha`;
  return axios.post(url, { phone: phone });
}

export const getLogin = () => {
  const url = `${Config}/user/get`;
  // return axios.post(url);
  return new Promise((resolve) => {
    resolve(true);
  });
}

export const logout = () => {
  
}