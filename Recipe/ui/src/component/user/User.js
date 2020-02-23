import React from 'react'
import {Route } from 'react-router-dom';

import './User.css'
import LoginForm from './login/Login.Form';
import RegisterForm from './register/Register.Form';
import logoURL from '../../assets/img/logo.jpg';

export class User extends React.Component {

    state = {
        url: 'https://shop316822115.taobao.com/?spm=a230r.7195193.1997079397.2.10b84a18miiyT3'
    }

    render() {
        return (
            <div className="container">
                <div className="logo" onClick={() => this.open()}>
                    <img alt="心筑月" src={logoURL}/>
                </div>
                <div className="main">
                    <Route component={LoginForm} path='/user/login' exact></Route>
                    <Route component={RegisterForm} path='/user/register' exact></Route>
                    <Route component={LoginForm} path='/' exact></Route>
                </div>
            </div>
        );
    }

    open() {
        window.open(this.state.url);
    }
}