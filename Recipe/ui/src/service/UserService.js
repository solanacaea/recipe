import axios from 'axios'

import Config from '../core/Config'

const BaseURL = Config == '.' ? '.' : `${Config}/user`;

export const login = (user) => {
  const url = `${BaseURL}/login`;
  return axios.post(url, user);
}

export const register = (user) => {
  const url = `${BaseURL}/register`;
  return axios.post(url, user);
}

export const getCaptcha = (phone) => {
  const url = `${BaseURL}/captcha?phone=` + phone;
  return axios.post(url, phone);
}

export const getLogin = () => {
  const url = `${BaseURL}/get`;
  // return axios.post(url);
  return new Promise((resolve) => {
    resolve(true);
  });
}

export const logout = () => {
  
}

export const checkName = (username) => {
  const url = `${BaseURL}/checkname?username=` + username;
  return axios.post(url, username);
}