import React from 'react'
import {Route, Switch} from 'react-router-dom'

import RecipeMain from '../recipe/RecipeMain'

export default class ContentRouter extends React.Component {
    render() {
        return (
            <Switch>
                <Route exact path='/' component={RecipeMain} />
                <Route exact path='/recipe' component={RecipeMain}/>
            </Switch>
        );
    }
}
