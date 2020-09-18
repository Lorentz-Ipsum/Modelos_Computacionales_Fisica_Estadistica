// Variables del canvasFluc
var canvasFluc = document.getElementById('canvasFluctuaciones'),
    ctxFluc = canvasFluc.getContext('2d'),
    canvasFlucMarco = document.getElementById('canvasFluctuacionesMarco'),
    ctxFlucMarco = canvasFlucMarco.getContext('2d'),
    w = canvasFluc.width,
    h = canvasFluc.height,
    wMarco = canvasFluc.width,
    hMarco = canvasFluc.height,
    defRadius = document.getElementById('defradius');

var kTFluc = tempFluctuaciones.value,
    checkBoxFluc = conInterFluc.checked;

var runningFluc = false;

var ballsFluc = [];

var Ndif = [],
    Nleft = [],
    Nright = [],
    FramesFluc = [];
var tFluc = 0;
var vel = 10;

var optsFluc = {
    // bg: "white",
    ballsFlucAmount: nFluctuaciones.value,
    lineWidth: 3
};

Ball.prototype = {
    drawFluc: function() {
        ctxFluc.beginPath();
        ctxFluc.arc(this.x, this.y, this.radius, 0, Math.PI * 2, false);
        ctxFluc.fillStyle = this.color;
        ctxFluc.strokeStyle = this.color;
        ctxFluc.lineWidth = optsFluc.lineWidth;
        ctxFluc.fill();
        // ctxFluc.stroke();
        ctxFluc.closePath();
    },
    update: function() {
        this.x += this.direction.x * this.speed;
        this.y += this.direction.y * this.speed;

        if (checkBoxFluc) {
            for (var i = 0; i < ballsFluc.length; i++) {
                if (ballsFluc[i] === this) continue;
                var distance = getDistance(this.x, this.y, ballsFluc[i].x, ballsFluc[i].y);
                if (distance - (this.radius + ballsFluc[i].radius) < 0) {
                    this.direction.x = (this.x - ballsFluc[i].x) / distance; //get new cosinus
                    this.direction.y = (this.y - ballsFluc[i].y) / distance; //get new sinus
                }
            }
        };
        if (this.x - this.radius < 0 || this.x + this.radius > w) {
            this.direction.x = -this.direction.x;
        };
        if (this.y - this.radius < 0 || this.y + this.radius > h) {
            this.direction.y = -this.direction.y;
        };
    }
};

resetFluc();
simulateFluc();









/////////////////////////////
// FUNCIONES DE SIMULACION //
/////////////////////////////

// Funcion para empezar o pausar la simulacion
function startStopFluc() {
    runningFluc = !runningFluc;
    if (runningFluc) {
        console.log("START");
        i = 0;
        startBtnFluc.value = " Pause ";
        // reset();
    } else {
        startBtnFluc.value = "Resume";
    }
}

// Funcion para restaurar la grafica
function resetFluc() {
    kTFluc = tempFluctuaciones.value;
    checkBoxFluc = conInterFluc.checked;
    runningFluc = false;
    startBtnFluc.value = " Start ";
    optsFluc.ballsFlucAmount = nFluctuaciones.value;
    ballsFluc = [];

    Ndif = [];
    Nleft = [];
    Nright = [];
    FramesFluc = [];
    tFluc = 0;
    for (var i = 0; i < optsFluc.ballsFlucAmount; i++) {
        var radius = 6; // randomIntFromRangeFluc(10, h / 10);
        var x = randomIntFromRangeFluc(radius, w / 2 - radius);
        var y = randomIntFromRangeFluc(radius, h - radius);
        var color = 'blue';

        if (i !== 0) {
            for (var j = 0; j < ballsFluc.length; j++) {
                if (getDistance(x, y, ballsFluc[j].x, ballsFluc[j].y) - (radius + ballsFluc[j].radius) < 0) {
                    x = randomIntFromRangeFluc(radius, w / 2 - radius);
                    y = randomIntFromRangeFluc(radius, h - radius);
                    j = -1;
                }
            }
        }
        ballsFluc.push(new Ball(x, y, radius, color));
    };
    drawBackFluc();
    plotPart(FramesFluc, Ndif);
}

// Funcion del bucle de la simulacion
function simulateFluc() {
    if (runningFluc) {
        animateFluc();
    }
    window.setTimeout(simulateFluc, vel); //Aqui iria el ajuste de velocidad
}

function animateFluc() {
    Ndif[tFluc] = 0;
    Nleft[tFluc] = 0;
    Nright[tFluc] = 0;
    FramesFluc[tFluc] = tFluc;

    ctxFluc.clearRect(0, 0, w, h);

    ballsFluc.forEach(function(ball) {
        ball.drawFluc();
        ball.update();
        if (ball.x < w / 2) {
            Nleft[tFluc]++;
        } else {
            Nright[tFluc]++;
        }
    });
    Ndif[tFluc] = Nleft[tFluc] - Nright[tFluc];
    plotPart(FramesFluc, Ndif);
    tFluc++;
};








