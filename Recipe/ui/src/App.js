import React from 'react'
import { Route, BrowserRouter } from 'react-router-dom'

import { User } from './component/user/User';
import { Auth } from './Auth';

export class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Route exact path='/' component={User} />
                <Route path='/user' component={User} />
                <Route path='/recipe' component={Auth}/>
            </BrowserRouter>
        );
    }
}
