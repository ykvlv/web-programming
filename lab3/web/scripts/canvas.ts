class Point {
    readonly x: number;
    readonly y: number;

    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

class CoordinatesCanvas {
    private canvas: HTMLCanvasElement;
    private readonly unitSize: number;
    jsonHistory: any = ""
    private readonly scale: number;
    private readonly xMin: number;
    private readonly xMax: number;
    private readonly yMin: number;
    private readonly yMax: number;
    private readonly unitsX: number;
    private readonly unitsY: number;
    private areaRadius: number;
    private context: CanvasRenderingContext2D;

    constructor(id: string, size: number, scale: number, xMin: number, xMax: number, yMin: number, yMax: number, onClick: (event: MouseEvent) => void, radius: number) {
        this.canvas = document.getElementById(id) as HTMLCanvasElement;
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
        this.canvas.addEventListener('click', event => onClick(event));
        this.areaRadius = radius;
        this.render();
    }

    setAreaRadius(areaRadius) {
        this.areaRadius = areaRadius;
        this.render();
    }

    render() {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.renderArea();
        this.renderGrid();
        this.renderHistory();
    }

    renderGrid() {
        let dash = 5 * this.scale;
        let textSize = 12 * this.scale;
        this.context.font = textSize + "px Arial Rounded MT Bold";
        this.context.lineWidth = this.scale;
        this.context.strokeStyle = 'rgb(0,0,0)';
        this.context.fillStyle = 'rgb(0,0,0)';

        for (let i = this.xMin; i <= this.xMax; i++) {
            let {x, y} = this.fromUnits(i, 0);
            this.context.beginPath();
            this.context.moveTo(x, y);
            this.context.lineTo(x + this.unitSize, y);
            if (i !== 0 && i > this.xMin && i < this.xMax) {
                this.context.moveTo(x, y - dash);
                this.context.lineTo(x, y + dash);
                let text = i.toString();
                let size = this.context.measureText(text);
                this.context.fillText(text, x - size.width / 2, y + dash + textSize);
            }
            this.context.stroke();
        }
        for (let i = this.yMin; i <= this.yMax; i++) {
            let {x, y} = this.fromUnits(0, i);
            this.context.beginPath();
            this.context.moveTo(x, y);
            this.context.lineTo(x, y - this.unitSize);
            if (i !== 0 && i > this.yMin && i < this.yMax) {
                this.context.moveTo(x - dash, y);
                this.context.lineTo(x + dash, y);
                let text = i.toString();
                let size = this.context.measureText(text);
                this.context.fillText(text, x - size.width - dash, y + textSize / 2);
            }
            this.context.stroke();
        }
        let text = '0';
        let size = this.context.measureText(text);
        let {x, y} = this.fromUnits(0, 0);
        this.context.fillText(text, x - size.width - dash, y + dash + textSize);
    }

    renderArea() {
        this.context.beginPath();
        this.context.fillStyle = "#329F5B";
        let pts = [
            [this.areaRadius/2, 0],
            [this.areaRadius, 0],
            [0, -this.areaRadius/2],
            [0, -this.areaRadius],
            [-this.areaRadius, -this.areaRadius],
            [-this.areaRadius, 0],
            [0, 0]
        ];
        for (let i = 0; i < pts.length; i++) {
            let {x, y} = this.fromUnits(pts[i][0], pts[i][1]);
            if (i === 0) {
                this.context.moveTo(x, y);
            } else {
                this.context.lineTo(x, y);
            }
        }
        let {x, y} = this.fromUnits(0, 0);
        this.context.arc(x, y, this.areaRadius / 2 * this.unitSize, -Math.PI / 2, 0);
        this.context.lineTo(x, y);
        this.context.fill();
    }

    renderHistory() {
        if (this.jsonHistory != "") {
            for (let row of this.jsonHistory.hitList) {
                let {x, y} = this.fromUnits(
                    parseFloat(row.x),
                    parseFloat(row.y)
                );
                let result = row.result;

                this.context.beginPath();
                this.context.fillStyle = result == true ? 'rgb(40, 227, 38)' : 'rgb(236, 31, 43)';
                this.context.arc(x, y, 4 * this.scale, 0, 2 * Math.PI);
                this.context.fill();
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