//////////////////////////
// FUNCIONES DE CALCULO //
//////////////////////////

function Ball(x, y, radius, color) {
    this.x = x;
    this.y = y;
    this.directionAngle = Math.random() * 360;
    this.direction = {
        x: Math.cos(this.directionAngle),
        y: Math.sin(this.directionAngle)
    };
    this.radius = radius;
    this.color = color;
    this.speed = Boltzmann(kTFluc); //5; //randomIntFromRangeFluc(1, 2) / 2;
}

function randomIntFromRangeFluc(min, max) {
    return Math.floor(min + Math.random() * (max - min + 1));
}

function getDistance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

function Boltzmann(kTFluc) {
    vel = Math.sqrt(-2.0 * Math.log(1.0 - Math.random()));
    return vel * Math.sqrt(kTFluc);
}


// Funcion para calcular la curva
function gaussiana() {
    var mu = 0,
        s2 = optsFluc.ballsFlucAmount,
        scale = 5,
        max = 4 * Math.sqrt(s2),
        X = [],
        Y = [];
    var k = 0;
    for (var i = -max; i < max; i++) {
        for (var j = 0; j < scale; j++) {
            X.push(i + (j + 1) / scale);
            Y.push((Math.sqrt(2 / (Math.PI * s2))) / Math.exp(Math.pow(X[k], 2) / (2 * s2)));
            k++;
        };
    };
    var traceGauss = {
        x: X,
        y: Y,
        mode: 'lines',
        line: {
            color: 'rgb(255,0,0)',
            width: 1,
            opacity: 0.7
        }
    }
    return traceGauss
}








///////////////////////
// FUNCIONES DE PLOT //
///////////////////////

// Funcion para dibujar el marco
function drawBackFluc() {
    ctxFlucMarco.fillStyle = 'lightgray';
    ctxFlucMarco.fillRect(0, 0, w, h);
    ctxFlucMarco.beginPath();
    ctxFlucMarco.moveTo(w / 2, 0);
    ctxFlucMarco.lineTo(w / 2, h);
    ctxFlucMarco.setLineDash([5, 15]);
    ctxFlucMarco.lineWidth = 3;
    ctxFlucMarco.strokeStyle = 'black';
    ctxFlucMarco.stroke();
}

// Funcion para plotear la diferencia de particulas en graphFluctuaciones
function plotPart(FramesFluc, Ndif) {
    // Trazas
    var numsTrace = {
        x: FramesFluc,
        y: Ndif,
        mode: 'lines',
        name: 'Diferencia',
        line: {
            color: 'rgb(0,0,255)',
            width: 1,
            opacity: 0.7
        }
    };
    var data = [numsTrace];
    var layout = {
        autosize: true,
        title: 'Diferencia de entre ambos lados',
        font: {
            size: 15
        },
        plot_bgcolor: 'rgb(223, 223, 223)',
        shapes: [
            //Line Horizontal
            {
                type: 'line',
                x0: 0,
                y0: Math.sqrt(optsFluc.ballsFlucAmount),
                x1: FramesFluc.length,
                y1: Math.sqrt(optsFluc.ballsFlucAmount),
                line: {
                    color: 'rgb(50,50,50,)',
                    width: 1,
                    dash: 'dashdot'
                }
            },
            {
                type: 'line',
                x0: 0,
                y0: -Math.sqrt(optsFluc.ballsFlucAmount),
                x1: FramesFluc.length,
                y1: -Math.sqrt(optsFluc.ballsFlucAmount),
                line: {
                    color: 'rgb(50,50,50,)',
                    width: 1,
                    dash: 'dashdot'
                }
            },
            {
                type: 'line',
                x0: 0,
                y0: 3 * Math.sqrt(optsFluc.ballsFlucAmount),
                x1: FramesFluc.length,
                y1: 3 * Math.sqrt(optsFluc.ballsFlucAmount),
                line: {
                    color: 'rgb(50,50,50,)',
                    width: 1,
                    dash: 'dot'
                }
            },
            {
                type: 'line',
                x0: 0,
                y0: -3 * Math.sqrt(optsFluc.ballsFlucAmount),
                x1: FramesFluc.length,
                y1: -3 * Math.sqrt(optsFluc.ballsFlucAmount),
                line: {
                    color: 'rgb(50,50,50,)',
                    width: 1,
                    dash: 'dot'
                }
            },
        ],
    };
    var config = {
        staticPlot: true,
        responsive: true,
    }

    Plotly.newPlot('graphFluctuaciones', data, layout, config);
}

function plotHistFluc() {
    var traceGauss = gaussiana();
    // Trazas
    var numsTrace = {
        x: Ndif,
        type: "histogram",
        histnorm: 'probability',
        name: 'Diferencia',
    };
    var data = [numsTrace, traceGauss];
    var layout = {
        autosize: true,
        title: 'Diferencia de números',
        font: {
            size: 15
        },
        plot_bgcolor: 'rgb(223, 223, 223)'
    };

    var config = {
        staticPlot: true,
        responsive: true,
    }

    Plotly.newPlot('graphHistFluc', data, layout, config);
}