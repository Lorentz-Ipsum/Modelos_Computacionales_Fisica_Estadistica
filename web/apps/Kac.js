// Variables de los sliders
var nSlider = document.getElementById('numeroSlider');
var nLectura = document.getElementById('numeroLectura');
var mSlider = document.getElementById('sectorSlider');
var mLectura = document.getElementById('sectorLectura');
var vSlider = document.getElementById('velocidadSlider');
var vLectura = document.getElementById('velocidadLectura');
var selectElement = document.getElementById('stateSelect');
var state = selectElement.value;
var n = nSlider.value,
    m = mSlider.value,
    vel = 1000;

// Variables del canvas
var canvas = document.getElementById("anilloExt");
var canvasInt = document.getElementById("anilloInt");
var ctx = canvas.getContext("2d");
var ctxInt = canvasInt.getContext("2d");
var w = canvas.width,
    h = canvas.height;

// Preparar el canvas
ctx.setTransform(1, 0, 0, 1, 0, 0);
ctx.clearRect(0, 0, w, h);
ctx.fillStyle = "white";
ctx.fillRect(0, 0, w, h);
ctx.translate(w / 2, h / 2);

var running = false; // will be true when running
var i = 0,
    frames = [],
    dif = [],
    ring = [],
    S = [],
    X0 = [],
    X = [];

var numsTrace = [],
    data = [],
    layout = [],
    config = [];

resetPlot();
simulate();

/////////////////////////////
// DEFINICION DE FUNCIONES //
/////////////////////////////

// Funcion para realizar la simulacion
function simulate() {
    if (running) {
        frames.push(i);
        drawInt(X);
        dif.push(getDif(X));
        // plotNumberDif(frames, dif);

        numsTrace = {
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
        data = [numsTrace];
        Plotly.newPlot(graph, data, layout, config);

        X = anilloStep(X, S);

        // Repite
        console.log("Fin de iteración " + i++);
    }
    window.setTimeout(simulate, vel); //Aqui iria el ajuste de velocidad
}

// FUNCIONES DE CALCULO
///////////////////////

// Funcion para desordenar un array de numeros aleatorios
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

// Funcion para obtener la diferencia de numero de bolas rojas y azules
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

// Funcion para generar los array de los sectores y el anillo
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

// Funcion para calcular las bolas que cambian de color
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

// FUNCIONES DE PLOT
////////////////////

function drawExt(S, X) {
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, w, h);
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, w, h);
    ctx.translate(w / 2, h / 2);
    var n = X.length;

    // Dibuja los sectores
    for (var i = 0; i < S.length; i++) {
        var r1 = w / 3 + 40;
        var r2 = w / 3 - 30;
        var theta = ((2 * Math.PI) / n) * (S[i]) + (Math.PI) / (n); // El pi / n que suma es para desplazar las divisiones y que aparezcan entre bolitas

        var x1 = r1 * Math.cos(theta);
        var y1 = r1 * Math.sin(theta);
        var x2 = r2 * Math.cos(theta);
        var y2 = r2 * Math.sin(theta);

        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.lineWidth = 7;
        ctx.strokeStyle = 'green';
        ctx.stroke();
    }

    ctx.beginPath();
    ctx.arc(0, 0, w / 3, 0, 2 * Math.PI, true);
    ctx.lineWidth = 3;
    ctx.strokeStyle = 'black';
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(w / 3 - 10, -10);
    ctx.lineTo(w / 3, 0);
    ctx.lineTo(w / 3 + 10, -10);
    ctx.lineWidth = 5;
    ctx.stroke();
    // Dibuja las bolitas
    for (var i = 0; i < X.length; i++) {
        var r = w / 3 + 20;
        var theta = ((2 * Math.PI) / n) * (i + 1);

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
        var r = w / 3 - 20;
        var theta = ((2 * Math.PI) / n) * (i + 1);

        var x = r * Math.cos(theta);
        var y = r * Math.sin(theta);

        ctxInt.beginPath();
        ctxInt.arc(x, y, 8, 0, 2 * Math.PI, true);
        if (X[i]) {
            ctxInt.fillStyle = 'blue';
        } else {
            ctxInt.fillStyle = 'red';
        }
        ctxInt.fill();
    }
}

// Funcion para empezar o pausar la simulacion
function startStop() {
    running = !running;
    if (running) {
        console.log("START");
        i = 0;
        startButton.value = " Pause ";
        // reset();
    } else {
        startButton.value = "Resume";
    }
}

// Funcion para restaurar la grafica
function resetPlot() {
    running = false;
    startButton.value = " Start ";
    emptyPlot();
    ring = generaAnillo(n, m, state);
    S = ring[0];
    X0 = ring[1];
    drawExt(S, X0);
    drawInt(X0);
    X = X0;
    // Generar el anillo externo y las divisiones, dibujarlos
}

// Funcion para actualizar la lectura de N
function updateNum() {
    nLectura.textContent = Number(nSlider.value);
    n = nSlider.value;
}

// Funcion para actualizar la lectura de M
function updateSec() {
    mLectura.textContent = Number(mSlider.value);
    m = mSlider.value;
}

// Funcion para actualizar la lectura de la velocidad
function updateVel() {
    vLectura.textContent = Number(vSlider.value);
    vel = (1 / Math.pow(vSlider.value, 6)) * 1000; // Para que el slider de la velocidad se comporte como debe
}

// Funcion para actualizar el tipo de distribucion
function updateState() {
    state = selectElement.value;
}

function emptyPlot() {
    frames = [];
    dif = [];
    numsTrace = {
        x: frames,
        y: dif,
        mode: 'lines',
        name: 'Diferencia',
        line: {
            color: 'rgb(0,0,255)',
            width: 1,
            opacity: 0.7
        },
    };
    data = [numsTrace];
    layout = {
        title: 'Diferencia de números',
        font: {
            size: 18,
        },
        margin: {
            l: 60,
            r: 30,
            d: 20,
        },
        showlegend: false,
        plot_bgcolor: 'rgb(223, 223, 223)'
    };
    config = {
        staticPlot: true,
        responsive: true,
    };
    Plotly.react("graph", data, layout, config);
}