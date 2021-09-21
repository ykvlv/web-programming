const in_Y = document.querySelector("input[id=in_Y]");
const sl_R = document.querySelector("select[id=sl_R]");
const ch_X = document.querySelectorAll("input[name=choose_X]");

const error_in_Y = document.querySelector("span[id=error_in_Y]");
const error_sl_R = document.querySelector("span[id=error_sl_R]");
const error_ch_X = document.querySelector("span[id=error_ch_X]");

function validateForm() {
    let xValid = false;
    let yValid = false;
    let rValid = false;

    // x validation
    for (let i = 0; i < ch_X.length; i++) {
        if (ch_X[i].checked) {
            xValid = true;
            error_ch_X.textContent = "";
        }
    }
    if (!xValid) {
        error_ch_X.textContent = "Выберите значение из предложенных";
    }

    // y validation
    let y = in_Y.value.replace(",", ".");
    if (y === "" || y === undefined) {
        error_in_Y.textContent = "Поле Y пустое";

    } else if (isNaN(parseFloat(y)) || !isFinite(y)) {
        error_in_Y.textContent = "Y — число";

    } else if (y.length > 15) {
        error_in_Y.textContent = "Длина Y не более 15 символов";

    } else if (y <= -4.5 || y >= 4.5) {
        error_in_Y.textContent = "Y в пределах (-4.5; 4.5)";
    } else {
        yValid = true;
        error_in_Y.textContent = "";
    }

    // r validation
    if (sl_R.value === "value0") {
        error_sl_R.textContent = "Выберите значение из предложенных"
    } else {
        error_sl_R.textContent = "";
        rValid = true;
    }
    return xValid && yValid && rValid;
}