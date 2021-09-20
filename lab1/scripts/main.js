const in_Y = document.querySelector("input[id=in_Y]");
const sl_R = document.querySelector("input[id=sl_R]");
const ch_X = document.querySelectorAll("input[name=choose_X]");

const error_in_Y = document.querySelector("span[id=error_in_Y]");
const error_sl_R = document.querySelector("span[id=error_sl_R]");
const error_ch_X = document.querySelector("span[id=error_ch_X]");

function validateForm() {
    let formValid = true;

    for (let i = 0; i < ch_X.length; i++) {
        if (!ch_X[i].checked) {
            error_ch_X.textContent = "Выберите значение из предложенных."
            return false;
        }
    }

    let value_in_Y = in_Y.value.replace(",", ".");
    if (value_in_Y === undefined) {
        error_in_Y.textContent = "Поле Y пустое."
        return false;
    }
    if (isNaN(parseFloat(value_in_Y)) || !isFinite(value_in_Y)) {
        error_in_Y.textContent = "X должен быть числом от -4.5 до 4.5!"
        return false;
    }
    if (value_in_Y < 15) {
        addToErrorMessage(`Длина числа должна быть не более ${maxLength} символов`);
        return false;
    }
    if (!((x > -3) && (x < 5))) {
        addToErrorMessage("Нарушена область допустимых значений X (-3; 5)");
        return false;
    }

    x = document.querySelector("input[id=xCoordinate]").value.replace(",", ".");

}

function showError() {
}