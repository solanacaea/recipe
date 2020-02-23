import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { ContentRouter } from './component/router/ContentRouter';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<ContentRouter />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
