var canvasBackBos = document.getElementById("canvasBackBose");
var ctxBackBos = canvasBackBos.getContext("2d");
var canvasFrontBos = document.getElementById("canvasFrontBose");
var ctxFrontBos = canvasFrontBos.getContext("2d");
var canvasBackFer = document.getElementById("canvasBackFermi");
var ctxBackFer = canvasBackFer.getContext("2d");
var canvasFrontFer = document.getElementById("canvasFrontFermi");
var ctxFrontFer = canvasFrontFer.getContext("2d");

var running = false;
var marginVert = 20,
    marginHor = 20,
    anchura = canvasBackBos.width,
    altura = canvasBackBos.height,
    paso = 100;
var f, N, beta, Niv, radio, occB, occF, hisB, hisF, levelshift, maxHistB, maxHistF, sumB, sumF;

resetSim();
simulate();

function simulate() {
    if (running) {
        /* START */
        console.log("START");
        var coord = montecarlo(Niv, occB, occF, hisB, hisF, sumB, sumF, maxHistB, maxHistF, beta, levelshift, radio, anchura, altura, marginVert);
        plotFront(coord, radio);
        plotHist(hisB, hisF)
    }
    window.setTimeout(simulate, 10);
}

// Funcion para empezar o pausar la simulacion
function startStop() {
    running = !running;
    if (running) {
        console.log("START");
        startButton.value = " Pause ";
        // reset();
    } else {
        startButton.value = "Resume";
    }
}

function resetSim() {
    f = 0;
    N = n.value;
    beta = 2 / temperatura.value;
    Niv = niveles.value;
    radio = 7;
    occB = []; // Ocupación de los niveles bosónicos. 0, 1, 2 ... partículas
    occF = [];
    hisB = [];
    hisF = [];
    levelshift = (canvasBackBos.height - 2 * marginVert) / Niv;
    plotBack(Niv, anchura, levelshift, marginHor, marginVert);
    // Inicializa
    maxHistB = 0.0;
    maxHistF = 0.0;
    sumB = 0.0;
    sumF = 0.0;
    for (var i = 0; i < Niv; i++) {
        if (i < N) {
            occF[i] = 1;
            occB[i] = 1;
        } else {
            occF[i] = 0;
            occB[i] = 0;
        }
        hisF[i] = 0;
        hisB[i] = 0;
    }
    plotHist(hisB, hisF);
}

// Funcion para dibujar los histogramas
function plotHist(hisB, hisF) {
    var freqB = [],
        freqF = [],
        levels = [],
        maxB = Math.max.apply(Math, hisB),
        maxF = Math.max.apply(Math, hisF);
    for (var i = 0; i < hisB.length; i++) {
        levels[i] = i + 1;
        freqB[i] = hisB[i] / maxB;
        freqF[i] = hisF[i] / maxF;
    }

    var traceB = {
        x: levels,
        y: freqB,
        type: "lines",
    };
    var traceF = {
        x: levels,
        y: freqF,
        type: "lines",
        line: {
            color: "orange",
        }
    };
    var dataB = [traceB],
        dataF = [traceF];

    var layoutB = {
        title: "Ocupación de bosones",
        xaxis: {
            title: "Nivel de energía"
        },
        yaxis: {
            title: "Frecuencia Bosones"
        },
        margin: {
            l: 40,
            r: 10,
            t: 40,
            b: 30,
        },
    };
    var layoutF = {
        title: "Ocupación de fermiones",
        xaxis: {
            title: "Nivel de energía"
        },
        yaxis: {
            title: "Frecuencia Fermiones"
        },
        margin: {
            l: 40,
            r: 10,
            t: 40,
            b: 30,
        },
    };
    var config = {
        staticPlot: true,
        responsive: true,
    };

    // Plotly.newPlot("graph", data, layout, config);
    Plotly.newPlot("graphBos", dataB, layoutB, config);
    Plotly.newPlot("graphFer", dataF, layoutF, config);
}

function plotBack(Niv, anchura, levelshift, marginHor, marginVert) {


    ctxBackBos.setTransform(1, 0, 0, 1, 0, 0);
    ctxBackBos.clearRect(0, 0, canvasBackBos.width, canvasBackBos.height);
    ctxBackBos.fillStyle = "white";
    ctxBackBos.fillRect(0, 0, canvasBackBos.width, canvasBackBos.height);

    ctxBackBos.font = "12px Times";
    ctxBackBos.fillStyle = "black";

    ctxBackFer.setTransform(1, 0, 0, 1, 0, 0);
    ctxBackFer.clearRect(0, 0, canvasBackFer.width, canvasBackFer.height);
    ctxBackFer.fillStyle = "white";
    ctxBackFer.fillRect(0, 0, canvasBackFer.width, canvasBackFer.height);
    ctxBackFer.font = "12px Times";
    ctxBackFer.fillStyle = "black";

    for (var i = 0; i < Niv; i++) {
        var step = levelshift * i + 2 * marginVert;
        var levelHeight = levelshift * (i - 1 / 3) + marginVert;
        var x1 = marginHor;
        var y1 = altura - step + radio;
        var x2 = anchura - 2 * marginHor;
        var y2 = altura - step + radio;
        ctxBackBos.beginPath(); // Bosones
        ctxBackBos.moveTo(x1, y1);
        ctxBackBos.lineTo(x2, y2);
        ctxBackBos.lineWidth = 3;
        ctxBackBos.strokeStyle = 'black';
        ctxBackBos.stroke();
        ctxBackBos.fillText(i, x2 + 10, y1 + levelshift / 4);
        ctxBackFer.beginPath(); // Fermiones
        ctxBackFer.moveTo(x1, y1);
        ctxBackFer.lineTo(x2, y2);
        ctxBackFer.lineWidth = 3;
        ctxBackFer.strokeStyle = 'black';
        ctxBackFer.stroke();
        ctxBackFer.fillText(i, x2 + 10, y1 + levelshift / 4);
    }
}

