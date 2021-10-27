const error_Y = document.querySelector("span[id=error_Y]");
const error_R = document.querySelector("span[id=error_R]");
const error_X = document.querySelector("span[id=error_X]");

const Y_MAX_DECIMAL = 9;
const X_MIN = -4, X_MAX = 4;
const Y_MIN = -5, Y_MAX = 3;

let xForm, yForm, rForm;
let previousX, previousR;

let canvas = new CoordinatesCanvas("canvas", 480, 8, X_MIN, X_MAX, Y_MIN, Y_MAX);

$(window).scroll(function () {
    move(this);
});

function move(ev) {
    if ($(ev).scrollTop() < 400) {
        $('#monkey').css('left', window.innerWidth * 0.85 - $(ev).scrollTop() / 1.5);
        $('#liana').css('left', window.innerWidth * 0.85 - $(ev).scrollTop() * 1.5);
    } else {
        $('#monkey').css('left', window.innerWidth * 0.85 - 400 / 1.5);
        $('#liana').css('left', window.innerWidth * 0.85 - 400 * 1.5);

    }
}

$("input[name=x]").click(function() {
    if (previousX !== null && previousX !== undefined) {
        previousX.style.borderColor = "black";
        previousX.style.borderWidth = "1px";
    }
    this.style.borderColor = "#329F5B";
    this.style.borderWidth = "3px";
    xForm = this.value;
    validateX();
    previousX = this;
});

$("input[name=r]").click(function() {
    if (previousR !== null && previousR !== undefined) {
        previousR.style.borderColor = "black";
        previousR.style.borderWidth = "1px";
    }
    this.style.borderColor = "#329F5B";
    this.style.borderWidth = "3px";
    rForm = this.value;
    validateR();
    previousR = this;

    canvas.setAreaRadius(this.value);
});

$("input[id=valueY]").keyup(function () {
    yForm = this.value.replace(",", ".");
    validateY();
})


function validateX() {
    if (xForm === null || xForm === undefined) {
        error_X.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_X.textContent = "";
    return true;
}

function validateY() {
    if (yForm === null || yForm === undefined || yForm === "") {
        error_Y.textContent = "Поле Y пустое";
    } else if (isNaN(parseFloat(yForm)) || !isFinite(yForm)) {
        error_Y.textContent = "Y — число";
    } else if ((yForm - parseFloat(yForm).toFixed(Y_MAX_DECIMAL)) !== 0) {
        error_Y.textContent = `Y не больше ${Y_MAX_DECIMAL} знаков после запятой`;
    } else if (yForm < Y_MIN || yForm > Y_MAX) {
        error_Y.textContent = `Y в пределах (${Y_MIN} ... ${Y_MAX})`;
    } else {
        error_Y.textContent = "";
        return true;
    }
    return false;
}

function validateR() {
    if (rForm === null || rForm === undefined || rForm === 0) {
        error_R.textContent = "Выберите значение из предложенных";
        return false;
    }
    error_R.textContent = "";
    return true;
}

function validate() {
    return Boolean(validateX(xForm) & validateY(yForm) & validateR(rForm));
}

function addHit() {
    if (validate()) {
        $.post("table/addHit", {
            "x": xForm,
            "y": yForm,
            "r": rForm
        }).done(function (data) {
            setTableFrom(data);
            canvas.render();
        });
    }
}

function addHitCanvas(x, y) {
    $.post("table/addHit", {
        "x": x,
        "y": y,
        "r": canvas.areaRadius
    }).done(function (data) {
        setTableFrom(data);
        canvas.render();
    });
}

function clearTable() {
    $.post("table/clearTable", {

    }).done(function (data) {
        setTableFrom(data);
        canvas.render();
    });
}

function setTableFrom(data) {
    let html = new DOMParser().parseFromString(data, "text/html");
    $("#result_table")[0].innerHTML = html.getElementById("result_table").innerHTML;
}

function init() {
    document.getElementById("valueY").setAttribute("placeholder", `от ${Y_MIN} до ${Y_MAX}`);
    move(window);
}
