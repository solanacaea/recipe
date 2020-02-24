import React from 'react'
import {Route, Switch} from 'react-router-dom'

import RecipeMain from '../recipe/RecipeMain'
import GenerateMain from '../generate/GenerateMain'

class ContentMain extends React.Component {
    render() {
        return (
            <Switch>
                <Route exact path='/' component={RecipeMain} />
                <Route exact path='/recipe' component={RecipeMain}/>
                <Route exact path='/recipe/generate' component={GenerateMain}/>
            </Switch>
        )
    }
}

export default ContentMain