import { post } from './BaseService';

export const login = (user) => {
    const url = `user/login`;
    return post(url, null, { params: user})
}

export const register = (user) => {
    const url = `user/register`;
    return post(url, user);
}

export const getCaptcha = (phone) => {
  const url = `user/captcha`;
    return post(url, null, {
        params: {
            phone: phone
        }
    });
}

export const getLogin = () => {
    const url = `user/get`;
    // return axios.post(url);
    return new Promise((resolve) => {
        resolve(true);
    });
}

export const logout = () => {
}

export const checkName = (username) => {
    const url = `user/checkname?username=` + username;
    return post(url, username);
}