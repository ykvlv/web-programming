import axios from 'axios';

export const SET_X = 'SET_X';
export const SET_Y = 'SET_Y';
export const SET_R = 'SET_R';
export const ADD_POINT = "ADD_POINT";
export const SET_TABLE = "SET_TABLE";
export const SET_ANSWER = "SET_ANS WER";


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
            type:           "POST",
            contentType:    "application/json",
            dataType:       "json",
            data:           data,
        })
            .then(result => {
                // TODO что здесь должно было вернуться?
                console.log(result.data);
                if (result.data != null) {
                    dispatch({
                        type: SET_TABLE,
                        payload: result.data,
                    })
                } else {
                    dispatch({
                        type: SET_ANSWER,
                        payload: "Обмануть меня вздумали?"
                    })
                }
            })
            .catch(error => {
                const answer = "Данные не отправлены, ошибка " + error.response.status;

                dispatch({
                    type: SET_ANSWER,
                    payload: answer,
                });
            });
        // TODO зачем эти сбросы?
        dispatch({
            type: SET_X,
            payload: null,
        });
        document.getElementById("inp").value = "";
    }
}

export function getPoints() {
    return dispatch => {
        axios({
            url:        "/app/all",
            type:       "GET",
        }).then(data => {
            // TODO Необходимо понять в какмо виде ожидаются данные
            dispatch({
                type: SET_TABLE,
                payload: data.data
            })
        }).catch(error => {
            const answer = "Запрос не был отправлен, ошибка " + error.response.status;

            dispatch({
                type: SET_ANSWER,
                payload: answer,
            });
        });
    }
}
