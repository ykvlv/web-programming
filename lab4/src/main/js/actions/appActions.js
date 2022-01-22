import axios from 'axios';

export const SET_X = 'SET_X';
export const SET_Y = 'SET_Y';
export const SET_R = 'SET_R';
export const ADD_POINT = "ADD_POINT";
export const SET_TABLE = "SET_TABLE";
export const SET_ANSWER = "SET_ANSWER";


export function setR(R) {
    return {
        type: SET_R,
        payload: R
    }
}

export function setX(X) {
    return {
        type: SET_X,
        payload: X
    }
}

export function setY(Y) {
    return {
        type: SET_Y,
        payload: Y
    }
}

export function setAnswer(answer) {
    return {
        type: SET_ANSWER,
        payload: answer
    }
}
export function sendPoint(point) {
    // да вообще по*уй 2
    const data = '{"x":"' + point.x +
        '","y":"' + point.y +
        '","r":"' + point.r + '"}'

    return dispatch => {
        axios({
            url:            "/app",
            method:         "POST",
            data:           data,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(result => {
                // TODO что здесь должно было вернуться?
                console.log(result);
                console.log(result.data.message);
                if (result.data.success) {
                    dispatch({
                        type: ADD_POINT,
                        payload: result.data.object,
                    })
                } else {
                    dispatch({
                        type: SET_ANSWER,
                        payload: result.data.message
                    })
                }
            })
            .catch(error => {
                console.log(error)
                const answer = "Данные не отправлены, ошибка";
                dispatch({
                    type: SET_ANSWER,
                    payload: answer,
                });
            });
    }
}

export function getPoints() {
    return dispatch => {
        axios({
            url:        "/app/all",
            type:       "GET",
        }).then(data => {
            console.log(data);
            dispatch({
                type: SET_TABLE,
                payload: data.data.object
            })
        }).catch(error => {
            console.log(error)
            const answer = "Ошибка, не удается получить попадания.";
            dispatch({
                type: SET_ANSWER,
                payload: answer,
            });
        });
    }
}
