import React, {Component} from 'react';
import {connect} from 'react-redux'; // Component - класс из пакета react
import './index.css';
import Header from "./components/Header";
import Footer from "./components/Footer";
import PointForm from "./components/PointForm";
import Graphic from "./components/Graphic";
import Table from "./components/Table";
import {getPoints} from "./actions/appActions";
import {withRouter} from 'react-router-dom';

// TODO грязь
class Main extends Component {

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
                <Footer/>
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

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Main));
