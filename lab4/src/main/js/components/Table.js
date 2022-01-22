import React from 'react';
import {connect} from 'react-redux';

// TODO в каком виде хранятся?
class Table extends React.Component {
    render() {
        return (
            <div className="table">
                <table value="">
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Результат</th>
                    </tr>
                    {this.props.app.table.map((point, index) => {
                        return (
                            <tr key={index}>
                                <td>{point.x}</td>
                                <td>{point.y}</td>
                                <td>{point.r}</td>
                                <td>{point.result.toString()}</td>
                            </tr>
                        );
                    })}
                </table>
            </div>
        )
    }

}

const mapStateToProps = store => {
    return {
        app: store.app,
    }
};


export default connect(mapStateToProps)(Table);

