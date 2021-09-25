const in_Y = document.querySelector("input[id=in_Y]");
const sl_R = document.querySelector("select[id=sl_R]");
const ch_X = document.querySelectorAll("input[name=X]");
let ok_X;

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
            ok_X = ch_X[i]
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

    } else if (y <= -3 || y >= 5) {
        error_in_Y.textContent = "Y в пределах (-3 ... 5)";
    } else {
        yValid = true;
        error_in_Y.textContent = "";
    }

    // r validation
    if (sl_R.value === "not_chosen") {
        error_sl_R.textContent = "Выберите значение из предложенных"
    } else {
        error_sl_R.textContent = "";
        rValid = true;
    }
    return xValid && yValid && rValid;
}

function ok() {
    if (validateForm()) {
        $.get("php/check.php", {
            'x': ok_X.value,
            'y': in_Y.value,
            'r': sl_R.value,
            'timezone': new Date().getTimezoneOffset()
        }).done(function (data) {
            let arr = JSON.parse(data);
            arr.forEach(function (elem) {
                if (!elem.validate) return;
                let newRow = '<tr class="im_class">';
                newRow += '<td>' + elem.xVal + '</td>';
                newRow += '<td>' + elem.yVal + '</td>';
                newRow += '<td>' + elem.rVal + '</td>';
                newRow += '<td>' + elem.curtime + '</td>';
                newRow += '<td>' + elem.exectime + '</td>';
                newRow += '<td>' + elem.hitres + '</td>';
                $('#resultTable').append(newRow);
                localStorage.setItem("arr", localStorage.getItem("arr") + newRow);
            });
        })
    }
}

function clearTable() {
    $('.im_class').remove();
    localStorage.setItem("arr", "");
}

function start() {
    if (localStorage.getItem("arr") == null) {
        localStorage.setItem("arr", "");
    } else {
        $('#resultTable').append(localStorage.getItem("arr"))
    }
}

start();
