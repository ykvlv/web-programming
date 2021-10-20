const error_Y = document.querySelector("span[id=error_Y]");
const error_R = document.querySelector("span[id=error_R]");
const error_X = document.querySelector("span[id=error_X]");

const in_Y = document.querySelector("input[id=valueY]");
const Y_MAX_LENGTH = 15, Y_MIN = -5, Y_MAX = 3;
document.getElementById("valueY").setAttribute("placeholder", `от ${Y_MIN} до ${Y_MAX}`);

let x, y, r;
let previousX, previousR;

$("input[name=x]").click(function() {
    if (previousX !== null && previousX !== undefined) {
        previousX.style.borderColor = "black";
    }
    error_X.textContent = "";
    this.style.borderColor = "rgb(212, 87, 75)";
    x = this.value;
    previousX = this;
});

$("input[name=r]").click(function() {
    if (previousR !== null && previousR !== undefined) {
        previousR.style.borderColor = "black";
    }
    error_R.textContent = "";
    this.style.borderColor = "rgb(212, 87, 75)";
    r = this.value;
    previousR = this;
});

$("input[id=valueY]").keyup(function () {
    validateY();
})


function validateX() {
    if (x === null || x === undefined) {
        error_X.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_X.textContent = "";
    return true;
}

function validateY() {
    y = in_Y.value.replace(",", ".");
    if (y === undefined || y === "") {
        error_Y.textContent = "Поле Y пустое";
    } else if (isNaN(parseFloat(y)) || !isFinite(y)) {
        error_Y.textContent = "Y — число";
    } else if (y.length > Y_MAX_LENGTH) {
        error_Y.textContent = `Длина Y не более ${Y_MAX_LENGTH} символов`;
    } else if (y < Y_MIN || y > Y_MAX) {
        error_Y.textContent = `Y в пределах (${Y_MIN} ... ${Y_MAX})`;
    } else {
        error_Y.textContent = "";
        return true;
    }
    return false;
}

function validateR() {
    if (r === null || r === undefined) {
        error_R.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_R.textContent = "";
    return true;
}

function validateForm() {
    return Boolean(validateX() & validateY() & validateR());
}

function addHit() {
    if (validateForm()) {
        $.post("table/addHit", {
            "x": x,
            "y": y,
            "r": r
        }).done(function (data) {
            console.log(data);
        })
    }
}