function plotFront(coord, radio) {
    ctxFrontBos.setTransform(1, 0, 0, 1, 0, 0);
    ctxFrontBos.clearRect(0, 0, canvasFrontBos.width, canvasFrontBos.height);
    ctxFrontFer.setTransform(1, 0, 0, 1, 0, 0);
    ctxFrontFer.clearRect(0, 0, canvasFrontFer.width, canvasFrontFer.height);

    for (var i = 0; i < coord[0].length; i++) {
        ctxFrontBos.beginPath();
        ctxFrontBos.arc(coord[0][i], coord[1][i], radio, 0, 2 * Math.PI, true);
        ctxFrontBos.fillStyle = 'blue';
        ctxFrontBos.fill();

        ctxFrontFer.beginPath();
        ctxFrontFer.arc(coord[2][i], coord[3][i], radio, 0, 2 * Math.PI, true);
        ctxFrontFer.fillStyle = 'orange';
        ctxFrontFer.fill();
    }
}

// Funciones de calculo
function inicializa(occF, occB, histF, hisB) {
    var maxHistB = 0.0;
    var maxHistF = 0.0;
    var sumB = 0.0;
    var sumF = 0.0;
    for (var i = 0; i < Niv; i++) {
        if (i < N) {
            occF[i] = 1;
            occB[i] = 1;
        } else {
            occF[i] = 0;
            occB[i] = 0;
        }
        hisF[i] = 0;
        hisB[i] = 0;
    };
}

function montecarlo(Niv, occB, occF, hisB, hisF, sumB, sumF, maxHistB, maxHistF, beta, levelshift, radio, anchura, altura, marginVert) {
    occB = Bose(Niv, occB, beta);
    occF = Fermi(Niv, occF, beta);

    var Xbos = [],
        Ybos = [],
        Xfer = [],
        Yfer = [];

    for (var i = 0; i < Niv; i++) {
        hisB[i] += occB[i];
        hisF[i] += occF[i];
        sumB += occB[i];
        sumF += occF[i];
        if (hisB[i] > maxHistB) {
            maxHistB = hisB[i];
        }
        if (hisF[i] > maxHistF) {
            maxHistF = hisF[i];
        }

        var step = levelshift * i + 2 * marginVert;

        if (occB[i] > 0) {
            var xpos = (anchura - 140) / (occB[i] + 1);
            if (xpos > 2 * radio + 2) {
                xpos = 2 * radio + 2;
            }
            for (var j = 1; j <= occB[i]; ++j) {
                Xbos.push(anchura / 2 + xpos * (j - 1) - radio - occB[i] * xpos / 2);
                Ybos.push(altura - step + radio);
            }
        }
        if (occF[i] != 0) {
            Xfer.push(anchura / 2 - 1.5 * radio);
            Yfer.push(altura - step + radio);
        }
    };
    return [Xbos, Ybos, Xfer, Yfer, hisB, hisF];
}


function Bose(Niv, occB, beta) {
    var n = Math.floor(Math.random() * Niv);
    for (var i = 0; i < Niv; i++) {
        if (occB[i] != 0) {
            var n2 = i + (2.0 * Math.floor(Math.random() * 2.0) - 1.0);
            if (n2 < 0) {
                n2 = 0;
            } else if (n2 > Niv) {
                n2 = Niv - 1;
            }
            var exp = Math.exp(-beta * (n2 - i));
            var rand = Math.random();
            if (exp >= 1.0) {
                occB[n2]++;
                occB[i]--;
            } else if (rand < exp) {
                occB[n2]++;
                occB[i]--;
            }
        }
    }
    return occB;
}

function Fermi(Niv, occF, beta) {
    var n = Math.floor(Math.random() * Niv);
    for (var i = 0; i < Niv; i++) {
        if (occF[i] != 0) { // Miramos los niveles ocupados
            var j = i + (2.0 * Math.floor(Math.random() * 2.0) - 1.0);
            if (j < 0) {
                j = 0;
            }
            if (j >= Niv - 1) {
                j = Niv - 2;
            }
            if (occF[j] != 1) {
                var exp = Math.exp(-beta * (j - i));
                var rand = Math.random()
                if (exp >= 1.0) {
                    occF[j]++;
                    occF[i]--;
                } else if (rand < exp) {
                    occF[j]++;
                    occF[i]--;
                }
            }
        }
    }
    return occF;
}