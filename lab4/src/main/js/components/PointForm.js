import React from 'react';
import { RadioGroup, RadioButton } from 'react-toolbox/lib/radio';
import { Slider } from 'react-toolbox/lib/slider';

import FormErrors from "./FormErrors";
import { connect } from 'react-redux';
import { sendPoint, setAnswer, setR, setX, setY } from "../actions/appActions";

// TODO переделать под себя
const paramXValues = [-4, -3, -2, -1, 0, 1, 2, 3, 4];

const paramRValues = [1, 2, 3, 4];


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
        if (!this.state.formValid) errors.all = 'Провести меня вздумали? Поля заполните и не буяньте тут';
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
                fieldValidationErrors.paramX = paramXValid ? '' : 'Вы же X не выбрали, повнимательнее';
                break;
            case 'paramR':
                paramRValid = (value !== '');
                fieldValidationErrors.paramR = paramRValid ? '' : 'Вы же R не выбрали, повнимательнее';
                break;
            case 'paramY':
                paramYValid = (value !== '');
                fieldValidationErrors.paramY = paramYValid ? '' : 'А Y я вводить буду?';
                if (!paramYValid) break;
                paramYValid = (!(isNaN(value) && value || !isNaN(value) && (Number(value) < -3 || Number(value) > 5)));
                fieldValidationErrors.paramY = paramYValid ? '' : 'Числоооо нужноооо от -3 до 5'
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
                <div className="fillItPls">Заполните тут все, позязя</div>
                <div className="XR">
                    <div className="chooseR">
                        <label id="chooseRTitle" className="chooseRTitle">Выберите R</label>
                        <RadioGroup value={app.r} onChange={this.handleChangeR}>
                            {paramRValues.map(number =>
                                <RadioButton key={number} label={number} value={number.toString()}/>
                            )}
                        </RadioGroup>
                    </div>

                    <div className="selectX">
                        <label id="selectXTitle" className="selectXTitle">Выберите X</label>
                        <RadioGroup value={app.x} onChange={this.handleChangeX}>
                            {paramXValues.map(number =>
                                <RadioButton key={number} label={number} value={number.toString()}/>
                            )}
                        </RadioGroup>
                    </div>

                </div>
                <div className="enterY">
                    <Slider min={-3} max={5} editable onChange={this.handleChangeY} />
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

