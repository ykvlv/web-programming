import React, {Component} from 'react';
import {connect} from 'react-redux';

// TODO где бы убрать импорты
import './index.css';
import Header from "./components/Header";
import PointForm from "./components/PointForm";
import Graphic from "./components/Graphic";
import Table from "./components/Table";
import {getPoints} from "./actions/appActions";

// TODO грязь
class App extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        this.getPoints();
    }

    getPoints() {
        this.props.getPoints();
    }

    render() {
        const {header} = this.props;
        return (
            <div>
                <Header name={header.name} surname={header.surname} variant={header.variant} group={header.group}/>
                <div className="centerBorderMain">
                    <div className="formAndGraphic">
                        <PointForm/>
                        <Graphic/>
                    </div>
                    <Table/>
                </div>
            </div>
        );
    }
}

const mapStateToProps = store => {
    return {
        app: store.app,
        header: store.header,
    }
};

const mapDispatchToProps = dispatch => {
    return {
        getPoints: () => dispatch(getPoints()),
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(App)
