const error_Y = document.querySelector("span[id=error_Y]");
const error_R = document.querySelector("span[id=error_R]");
const error_X = document.querySelector("span[id=error_X]");

const in_Y = document.querySelector("input[id=valueY]");
const Y_MAX_LENGTH = 15, Y_MIN = -5, Y_MAX = 3;
const X_MIN = -4, X_MAX = 4;
const R_MAX = 5;
document.getElementById("valueY").setAttribute("placeholder", `от ${Y_MIN} до ${Y_MAX}`);

let x, y, r;
let xCanvas, yCanvas;
let previousX, previousR;
let canvas;

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

    canvas.render();
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
            let html = new DOMParser().parseFromString(data, "text/html");
            $("#result_table")[0].innerHTML = html.getElementById("result_table").innerHTML;

            canvas.render()
        });
    }
}

function addHitCanvas() {
    $.post("table/addHit", {
        "x": xCanvas,
        "y": yCanvas,
        "r": r
    }).done(function (data) {
        let html = new DOMParser().parseFromString(data, "text/html");
        $("#result_table")[0].innerHTML = html.getElementById("result_table").innerHTML;

        canvas.render()
    });
}

function clearTable() {
    $.post("table/clearTable").done(function (data) {
        let html = new DOMParser().parseFromString(data, "text/html");
        $("#result_table")[0].innerHTML = html.getElementById("result_table").innerHTML;

        canvas.render()
    });
}

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

class CoordinatesCanvas {

    constructor(id, unitSize, xMin, xMax, yMin, yMax) {
        this.canvas = document.getElementById(id);
        this.unitSize = unitSize;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.unitsX = (xMax - xMin);
        this.unitsY = (yMax - yMin);
        this.canvas.width = this.unitsX * this.unitSize;
        this.canvas.height = this.unitsY * this.unitSize;
        this.canvas.style.width = this.canvas.width + 'px';
        this.canvas.style.height = this.canvas.height + 'px';
        this.canvas.addEventListener('click', (event) => this.onClick(event));
        this.ctx = this.canvas.getContext("2d");
        this.render();
    }

    onClick(event) {
        if (validateR()) {
            const rect = this.canvas.getBoundingClientRect();
            const canvasX = event.clientX - rect.left;
            const canvasY = event.clientY - rect.top;
            let p = this.toUnits(canvasX, canvasY);
            xCanvas = p.x.toFixed(8);
            yCanvas = p.y.toFixed(8);
            addHitCanvas();
        }
    }

    setAreaRadius(areaRadius) {
        r = areaRadius;
    }

    render() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.renderArea();
        this.renderGrid();
        this.renderHistory();
    }

    renderGrid() {
        let dash = 5;
        let textSize = 12;
        this.ctx.font = textSize + "px sans-serif";
        this.ctx.strokeStyle = 'rgb(0,0,0)';
        this.ctx.fillStyle = 'rgb(0,0,0)';

        for (let i = this.xMin; i <= this.xMax; i++) {
            let {x, y} = this.fromUnits(i, 0);
            this.ctx.beginPath();
            this.ctx.moveTo(x, y);
            this.ctx.lineTo(x + this.unitSize, y);
            if (i !== 0 && i > this.xMin && i < this.xMax) {
                this.ctx.moveTo(x, y - dash);
                this.ctx.lineTo(x, y + dash);
                let text = i.toString();
                let size = this.ctx.measureText(text);
                this.ctx.fillText(text, x - size.width / 2, y + dash + textSize);
            }
            this.ctx.stroke();
        }
        for (let i = this.yMin; i <= this.yMax; i++) {
            let {x, y} = this.fromUnits(0, i);
            this.ctx.beginPath();
            this.ctx.moveTo(x, y);
            this.ctx.lineTo(x, y - this.unitSize);
            if (i !== 0 && i > this.yMin && i < this.yMax) {
                this.ctx.moveTo(x - dash, y);
                this.ctx.lineTo(x + dash, y);
                let text = i.toString();
                let size = this.ctx.measureText(text);
                this.ctx.fillText(text, x - size.width - dash, y + textSize / 2);
            }
            this.ctx.stroke();
        }
        let text = '0';
        let size = this.ctx.measureText(text);
        let {x, y} = this.fromUnits(0, 0);
        this.ctx.fillText(text, x - size.width - dash, y + dash + textSize);
    }

    renderArea() {
        this.ctx.beginPath();
        this.ctx.fillStyle = "rgb(212, 87, 75)";
        let pts = [
            [-r/2, 0],
            [-r, 0],
            [-r, r],
            [0, r],
            [0, 0],
            [r/2, 0],
            [0, -r]
        ]
        for (let i = 0; i < pts.length; i++) {
            let p = this.fromUnits(pts[i][0], pts[i][1]);
            if (i === 0) {
                this.ctx.moveTo(p.x, p.y);
            } else {
                this.ctx.lineTo(p.x, p.y);
            }
        }
        let c = this.fromUnits(0, 0);
        this.ctx.arc(c.x, c.y, r / 2 * this.unitSize, Math.PI / 2, Math.PI);
        this.ctx.lineTo(c.x, c.y);
        this.ctx.fill();
    }

    renderHistory() {
        if (r === null || r === undefined) {
            return;
        }
        let rows = document.getElementsByClassName("history");
        for (let row of Array.from(rows)) {
            console.log(row);
            let p = this.fromUnits(
                parseFloat(row.children[0].innerText.replace(",", ".")),
                parseFloat(row.children[1].innerText.replace(",", "."))
            );

            let hit = row.children[5].innerText;
            this.ctx.beginPath();
            this.ctx.fillStyle = hit === 'true' ? 'rgb(0, 255, 0)' : 'rgb(255, 0, 0)';
            this.ctx.arc(p.x, p.y, 4, 0, 2 * Math.PI);
            this.ctx.fill();
        }
    }

    fromUnits(x, y) {
        return new Point(
            (x - this.xMin) * this.unitSize,
            this.canvas.height - (y - this.yMin) * this.unitSize,
        );
    }

    toUnits(x, y) {
        return new Point(
            (x / this.unitSize) + this.xMin,
            ((this.canvas.height - y) / this.unitSize) + this.yMin,
        );
    }
}

function init() {
    canvas = new CoordinatesCanvas('canvas', 40, X_MIN, X_MAX, Y_MIN, Y_MAX);
    canvas.setAreaRadius(r);
    canvas.render();
}