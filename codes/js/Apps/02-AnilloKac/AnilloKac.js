var canvas = document.getElementById("anilloExt");
var canvasInt = document.getElementById("anilloInt");
var ctx = canvas.getContext("2d");
var ctxInt = canvasInt.getContext("2d");
var stateSelect = document.querySelector('#stateSelect');
var speedSelect = document.querySelector('#speedSelect');


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
            var tipo = stateSelect.value;
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
            }

            // Generar el anillo externo y las divisiones, dibujarlos
            var ring = generaAnillo(n, m, tipo);
            var S = ring[0],
                X0 = ring[1];

            drawExt(S, X0);

            var X = X0;

            interval = setInterval(function() {
                // LOOP DE PLOT
                ///////////////
                drawInt(X);

                frames.push(f);
                dif.push(getDif(X));
                plotNumberDif(frames, dif);

                X = anilloStep(X, S);

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
function shuffle(array) {
    var i = array.length,
        j = 0,
        temp;

    while (i--) {

        j = Math.floor(Math.random() * (i + 1));

        // swap randomly chosen element with current element
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;

    }

    return array;
}

function generaAnillo(n, m, tipo) {
    var X = [],
        S = [],
        nums = [],
        blue = [],
        red = [];

    // Genera los sectores
    for (var i = 0; i < n; i++) {
        S.push(i);
    }

    shuffle(S);
    S.length = m;

    // Genera el anillo
    switch (Number(tipo)) {
        case 0:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.5);
                X.push(j);
            }
            break;
        case 1:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.3);
                X.push(j);
            }
            break;
        case 2:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.7);
                X.push(j);
            }
            break;
        case 3:
            var j = true;
            for (var i = 0; i < n; i++) {
                X.push(j);
            }
            break;
        case 4:
            for (var i = 0; i < n; i++) {
                var j = (i % 2 == 0);
                X.push(j);
            }
            break;
    };
    return [S, X];
}


function anilloStep(X, S) {
    var X_s = [],
        L = X.length;
    for (var i = 0; i < L; i++) {
        if (i == 0) {
            if (S.includes(i, 0)) {
                X_s.push(!X[L - 1]);
            } else {
                X_s.push(X[L - 1]);
            }
        } else {
            if (S.includes(i, 0)) {
                X_s.push(!X[i - 1]);
            } else {
                X_s.push(X[i - 1]);
            }
        }
    }
    return X_s;
}


function getDif(X) {
    var blue = 0,
        red = 0;
    for (var i = 0; i < X.length; i++) {
        if (X[i]) {
            blue += 1;
        } else {
            red += 1;
        }
    }
    return blue - red;
}



// FUNCIONES DE PLOT
////////////////////

function drawExt(S, X) {
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.translate(canvas.width / 2, canvas.height / 2);
    var n = X.length;

    // Dibuja los sectores
    for (var i = 0; i < S.length; i++) {
        var r1 = 150;
        var r2 = 250;
        var theta = ((2 * Math.PI) / n) * (S[i]) + (Math.PI) / (n); // El pi / n que suma es para desplazar las divisiones y que aparezcan entre bolitas

        var x1 = r1 * Math.cos(theta);
        var y1 = r1 * Math.sin(theta);
        var x2 = r2 * Math.cos(theta);
        var y2 = r2 * Math.sin(theta);

        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.lineWidth = 10;
        ctx.strokeStyle = 'green';
        ctx.stroke();
    }

    // Dibuja las bolitas
    for (var i = 0; i < X.length; i++) {
        var r = 230;
        var theta = ((2 * Math.PI) / n) * i;

        var x = r * Math.cos(theta);
        var y = r * Math.sin(theta);

        ctx.beginPath();
        ctx.arc(x, y, 10, 0, 2 * Math.PI, true);
        if (X[i]) {
            ctx.fillStyle = 'blue';
        } else {
            ctx.fillStyle = 'red';
        }
        ctx.fill();
    }
}

function drawInt(X) {
    ctxInt.setTransform(1, 0, 0, 1, 0, 0);
    ctxInt.clearRect(0, 0, canvasInt.width, canvasInt.height);
    ctxInt.translate(canvasInt.width / 2, canvasInt.height / 2);
    var n = X.length;

    // Dibuja las bolitas
    for (var i = 0; i < X.length; i++) {
        var r = 180;
        var theta = ((2 * Math.PI) / n) * i;

        var x = r * Math.cos(theta);
        var y = r * Math.sin(theta);

        ctxInt.beginPath();
        ctxInt.arc(x, y, 10, 0, 2 * Math.PI, true);
        if (X[i]) {
            ctxInt.fillStyle = 'blue';
        } else {
            ctxInt.fillStyle = 'red';
        }
        ctxInt.fill();
    }
}

function plotNumberDif(frames, dif) {
    // Trazas
    var numsTrace = {
        x: frames,
        y: dif,
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
        title: 'Diferencia de números',
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