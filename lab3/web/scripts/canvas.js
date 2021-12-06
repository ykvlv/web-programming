var Point = /** @class */ (function () {
    function Point(x, y) {
        this.x = x;
        this.y = y;
    }
    return Point;
}());
var CoordinatesCanvas = /** @class */ (function () {
    function CoordinatesCanvas(id, size, scale, xMin, xMax, yMin, yMax, onClick, radius) {
        this.jsonHistory = "";
        this.canvas = document.getElementById(id);
        this.context = this.canvas.getContext("2d");
        this.unitSize = size / 8 * scale;
        this.scale = scale;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.unitsX = xMax - xMin;
        this.unitsY = yMax - yMin;
        this.canvas.width = this.unitsX * this.unitSize;
        this.canvas.height = this.unitsY * this.unitSize;
        this.canvas.style.width = this.canvas.width / this.scale + 'px';
        this.canvas.style.height = this.canvas.height / this.scale + 'px';
        this.canvas.addEventListener('click', function (event) { return onClick(event); });
        this.areaRadius = radius;
        this.render();
    }
    CoordinatesCanvas.prototype.setAreaRadius = function (areaRadius) {
        this.areaRadius = areaRadius;
        this.render();
    };
    CoordinatesCanvas.prototype.render = function () {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.renderArea();
        this.renderGrid();
        this.renderHistory();
    };
    CoordinatesCanvas.prototype.renderGrid = function () {
        var dash = 5 * this.scale;
        var textSize = 12 * this.scale;
        this.context.font = textSize + "px Arial Rounded MT Bold";
        this.context.lineWidth = this.scale;
        this.context.strokeStyle = 'rgb(0,0,0)';
        this.context.fillStyle = 'rgb(0,0,0)';
        for (var i = this.xMin; i <= this.xMax; i++) {
            var _a = this.fromUnits(i, 0), x_1 = _a.x, y_1 = _a.y;
            this.context.beginPath();
            this.context.moveTo(x_1, y_1);
            this.context.lineTo(x_1 + this.unitSize, y_1);
            if (i !== 0 && i > this.xMin && i < this.xMax) {
                this.context.moveTo(x_1, y_1 - dash);
                this.context.lineTo(x_1, y_1 + dash);
                var text_1 = i.toString();
                var size_1 = this.context.measureText(text_1);
                this.context.fillText(text_1, x_1 - size_1.width / 2, y_1 + dash + textSize);
            }
            this.context.stroke();
        }
        for (var i = this.yMin; i <= this.yMax; i++) {
            var _b = this.fromUnits(0, i), x_2 = _b.x, y_2 = _b.y;
            this.context.beginPath();
            this.context.moveTo(x_2, y_2);
            this.context.lineTo(x_2, y_2 - this.unitSize);
            if (i !== 0 && i > this.yMin && i < this.yMax) {
                this.context.moveTo(x_2 - dash, y_2);
                this.context.lineTo(x_2 + dash, y_2);
                var text_2 = i.toString();
                var size_2 = this.context.measureText(text_2);
                this.context.fillText(text_2, x_2 - size_2.width - dash, y_2 + textSize / 2);
            }
            this.context.stroke();
        }
        var text = '0';
        var size = this.context.measureText(text);
        var _c = this.fromUnits(0, 0), x = _c.x, y = _c.y;
        this.context.fillText(text, x - size.width - dash, y + dash + textSize);
    };
    CoordinatesCanvas.prototype.renderArea = function () {
        this.context.beginPath();
        this.context.fillStyle = "#329F5B";
        var pts = [
            [this.areaRadius / 2, 0],
            [this.areaRadius, 0],
            [0, -this.areaRadius / 2],
            [0, -this.areaRadius],
            [-this.areaRadius, -this.areaRadius],
            [-this.areaRadius, 0],
            [0, 0]
        ];
        for (var i = 0; i < pts.length; i++) {
            var _a = this.fromUnits(pts[i][0], pts[i][1]), x_3 = _a.x, y_3 = _a.y;
            if (i === 0) {
                this.context.moveTo(x_3, y_3);
            }
            else {
                this.context.lineTo(x_3, y_3);
            }
        }
        var _b = this.fromUnits(0, 0), x = _b.x, y = _b.y;
        this.context.arc(x, y, this.areaRadius / 2 * this.unitSize, -Math.PI / 2, 0);
        this.context.lineTo(x, y);
        this.context.fill();
    };
    CoordinatesCanvas.prototype.renderHistory = function () {
        if (this.jsonHistory != "") {
            for (var _i = 0, _a = this.jsonHistory.hitList; _i < _a.length; _i++) {
                var row = _a[_i];
                var _b = this.fromUnits(parseFloat(row.x), parseFloat(row.y)), x = _b.x, y = _b.y;
                var result = row.result;
                this.context.beginPath();
                this.context.fillStyle = result == true ? 'rgb(40, 227, 38)' : 'rgb(236, 31, 43)';
                this.context.arc(x, y, 4 * this.scale, 0, 2 * Math.PI);
                this.context.fill();
            }
        }
    };
    CoordinatesCanvas.prototype.fromUnits = function (x, y) {
        return new Point((x - this.xMin) * this.unitSize, this.canvas.height - (y - this.yMin) * this.unitSize);
    };
    CoordinatesCanvas.prototype.toUnits = function (x, y) {
        return new Point((x / this.unitSize * this.scale) + this.xMin, ((this.canvas.height - y * this.scale) / this.unitSize) + this.yMin);
    };
    return CoordinatesCanvas;
}());
