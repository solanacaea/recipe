import React from 'react';
import { Redirect } from 'react-router-dom'

import * as UserService from './service/UserService'
import { Recipe } from './Recipe';

export class Auth extends React.Component {

    state = {
        component: null
    }

    componentDidMount() {
        UserService.getLogin().then(user => {
            if (user) {
                this.setState({component: <Recipe></Recipe>});
            } else {
                this.setState({component: <Redirect to="/user/login"></Redirect>});
            }
        }, err => {
            this.setState({component: <Redirect to="/user/login"></Redirect>});
        });
    }

    render() {
        return this.state.component;
    }
}