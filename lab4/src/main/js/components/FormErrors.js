import React from 'react';

class FormErrors extends React.Component {
    render() {
        return (
            <div>
                {this.props.answer}
                {/*TODO эм ту хард*/}
                {/*{Object.keys(this.props.formErrors).map((fieldName, i) => {*/}
                {/*    if(this.props.formErrors[fieldName].length > 0){*/}
                {/*        return (*/}
                {/*            <p key={i}>{fieldName}: {this.props.formErrors[fieldName]}</p>*/}
                {/*        )*/}
                {/*    } else {*/}
                {/*        return '   ';*/}
                {/*    }*/}
                {/*})}*/}
            </div>
        )
    }
}

export default FormErrors;

