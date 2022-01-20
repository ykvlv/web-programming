import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {store} from "./store/store";

import './index.css';
import App from "./App";

import {BrowserRouter} from "react-router-dom";

// TODO грязь
ReactDOM.render(
    <BrowserRouter>
        <Provider store={store}>
            <App/>
        </Provider>
    </BrowserRouter>,
    document.getElementById('root')
)
;
