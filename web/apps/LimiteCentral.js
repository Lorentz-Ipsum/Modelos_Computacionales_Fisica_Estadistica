// Variables de los sliders
var nSlider = document.getElementById('numeroSlider');
var nLectura = document.getElementById('numeroLectura');
var vSlider = document.getElementById('velocidadSlider');
var vLectura = document.getElementById('velocidadLectura');
var selectElement = document.getElementById('stateSelect');
var state = selectElement.value;
var n = nSlider.value,
    vel = vSlider.value;

var running = false; // will be true when running
var i = 0,
    spf = 1;
var Y = [],
    nums = [],
    data = [],
    layout = [],
    config = [],
    binsSize;
emptyPlot();
simulate();






/////////////////////////////
// FUNCIONES DE SIMULACION //
/////////////////////////////

// Funcion para realizar la simulacion
function simulate() {
    if (running) {
        for (var step = 0; step < spf; step++) { // Segun los spf nos saltamos plotear iteraciones
            var traceGauss = gaussiana(n, state);
            var j = 0;
            j = randomCompound(n, state);
            Y.push(j);
        };
        trace = {
            x: Y,
            type: "histogram",
            histnorm: 'probability',
            xbins: {
                size: binsSize,
            }
        };
        data = [trace, traceGauss];
        Plotly.react(myDiv, data, layout, config);
        i++;
    }
    window.setTimeout(simulate, 1); //Aqui iria el ajuste de velocidad
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
function resetNums() {
    running = false;
    startButton.value = " Start ";
    Y = [];
    emptyPlot();
}









//////////////////////////
// FUNCIONES DE CALCULO //
//////////////////////////

// Funcion para calcular el numero compuesto Y
function randomCompound(n, tipo) {
    var X, Y = 0;
    if (tipo == "0") {
        for (var i = 0; i < n; i++) {
            X = Math.floor(Math.random() * 6) + 1;
            Y += X;
        }
        binsSize = "1";
        return Y;
    } else if (tipo == "1") {
        for (var i = 0; i < n; i++) {
            X = Math.floor(Math.random() * 2);
            Y += X;
        }
        binsSize = "1";
        return Y;
    } else if (tipo == "2") {
        for (var i = 0; i < n; i++) {
            X = Math.random();
            Y += X;
        }
        binsSize = "1";
        return Y;
    } else {
        for (var i = 0; i < n; i++) {
            X = Math.sin(Math.pow(Math.random() * Math.PI, 2));
            Y += X * X;
        }
        if (n < 4) {
            binsSize = "0.1";
        } else {
            binsSize = "1";
        }
        return Y;
    };
}

// Funcion para calcular la curva
function gaussiana(n, state) {
    var mu, s2, Ymu, Ys2, max, scale, k,
        X = [],
        Y = [];
    if (state == "0") {
        mu = 3.5;
        s2 = 105 / 36; //Esta fraccion es la varianza de un solo dado
        max = 6 * n;
        scale = 5;
    } else if (state == "1") {
        mu = 0.5;
        s2 = 1 / 4;
        max = 2 * n;
        scale = 5;
    } else if (state == "2") {
        mu = 0.5;
        s2 = 1 / 12;
        max = 2 * n / 3;
        scale = 5;
    } else {
        mu = 0.4;
        s2 = 1 / 8;
        max = 2 * n;
        scale = 5;
    };
    Ymu = mu * n;
    Ys2 = s2 / n;
    k = 0;
    for (var i = -1; i < max; i++) {
        for (var j = 0; j < scale; j++) {
            X.push(i + (j + 1) / scale);
            Y.push((1 / Math.sqrt(2 * n * n * Math.PI * Ys2)) / Math.pow(Math.E, Math.pow(X[k] - Ymu, 2) / (2 * n * n * Ys2)));
            k++;
        }
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

function emptyPlot() {
    nums = [];
    trace = {
        x: nums,
        type: "histogram",
        histnorm: 'probability',
    };
    data = [trace];
    layout = {
        title: "Histograma de Y",
        xaxis: {
            title: 'y',
        },
        yaxis: {
            title: 'P(y)',
        },
        font: {
            size: 15,
        },
        margin: {
            l: 60,
            r: 60,
            d: 60,
        },
        showlegend: false,
    };
    config = {
        staticPlot: true,
        responsive: true,
    };
    Plotly.react("myDiv", data, layout, config);
}









///////////////////////////
// FUNCIONES DE INTERFAZ //
///////////////////////////

// Funcion para actualizar la lectura de N
function updateNum() {
    nLectura.textContent = Number(nSlider.value);
    n = nSlider.value;
}

// Funcion para actualizar la lectura de la velocidad
function updateVel() {
    vLectura.textContent = Number(vSlider.value);
    vel = vSlider.value;
    spf = Math.pow(vel, 2)
}

// Funcion para actualizar el tipo de distribucion
function updateState() {
    state = selectElement.value;
}