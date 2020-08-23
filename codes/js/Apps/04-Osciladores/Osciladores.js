var canvasSec = document.getElementById("canvasSec");
var ctxSec = canvasSec.getContext("2d");
var canvasOsc = document.getElementById("canvasOsc");
var ctxOsc = canvasOsc.getContext("2d");
var freqSelect = document.querySelector('#freqSelect');
var faseSelect = document.querySelector('#faseSelect');
var speedSelect = document.querySelector('#speedSelect');

var interval;


// Contadores para los slider
$(document).on('input', 'input[type="range"]', function(e) {
    var changes = this.id + 'Out';
    document.getElementById(changes).innerHTML = e.target.value;
});


// CODIGO PRINCIPAL
$(document).ready(function() {

    $('input[type=checkbox][name=test]').change(function(e) {
        if (this.checked) {
            console.log("START");
            var f = 0;
            var frames = [],
                dif = [];
            frames.push(f);

            // Inicializar los valores de los sliders
            var freq = freqSelect.value,
                fase = faseSelect.value;
            var n = numero.value,
                m = sectores.value,
                vel;

            switch (Number(speedSelect.value)) {
                case 0:
                    vel = 1000;
                    break;
                case 1:
                    vel = 500;
                    break;
                case 2:
                    vel = 100;
                    break;
                case 3:
                    vel = 10;
                    break;
            }

            drawSec(m);
            var osc = generaOsc(n, freq, fase);
            var freqArr = osc[0],
                faseArr = osc[1],
                arrS = [];

            arrS.push(getEntropy(m, faseArr));

            interval = setInterval(function() {
                // LOOP DE PLOT
                ///////////////
                drawOsc(faseArr);
                plotNormEntropy(f, arrS);

                faseArr = stepOsc(freqArr, faseArr);
                arrS.push(getEntropy(m, faseArr));

                // Repite
                console.log("Fin de iteración " + f++);
            }, vel);
        } else {
            console.log("STOP");
            clearInterval(interval);
        }
    });
}); // Fin de document.ready



/////////////////////////////
// DEFINICION DE FUNCIONES //
/////////////////////////////


// FUNCIONES DE CALCULO
///////////////////////

function generaOsc(n, freq, fase) {
    var freqArr = [],
        faseArr = [];
    // Genera las frecuencias
    switch (Number(freq)) {
        case 0:
            for (var i = 0; i < n; i++) {
                var j = 2 * Math.PI * Math.random();
                freqArr.push(j);
            }
            break;
        case 1:
            for (var i = 0; i < n; i++) {
                var j = 0.5 * Math.random();
                freqArr.push(j);
            }
            break;
        case 2:
            for (var i = 0; i < n; i++) {
                var j = i * 2 * Math.PI / n;
                freqArr.push(j);
            }
            break;
    };
    // Genera las fases
    switch (Number(fase)) {
        case 0:
            for (var i = 0; i < n; i++) {
                var j = 2 * Math.PI * Math.random();
                faseArr.push(j);
            }
            break;
        case 1:
            for (var i = 0; i < n; i++) {
                faseArr.push(0);
            }
            break;
    };
    return [freqArr, faseArr];
}


function stepOsc(freqArr, faseArr) {
    for (var i = 0; i < freqArr.length; i++) {
        faseArr[i] += freqArr[i] / 4;
    }
    return faseArr;
}


function getEntropy(m, faseArr) {
    var Smax = Math.log(m),
        n = faseArr.length;
    var S = 0;
    var alpha = [];
    var theta0, theta1, faseAbsoluta;

    for (var i = 0; i < m; i++) {
        alpha.push(0);
        theta0 = ((2 * Math.PI) / m) * i;
        theta1 = ((2 * Math.PI) / m) * (i + 1);
        for (var j = 0; j < faseArr.length; j++) {
            faseAbsoluta = faseArr[j] % (2 * Math.PI);
            if (faseAbsoluta >= theta0 && faseAbsoluta < theta1) {
                alpha[i] += 1;
            }
        }
        if (alpha[i] != 0) {
            S += alpha[i] / n * Math.log(alpha[i] / n);
        }
    }
    S = -S / Smax;
    return S;
}


// FUNCIONES DE PLOT
////////////////////

function drawOsc(faseArr) {
    ctxOsc.setTransform(1, 0, 0, 1, 0, 0);
    ctxOsc.clearRect(0, 0, canvasOsc.width, canvasOsc.height);
    ctxOsc.translate(canvasOsc.width / 2, canvasOsc.height / 2);

    var r1 = 10;
    var r2 = 170;

    // Dibuja las bolitas
    for (var i = 0; i < faseArr.length; i++) {
        var theta = faseArr[i];

        var x1 = r1 * Math.cos(theta);
        var y1 = r1 * Math.sin(theta);
        var x2 = r2 * Math.cos(theta);
        var y2 = r2 * Math.sin(theta);

        ctxOsc.beginPath();
        ctxOsc.moveTo(x1, y1);
        ctxOsc.lineTo(x2, y2);
        ctxOsc.lineWidth = 3;
        ctxOsc.strokeStyle = 'red';
        ctxOsc.stroke();
    }
}

function drawSec(m) {
    ctxSec.setTransform(1, 0, 0, 1, 0, 0);
    ctxSec.clearRect(0, 0, canvasSec.width, canvasSec.height);
    ctxSec.fillStyle = "white";
    ctxSec.fillRect(0, 0, canvasOsc.width, canvasOsc.height);
    ctxSec.translate(canvasSec.width / 2, canvasSec.height / 2);

    // Dibuja el circulo
    ctxSec.beginPath();
    ctxSec.arc(0, 0, 200, 0, 2 * Math.PI, true);
    ctxSec.lineWidth = 3;
    ctxSec.strokeStyle = 'black';
    ctxSec.stroke();

    var r1 = 150;
    var r2 = 250;

    // Dibuja los sectores
    for (var i = 0; i < m; i++) {
        var theta = ((2 * Math.PI) / m) * i;

        var x1 = r1 * Math.cos(theta);
        var y1 = r1 * Math.sin(theta);
        var x2 = r2 * Math.cos(theta);
        var y2 = r2 * Math.sin(theta);

        ctxSec.beginPath();
        ctxSec.moveTo(x1, y1);
        ctxSec.lineTo(x2, y2);
        ctxSec.lineWidth = 5;
        ctxSec.strokeStyle = 'green';
        ctxSec.stroke();
    }
}

function plotNormEntropy(frames, arrS) {
    // Trazas
    var numsTrace = {
        x: frames,
        y: arrS,
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
        width: 500,
        height: 300,
        title: 'Entropía normalizada',
        font: {
            size: 15
        },
        plot_bgcolor: 'rgb(223, 223, 223)'
    };

    var config = {
        staticPlot: true
    }

    Plotly.newPlot('graph', data, layout, config);
}