import React from 'react'
import { Route, BrowserRouter } from 'react-router-dom'

import { User } from './component/user/User';
import { Auth } from './Auth';

export class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Route path='/user' component={User} />
                <Route exact path='/recipe' component={Auth}/>
                <Route exact path='/' component={User} />
            </BrowserRouter>
        );
    }
}
