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
    var vel = 100;
    $('input[type=checkbox][name=test]').change(function(e) {
        if (this.checked) {
            /* START */
            console.log("START");
            var f = 0;

            var N = n.value;
            var Nmax = 20;
            var corriendo = false;
            var activo = true;
            var kT = 0.5;
            var radio = 4;
            var velocidad = 1;
            var Niv = 20;
            var occB = [];
            var occF = [];
            var hisB = [];
            var hisF = [];

            var anchura = (canvasBackBos.width - 20) / 2;
            var altura = (canvasBackBos.height - 80);
            var levelshift = (altura - 20) / Niv;

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

            var beta = 0.1;
            var sumB = 0.0;
            var sumF = 0.0;
            var paso = 100;
            // Variables de los sliders
            var N = n.value,
                beta = 20 / temperatura.value;

            plotBack(Niv, levelshift, anchura);


            // LOOP DE PLOT
            ///////////////
            interval = setInterval(function() {

                // Botón del histograma. Cuando se pulsa debe pausarse la simulación
                coord = montecarlo(Niv, occB, occF, hisB, hisF, sumB, sumF, maxHistB, maxHistF, beta, levelshift, radio, anchura);
                plotFront(coord, radio);

                // Repite
                console.log("Fin de iteración " + f++);
            }, vel);
        } else {
            console.log("STOP");
            clearInterval(interval);
        };

    });


    // Funciones de plot
    function plotHistBose(nums, dens) {
        var trace = {
            x: nums,
            y: dens,
            type: "lines",
        };
        var data = [trace];

        var layout = {
            title: "Distribution Graph"
        };

        Plotly.newPlot("myDiv", data, layout);
    }

    function plotHistFermi(nums, dens) {
        var trace = {
            x: nums,
            y: dens,
            type: "lines",
        };
        var data = [trace];

        var layout = {
            title: "Distribution Graph"
        };

        Plotly.newPlot("myDiv", data, layout);
    }

    function plotBack(Niv, levelshift, anchura) {
        ctxBackBos.setTransform(1, 0, 0, 1, 0, 0);
        ctxBackBos.clearRect(0, 0, canvasBackBos.width, canvasBackBos.height);
        ctxBackBos.fillStyle = "white";
        ctxBackBos.fillRect(0, 0, canvasBackBos.width, canvasBackBos.height);

        ctxBackFer.setTransform(1, 0, 0, 1, 0, 0);
        ctxBackFer.clearRect(0, 0, canvasBackFer.width, canvasBackFer.height);
        ctxBackFer.fillStyle = "white";
        ctxBackFer.fillRect(0, 0, canvasBackFer.width, canvasBackFer.height);

        for (var i = 0; i < Niv; i++) {

            var n3 = levelshift * i + 20;

            // bosones.Recta(70.0, n3, (anchura - 70), n3);
            // fermiones.Recta(80.0, n3, (anchura - 80), n3);

            var bx1 = 10;
            var by1 = n3;
            var bx2 = (2 * anchura - 10);
            var by2 = n3;
            var fx1 = 10;
            var fy1 = n3;
            var fx2 = (2 * anchura - 10);
            var fy2 = n3;

            ctxBackBos.beginPath();
            ctxBackBos.moveTo(bx1, by1);
            ctxBackBos.lineTo(bx2, by2);
            ctxBackBos.lineWidth = 5;
            ctxBackBos.strokeStyle = 'black';
            ctxBackBos.stroke();

            ctxBackFer.beginPath();
            ctxBackFer.moveTo(fx1, fy1);
            ctxBackFer.lineTo(fx2, fy2);
            ctxBackFer.lineWidth = 5;
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

    function montecarlo(Niv, occB, occF, hisB, hisF, sumB, sumF, maxHistB, maxHistF, beta, levelshift, radio, anchura) {
        occB = Bose(Niv, occB, beta);
        occF = Fermi(Niv, occF, beta);

        var Xbos = [],
            Ybos = [],
            Xfer = [],
            Yfer = [];

        for (var i = 0; i < Niv; i++) {
            var n = i;
            hisB[n] += occB[i];
            sumB += occB[i];
            hisF[i] += occF[i];
            sumF += occF[i];
            if (hisB[i] > maxHistB) {
                maxHistB = hisB[i];
            }
            if (hisF[i] > maxHistF) {
                maxHistF = hisF[i];
            }
            var step = levelshift * i + 20;

            if (occB[i] > 0) {
                n4 = (anchura - 140) / (occB[i] + 1);
                if (n4 > 2 * radio + 2) {
                    n4 = 2 * radio + 2;
                }
                for (var j = 1; j <= occB[i]; ++j) {
                    Xbos.push(anchura / 2 + n4 * (j - 1) - radio - occB[i] * n4 / 2);
                    Ybos.push(step + 2 * radio);
                    // bosones.Imagen(bolaB, (anchura / 2 + n4 * (j - 1) - radio - occB[i] * n4 / 2), (step + 2 * radio));
                }
            }
            if (occF[i] != 0) {
                Xfer.push(anchura / 2 - 1.5 * radio);
                Yfer.push(step + 2 * radio);
                // fermiones.Imagen(bolaF, anchura / 2 - 1.5 * radio, (step + 2 * radio));
            }
        };
        return [Xbos, Ybos, Xfer, Yfer];
    }


    function Bose(Niv, occB, beta) {
        var n = Math.floor(Math.random() * Niv);
        for (var i = 0; i <= Niv; i++) {
            if (occB[i] != 0) {
                var n2 = i + (2.0 * Math.floor(Math.random() * 2.0) - 1.0);
                if (n2 < 0) {
                    n2 = 0;
                } else if (n2 >= Niv) {
                    n2 = Niv - 1;
                }
                var exp = Math.exp(-beta * (n2 - i));
                if (exp >= 1.0) {
                    occB[n2]++;
                    occB[i]--;
                } else if (Math.random() < exp) {
                    occB[n2]++;
                    occB[i]--;
                }
            }
        }
        return occB;
    }

    function Fermi(Niv, occF, beta) {
        var n = Math.floor(Math.random() * Niv);
        for (var i = 0; i <= Niv; i++) {
            if (occF[i] != 0) {
                var n2 = i + (2.0 * Math.floor(Math.random() * 2.0) - 1.0);
                if (n2 < 0) {
                    n2 = 0;
                }
                if (n2 >= Niv) {
                    n2 = Niv - 1;
                }
                if (occF[n2] != 1) {
                    var exp = Math.exp(-beta * (n2 - i));
                    if (exp >= 1.0) {
                        occF[n2]++;
                        occF[i]--;
                    } else if (Math.random() < exp) {
                        occF[n2]++;
                        occF[i]--;
                    }
                }
            }
        }
        return occF;
    }
})