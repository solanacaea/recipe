import React from 'react'
import { Route, BrowserRouter } from 'react-router-dom'

import { User } from '../user/User';
import { AuthComponent } from './AuthComponent';

export class ContentRouter extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Route path='/user' component={User} />
                <Route exact path='/recipe' component={AuthComponent}/>
                <Route exact path='/' component={User} />
            </BrowserRouter>
        );
    }
}
