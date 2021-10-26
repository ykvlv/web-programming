const error_Y = document.querySelector("span[id=error_Y]");
const error_R = document.querySelector("span[id=error_R]");
const error_X = document.querySelector("span[id=error_X]");

const valueY = document.querySelector("input[id=valueY]");
const Y_MAX_LENGTH = 15, Y_MIN = -5, Y_MAX = 3;
const X_MIN = -4, X_MAX = 4;
const R_MAX = 5;
document.getElementById("valueY").setAttribute("placeholder", `от ${Y_MIN} до ${Y_MAX}`);

let xForm, yForm, rForm;
let previousX, previousR;

let canvas = new CoordinatesCanvas("canvas", 40, X_MIN, X_MAX, Y_MIN, Y_MAX);
canvas.render()


$("input[name=x]").click(function() {
    if (previousX !== null && previousX !== undefined) {
        previousX.style.borderColor = "black";
    }
    error_X.textContent = "";
    this.style.borderColor = "rgb(212, 87, 75)";
    xForm = this.value;
    previousX = this;
});

$("input[name=r]").click(function() {
    if (previousR !== null && previousR !== undefined) {
        previousR.style.borderColor = "black";
    }
    error_R.textContent = "";
    this.style.borderColor = "rgb(212, 87, 75)";
    rForm = this.value;
    previousR = this;
    canvas.setAreaRadius(this.value);
    canvas.render();
});

$("input[id=valueY]").keyup(function () {
    yForm = valueY.value.replace(",", ".");
    validateY(yForm);
})


function validateX(x) {
    if (x === null || x === undefined) {
        error_X.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_X.textContent = "";
    return true;
}

function validateY(y) {
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

function validateR(r) {
    if (r === null || r === undefined || r === 0) {
        error_R.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_R.textContent = "";
    return true;
}

function validate(x, y, r) {
    return Boolean(validateX(x) & validateY(y) & validateR(r));
}

function addHit(x, y, r) {
    if (validate(x, y, r)) {
        $.post("table/addHit", {
            "x": x,
            "y": y,
            "r": r
        }).done(function (data) {
            setTableFrom(data);
            canvas.render()
        });
    }
}

function clearTable() {
    $.post("table/clearTable", {

    }).done(function (data) {
        setTableFrom(data);
        canvas.render()
    });
}

function setTableFrom(data) {
    let html = new DOMParser().parseFromString(data, "text/html");
    $("#result_table")[0].innerHTML = html.getElementById("result_table").innerHTML;
}