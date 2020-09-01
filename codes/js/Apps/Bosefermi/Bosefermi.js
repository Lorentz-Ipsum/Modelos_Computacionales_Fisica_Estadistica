var canvasBackBos = document.getElementById("canvasBackBose");
var ctxBackBos = canvasBackBos.getContext("2d");
var canvasFrontBos = document.getElementById("canvasFrontBose");
var ctxFrontBos = canvasFrontBos.getContext("2d");

var canvasBackFer = document.getElementById("canvasBackFermi");
var ctxBackFer = canvasBackFer.getContext("2d");
var canvasFrontFer = document.getElementById("canvasFrontFermi");
var ctxFrontFer = canvasFrontFer.getContext("2d");
// var freqSelect = document.querySelector('#freqSelect');
// var faseSelect = document.querySelector('#faseSelect');
// var speedSelect = document.querySelector('#speedSelect');

var interval;

// CODIGO PRINCIPAL
$(document).ready(function() {
    // Boton Start / Stop
    var vel = 10;
    $('input[type=checkbox][name=test]').change(function(e) {
        if (this.checked) {
            /* START */
            console.log("START");
            var f = 0;

            // Variables de los sliders
            var N = n.value,
                beta = 2 / temperatura.value,
                Niv = niveles.value;

            var corriendo = false;
            var activo = true;
            var kT = 0.5;
            var radio = 7;
            var velocidad = 1;
            // var Niv = 20;
            var occB = []; // Ocupación de los niveles bosónicos. 0, 1, 2 ... partículas
            var occF = [];
            var hisB = [];
            var hisF = [];


            var marginVert = 30,
                marginHor = 20;
            var anchura = canvasBackBos.width;
            var altura = canvasBackBos.height;
            var levelshift = (canvasBackBos.height - 2 * marginVert) / Niv;

            plotBack(Niv, anchura, levelshift, marginHor, marginVert);

            // Inicializa
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
            }

            var sumB = 0.0;
            var sumF = 0.0;
            var paso = 100;

            // LOOP DE PLOT
            ///////////////
            interval = setInterval(function() {

                // Botón del histograma. Cuando se pulsa debe pausarse la simulación
                var coord = montecarlo(Niv, occB, occF, hisB, hisF, sumB, sumF, maxHistB, maxHistF, beta, levelshift, radio, anchura, altura, marginVert);
                plotFront(coord, radio);
                histBosBtn.onclick = function() {
                    plotHistBos(hisB);
                };
                histFerBtn.onclick = function() {
                    plotHistFer(hisF);
                };

                // Repite
                console.log("Fin de iteración " + f++);
            }, vel);
        } else {
            console.log("STOP");
            clearInterval(interval);
        };

    });


    // Funciones de plot
    function plotHistBos(hisB) {
        var freq = [],
            levels = [],
            max = Math.max.apply(Math, hisB);
        for (var i = 0; i < hisB.length; i++) {
            levels[i] = i + 1;
            freq[i] = hisB[i] / max;
        }
        var trace = {
            x: levels,
            y: freq,
            type: "markers",
        };
        var data = [trace];

        var layout = {
            title: "Histograma"
        };

        Plotly.newPlot("graphBos", data, layout);
    }

    function plotHistFer(hisF) {
        var freq = [],
            levels = [],
            max = Math.max.apply(Math, hisF);
        for (var i = 0; i < hisF.length; i++) {
            levels[i] = i + 1;
            freq[i] = hisF[i] / max;
        }
        var trace = {
            x: levels,
            y: hisF,
            type: "markers",
            histnorm: 'probability',
        };
        var data = [trace];

        var layout = {
            title: "histograma"
        };

        Plotly.newPlot("graphFer", data, layout);
    }

    function plotBack(Niv, anchura, levelshift, marginHor, marginVert) {

        ctxBackBos.setTransform(1, 0, 0, 1, 0, 0);
        ctxBackBos.clearRect(0, 0, canvasBackBos.width, canvasBackBos.height);
        ctxBackBos.fillStyle = "white";
        ctxBackBos.fillRect(0, 0, canvasBackBos.width, canvasBackBos.height);

        ctxBackBos.font = "14px Times";
        ctxBackBos.fillStyle = "black";

        ctxBackFer.setTransform(1, 0, 0, 1, 0, 0);
        ctxBackFer.clearRect(0, 0, canvasBackFer.width, canvasBackFer.height);
        ctxBackFer.fillStyle = "white";
        ctxBackFer.fillRect(0, 0, canvasBackFer.width, canvasBackFer.height);

        for (var i = 0; i < Niv; i++) {

            var levelHeight = levelshift * i + marginVert;

            // bosones.Recta(70.0, n3, (anchura - 70), n3);
            // fermiones.Recta(80.0, n3, (anchura - 80), n3);

            var x1b = marginHor;
            var y1b = levelHeight;
            var x2b = anchura - 2 * marginHor;
            var y2b = levelHeight;

            var x1f = marginHor;
            var y1f = levelHeight;
            var x2f = anchura - 2 * marginHor;
            var y2f = levelHeight;

            ctxBackBos.beginPath();
            ctxBackBos.moveTo(x1b, y1b);
            ctxBackBos.lineTo(x2b, y2b);
            ctxBackBos.lineWidth = 3;
            ctxBackBos.strokeStyle = 'black';
            ctxBackBos.stroke();
            ctxBackBos.fillText(Niv - i, x1b + 5, y1b + levelshift / 2);

            ctxBackFer.beginPath();
            ctxBackFer.moveTo(x1f, y1f);
            ctxBackFer.lineTo(x2f, y2f);
            ctxBackFer.lineWidth = 3;
            ctxBackFer.strokeStyle = 'black';
            ctxBackFer.stroke();
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
                    // bosones.Imagen(bolaB, (anchura / 2 + n4 * (j - 1) - radio - occB[i] * n4 / 2), (step + 2 * radio));
                }
            }
            if (occF[i] != 0) {
                Xfer.push(anchura / 2 - 1.5 * radio);
                Yfer.push(altura - step + radio);
                // fermiones.Imagen(bolaF, anchura / 2 - 1.5 * radio, (step + 2 * radio));
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
})