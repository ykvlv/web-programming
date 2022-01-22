import React from 'react';
import {connect} from 'react-redux';

// TODO в каком виде хранятся?
class Table extends React.Component {
    render() {
        return (
            <div className="table">
                <table>
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>x</th>
                            <th>y</th>
                            <th>r</th>
                            <th>result</th>
                            <th>owner</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.app.table.map(point =>
                            <tr key={point.id}>
                                <td>{point.id}</td>
                                <td>{point.x}</td>
                                <td>{point.y}</td>
                                <td>{point.r}</td>
                                <td>{point.hit}</td>
                                <td>{point.owner}</td>
                            </tr>
                        )}
                    </tbody>
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

