class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

class CoordinatesCanvas {

    constructor(id, size, scale, xMin, xMax, yMin, yMax) {
        this.canvas = document.getElementById(id);
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
        this.canvas.addEventListener('click', (event) => this.onClick(event));
        this.areaRadius = 0;
        this.ctx = this.canvas.getContext("2d");
        this.render();
    }

    onClick(event) {
        if (validateR(this.areaRadius)) {
            const rect = this.canvas.getBoundingClientRect();
            const canvasX = event.clientX - rect.left;
            const canvasY = event.clientY - rect.top;
            let p = this.toUnits(canvasX, canvasY);
            addHitCanvas(p.x.toFixed(9), p.y.toFixed(9));
        }
    }

    setAreaRadius(areaRadius) {
        this.areaRadius = areaRadius;
        this.render();
    }

    render() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.renderArea();
        this.renderGrid();
        this.renderHistory();
    }

    renderGrid() {
        let dash = 5 * this.scale;
        let textSize = 12 * this.scale;
        this.ctx.font = textSize + "px ALSSchlangesans";
        this.ctx.lineWidth = this.scale;
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
            [-this.areaRadius/2, 0],
            [-this.areaRadius, 0],
            [-this.areaRadius, this.areaRadius],
            [0, this.areaRadius],
            [0, 0],
            [this.areaRadius/2, 0],
            [0, -this.areaRadius]
        ];
        for (let i = 0; i < pts.length; i++) {
            let {x, y} = this.fromUnits(pts[i][0], pts[i][1]);
            if (i === 0) {
                this.ctx.moveTo(x, y);
            } else {
                this.ctx.lineTo(x, y);
            }
        }
        let c = this.fromUnits(0, 0);
        this.ctx.arc(c.x, c.y, this.areaRadius / 2 * this.unitSize, Math.PI / 2, Math.PI);
        this.ctx.lineTo(c.x, c.y);
        this.ctx.fill();
    }

    renderHistory() {
        let rows = document.getElementsByClassName("validHit");
        for (let row of Array.from(rows)) {
            let {x, y} = this.fromUnits(
                parseFloat(row.children[0].innerText.replace(",", ".")),
                parseFloat(row.children[1].innerText.replace(",", "."))
            );
            let r = row.children[2].innerText;
            let result = row.children[5].innerText;

            if (this.areaRadius === r) {
                this.ctx.beginPath();
                this.ctx.fillStyle = result === 'true' ? 'rgb(40, 227, 38)' : 'rgb(236, 31, 43)';
                this.ctx.arc(x, y, 4 * this.scale, 0, 2 * Math.PI);
                this.ctx.fill();
            }
        }
    }

    fromUnits(x, y) {
        return new Point(
            (x - this.xMin) * this.unitSize,
            this.canvas.height - (y - this.yMin) * this.unitSize
        );
    }

    toUnits(x, y) {
        return new Point(
            (x / this.unitSize * this.scale) + this.xMin,
            ((this.canvas.height - y * this.scale) / this.unitSize) + this.yMin
        );
    }
}
