import React from 'react';
import { Dropdown } from 'react-toolbox/lib/dropdown';
import { Input } from 'react-toolbox/lib/input';

import FormErrors from "./FormErrors";
import { connect } from 'react-redux';
import { sendPoint, setAnswer, setR, setX, setY } from "../actions/appActions";

const paramXValues = [
    {value: '-3', label: '-3'},
    {value: '-2', label: '-2'},
    {value: '-1', label: '-1'},
    {value: '0', label: '0'},
    {value: '1', label: '1'},
    {value: '2', label: '2'},
    {value: '3', label: '3'}
];

const paramRValues = [
    {value: '-3', label: '-3'},
    {value: '-2', label: '-2'},
    {value: '-1', label: '-1'},
    {value: '0', label: '0'},
    {value: '1', label: '1'},
    {value: '2', label: '2'},
    {value: '3', label: '3'}
];


class PointForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            formErrors: {paramX: '', paramR: '', paramY: '', all: ''},
            paramXValid: false,
            paramRValid: false,
            paramYValid: false,
        };
        this.handleChangeX = this.handleChangeX.bind(this);
        this.handleChangeY = this.handleChangeY.bind(this);
        this.handleChangeR = this.handleChangeR.bind(this);
        this.paramsIsReady = this.paramsIsReady.bind(this);

    }

    handleChangeX = (value) => {
        this.props.setX(value);
        this.validateField('paramX', value)
    };

    handleChangeR = (value) => {
        this.props.setR(value);
        this.validateField('paramR', value)
    };

    handleChangeY = (value) => {
        this.props.setY(Number(value).toFixed(2));
        this.validateField('paramY', Number(value).toFixed(2))
    };

    paramsIsReady = () => {
        let errors = this.state.formErrors;
        if (!this.state.formValid) errors.all = 'Заполните все поля';
        else {
            this.preparePoint(this.props.app.x, this.props.app.y, this.props.app.r);
        }
    };

    preparePoint(x, y, r) {
        console.log(x + " " + y + " " + r);
        let params = {
            x: x,
            y: y,
            r: r
        };
        this.props.sendPoint(params);
    }


    validateField(fieldName, value) {

        this.props.setAnswer('');
        let fieldValidationErrors = this.state.formErrors;
        let paramXValid = this.state.paramXValid;
        let paramYValid = this.state.paramYValid;
        let paramRValid = this.state.paramRValid;

        switch (fieldName) {
            case 'paramX':
                paramXValid = (value !== '');
                fieldValidationErrors.paramX = paramXValid ? '' : 'Выберите X';
                break;
            case 'paramR':
                paramRValid = (value !== '');
                fieldValidationErrors.paramR = paramRValid ? '' : 'Выберите R';
                break;
            case 'paramY':
                paramYValid = (value !== '');
                fieldValidationErrors.paramY = paramYValid ? '' : 'Введите Y';
                if (!paramYValid) break;
                paramYValid = (!(isNaN(value) && value || !isNaN(value) && (Number(value) < -5 || Number(value) > 3)));
                fieldValidationErrors.paramY = paramYValid ? '' : 'Введите число -5 до 3'
                break;
            default:
                break;
        }

        this.setState({
            formErrors: fieldValidationErrors,
            paramXValid: paramXValid,
            paramYValid: paramYValid,
            paramRValid: paramRValid,
        }, this.validateForm);

    }

    validateForm() {
        this.setState({
            formValid: this.state.paramXValid &&
                this.state.paramYValid && this.state.paramRValid
        });
    }


    render() {
        const {app} = this.props;
        return (
            <div className="commonGroup">
                <div className="fillItPls">Добро пожаловать в панель управления</div>
                <div className="XR">
                    <div className="chooseR">
                        <label id="chooseRTitle" className="chooseRTitle">Выберите R</label>
                        <Dropdown
                            auto
                            onChange={this.handleChangeR}
                            className="dropdown"
                            source={paramRValues}
                            value={app.r}
                        />
                    </div>

                    <div className="selectX">
                        <label id="selectXTitle" className="selectXTitle">Выберите X</label>
                        <Dropdown
                            auto
                            onChange={this.handleChangeX}
                            className="dropdown"
                            source={paramXValues}
                            value={app.x}
                        />
                    </div>

                </div>
                <div className="enterY">
                    <Input onChange={this.handleChangeY} placeholder="Введите значение Y от -5 до 3" id="inp"/>
                </div>
                <div className="formErrors">
                    <FormErrors formErrors={this.state.formErrors} answer={this.props.app.answer}/>
                </div>
                <div className="checkButton">
                    <button className="checkButtonInside" onClick={this.paramsIsReady}
                            disabled={!this.state.formValid}>Проверить
                    </button>
                </div>
            </div>

        )
    }

}

const mapStateToProps = store => {
    return {
        app: store.app,
    }
};

const mapDispatchToProps = dispatch => {
    return {
        setX: x => dispatch(setX(x)),
        setR: r => dispatch(setR(r)),
        setY: y => dispatch(setY(y)),
        setAnswer: answer => dispatch(setAnswer(answer)),
        sendPoint: point => dispatch(sendPoint(point))
    }
};


export default connect(mapStateToProps, mapDispatchToProps)(PointForm);
