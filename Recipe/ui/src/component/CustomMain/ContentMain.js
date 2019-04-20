import React from 'react'
//引入路由
import {Route, Switch} from 'react-router-dom'
import RecipeMain from '../recipe/RecipeMain'

class ContentMain extends React.Component {
    render() {
        return (
           
                <Switch>
                    <Route exact path='/' component={RecipeMain} />
                    <Route exact path='/recipe' component={RecipeMain}/>
                   
                </Switch>
            
        )
    }
}

export default ContentMain