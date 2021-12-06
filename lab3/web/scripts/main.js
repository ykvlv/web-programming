const X_MIN = -4, X_MAX = 4;
const Y_MIN = -3, Y_MAX = 5;
const R_MIN = 2, R_MAX = 5;

const canvas = new CoordinatesCanvas("canvas", 480, 8, X_MIN, X_MAX, Y_MIN, Y_MAX, sendHit, document.getElementById("canvas-form:canvas-r").value);
dbConnect()
updateHits()

function dbConnect() {
    getButtonByValue("Подключиться").click()
}

function rChanged2() {
    let value = document.getElementById("main-form:r").value
    if (!(value === undefined) && !(value === "") && !isNaN(parseFloat(value)) && isFinite(value)) {
        if (R_MIN <= value && value <= R_MAX) {
            document.getElementById("canvas-form:canvas-r").value = value;
            canvas.setAreaRadius(parseFloat(value).toFixed(3))
        }
    }
}

function rChanged() {
    let value = document.getElementById("canvas-form:canvas-r").value
    if (!(value === undefined) && !(value === "") && !isNaN(parseFloat(value)) && isFinite(value)) {
        if (R_MIN <= value && value <= R_MAX) {
            canvas.setAreaRadius(parseFloat(value).toFixed(3))
        }
    }
}

function updateHits() {
    canvas.jsonHistory = JSON.parse(document.getElementById("table-form:json-history").innerText)
    canvas.render()
}

function getButtonByValue(value) {
    let els = document.getElementsByTagName('button');
    for (let i = 0, length = els.length; i < length; i++) {
        if (els[i].innerHTML === "<span class=\"ui-button-text ui-c\">" + value + "</span>") {
            return els[i]
        }
    }
}

function sendHit(event) {
    const rect = document.getElementById("canvas").getBoundingClientRect();
    const canvasX = event.clientX - rect.left;
    const canvasY = event.clientY - rect.top;
    let {x, y} = canvas.toUnits(canvasX, canvasY);
    y = y.toFixed(3)
    x = x.toFixed(3)

    document.getElementById("canvas-form:canvas-x").value = x
    document.getElementById("canvas-form:canvas-y").value = y
    getButtonByValue("Canvas").click()
}
