import React, {Component} from 'react';

import './App.css';
import Main from "./Main";
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';


// TODO почистить импорты, коменты
class App extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Main />
            </div>
        )
    }
}

const mapStateToProps = store => {
    return {
        app: store.app,
    }
};

export default withRouter(connect(mapStateToProps)(App));
