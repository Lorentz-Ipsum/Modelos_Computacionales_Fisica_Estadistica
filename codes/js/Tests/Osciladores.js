// Variables de los sliders
var nSlider = document.getElementById('numeroSlider');
var nLectura = document.getElementById('numeroLectura');
var mSlider = document.getElementById('sectorSlider');
var mLectura = document.getElementById('sectorLectura');
var vSlider = document.getElementById('velocidadSlider');
var vLectura = document.getElementById('velocidadLectura');
var freqSelect = document.getElementById('freqSelect');
var faseSelect = document.getElementById('faseSelect');
var n = nSlider.value,
    m = mSlider.value,
    vel = velocidadSlider.value;
var freq = faseSelect.value;
var fase = faseSelect.value;

// Variables del canvas
var canvasSec = document.getElementById("canvasSec");
var ctxSec = canvasSec.getContext("2d");
var canvasOsc = document.getElementById("canvasOsc");
var ctxOsc = canvasOsc.getContext("2d");
var freqSelect = document.querySelector('#freqSelect');
var faseSelect = document.querySelector('#faseSelect');
var speedSelect = document.querySelector('#speedSelect');
var w = canvasSec.width;

// Variables de la simulacion
var frames = [],
    dif = [];
var f = 0;
frames.push(f);
var barras = 100;
var running = false;
var osc = generaOsc(n, freq, fase);
var freqArr = osc[0],
    faseArr = osc[1],
    arrS = [],
    histo = [];

// Inicializar variables
for (var i = 0; i < barras + 1; i++) {
    histo[i] = 0;
}
var maxHist = 0;
arrS.push(getEntropy(m, faseArr));
histo[Math.floor(arrS[0] * barras)]++;

// Empieza la
updateNum();
updateSec();
updateVel();
drawSec(m);
plotNormEntropy(f, arrS);
simulate();



/////////////////////////////
// FUNCIONES DE SIMULACION //
/////////////////////////////

// Funcion del bucle principal
function simulate() {
    if (running) {
        drawOsc(faseArr);
        plotNormEntropy(f, arrS);
        faseArr = stepOsc(freqArr, faseArr);
        arrS.push(getEntropy(m, faseArr));
        histo[Math.floor(arrS[f] * barras)]++; // Suma 1 en histo, en el valor de entropia correspondiente
        f++;
    }
    window.setTimeout(simulate, vel); // Ajuste de velocidad
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
    frames = [];
    dif = [];
    f = 0;
    frames.push(f);
    barras = 100;
    osc = generaOsc(n, freq, fase);
    freqArr = osc[0];
    faseArr = osc[1];
    arrS = [];
    histo = [];
    for (var i = 0; i < barras; i++) {
        histo[i] = 0;
    }
    maxHist = 0;
    arrS.push(getEntropy(m, faseArr));
    histo[Math.floor(arrS[0] * barras)]++;
    drawSec(m);
    drawOsc(faseArr);
    plotNormEntropy(f, arrS);
}









//////////////////////////
// FUNCIONES DE CALCULO //
//////////////////////////

// Funcion que genera el array de osciladores
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

// Funcion que evoluciona el estado de los osciladores
function stepOsc(freqArr, faseArr) {
    for (var i = 0; i < freqArr.length; i++) {
        faseArr[i] += freqArr[i] / 4;
    }
    return faseArr;
}

// Funcion que calcula la entropia de los osciladores
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









///////////////////////
// FUNCIONES DE PLOT //
///////////////////////

// Funcion para dibujar el circulo y los sectores
function drawSec(m) {
    ctxSec.setTransform(1, 0, 0, 1, 0, 0);
    ctxSec.clearRect(0, 0, w, w);
    ctxSec.fillStyle = "white";
    ctxSec.fillRect(0, 0, w, w);
    ctxSec.translate(w / 2, w / 2);
    ctxSec.beginPath();
    ctxSec.arc(0, 0, w / 2 - 30, 0, 2 * Math.PI, true);
    ctxSec.lineWidth = 5;
    ctxSec.strokeStyle = 'black';
    ctxSec.stroke();
    var r1 = w / 2 - 20;
    var r2 = w / 2 - 60;
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

// Funcion para dibujar el estado instantaneo de los osciladores
function drawOsc(faseArr) {
    ctxOsc.setTransform(1, 0, 0, 1, 0, 0);
    ctxOsc.clearRect(0, 0, w, w);
    ctxOsc.translate(w / 2, w / 2);
    var r1 = w / 20;
    var r2 = 3 * w / 8;
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

// Funcion para dibujar la entropia normalizada
function plotNormEntropy(frames, arrS) {
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
        title: 'Entropía normalizada',
        xaxis: {
            title: 't',
        },
        yaxis: {
            title: 'S',
            range: [0, 1],
        },
        font: {
            size: 18,
        },
        margin: {
            u: 30,
            l: 60,
            r: 30,
            d: 20,
        },
        showlegend: false,
        plot_bgcolor: 'rgb(223, 223, 223)'
    };
    var config = {
        staticPlot: true,
        responsive: true,
    }
    Plotly.newPlot('graph', data, layout, config);
}

// Funcion para dibujar el histograma
function plotHist() {
    var threshold = 0;
    for (var i = 0; i < barras; i++) {
        if (histo[i] > 1 && threshold == 0) {
            threshold = i;
        }
    }
    var S = [],
        Ns = [];
    for (var i = threshold; i < barras; i++) {
        S.push(i / barras);
        Ns.push(histo[i] / maxHist);
        // NsForm.push(Math.exp(S));
    }
    var numsTrace = {
        x: S,
        y: Ns,
        mode: 'markers',
        name: 'Diferencia',
    };
    var solTrace = {
        x: arrS,
        type: 'histogram',
    };
    var data = [solTrace];
    var layout = {
        width: 450,
        title: 'Logaritmo',
        font: {
            size: 15
        },
        xaxis: {
            title: 'S',
        },
        yaxis: {
            title: 'N(s)',
        },
        plot_bgcolor: 'rgb(223, 223, 223)'
    };
    var config = {
        staticPlot: true,
        responsive: true,
    }
    Plotly.newPlot('graphHist', data, layout, config);
}






///////////////////////////
// FUNCIONES DE INTERFAZ //
///////////////////////////

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
    vel = (1 / Math.pow(vSlider.value, 5)) * 1000;
}

// Funcion para actualizar el tipo de distribucion de frecuencias
function updateFreq() {
    freq = freqSelect.value;
}

// Funcion para actualizar el tipo de distribucion de fases
function updateFase() {
    fase = faseSelect.value;
}