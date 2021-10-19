const in_Y = document.querySelector("input[id=in_Y]");
const sl_R = document.querySelector("select[id=sl_R]");
const ch_X = document.querySelectorAll("input[name=x]");
const Y_MAX_LENGTH = 15, Y_MIN = -3, Y_MAX = 5;
let x, y, r;

const error_in_Y = document.querySelector("span[id=error_in_Y]");
const error_sl_R = document.querySelector("span[id=error_sl_R]");
const error_ch_X = document.querySelector("span[id=error_ch_X]");

function validateX() {
    for (let i = 0; i < ch_X.length; i++) {
        if (ch_X[i].checked) {
            x = ch_X[i].value
            error_ch_X.textContent = "";
            return true;
        }
    }
    error_ch_X.textContent = "Выберите значение из предложенных";
    return false;
}

function validateY() {
    y = in_Y.value.replace(",", ".");
    if (y === undefined || y === "") {
        error_in_Y.textContent = "Поле Y пустое";
    } else if (isNaN(parseFloat(y)) || !isFinite(y)) {
        error_in_Y.textContent = "Y — число";
    } else if (y.length > Y_MAX_LENGTH) {
        error_in_Y.textContent = "Длина Y не более 15 символов";
    } else if (y <= Y_MIN || y >= Y_MAX) {
        error_in_Y.textContent = "Y в пределах (-3 ... 5)";
    } else {
        error_in_Y.textContent = "";
        return true;
    }
    return false;
}

function validateR() {
    r = sl_R.value;
    if (r === undefined || r === "not_chosen") {
        error_sl_R.textContent = "Выберите значение из предложенных"
    } else {
        error_sl_R.textContent = "";
        return true;
    }
    return false;
}

function validateForm() {
    return Boolean(validateX() & validateY() & validateR());
}
