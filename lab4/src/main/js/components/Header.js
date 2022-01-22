import React from 'react';

class Header extends React.Component {
    render() {
        const { name, surname, variant, group } = this.props;
        return (
            <div className="header">
                <h2>{surname} {name} {group}. Вариант {variant}</h2>
            </div>
        )
    }
}

export default Header;
