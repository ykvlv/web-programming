import React, {Component} from 'react';
import {connect} from 'react-redux'; // Component - класс из пакета react
import Header from "./components/Header";
import Footer from "./components/Footer";
import { withRouter } from 'react-router-dom';

// TODO почистить от комментов импортов, да и вообще страница такая не нужна мне
class Start extends Component {
    render() {
        const {header} = this.props;
        return (
            <div>
                <Header name={header.name} surname={header.surname} variant={header.variant} group={header.group}/>
                <div className="centerBorderStart">
                    <label className="title" id="title">Необходимо авторизоваться</label>
                    <label className="title smallTitle" id="title">*Сожалею, это не моя прихоть*</label>
                </div>
                <Footer/>
            </div>
        );
    }
}

const mapStateToProps = store => {
    return {
        header: store.header,
    }
};



export default (withRouter(connect(mapStateToProps)(Start)));
