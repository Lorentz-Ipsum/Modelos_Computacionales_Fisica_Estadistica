// Contadores para los slider
$(document).on('input', 'input[type="range"]', function(e) {
    var changes = this.id + 'Out';
    document.getElementById(changes).innerHTML = e.target.value;
});

// CODIGO PRINCIPAL
$(document).ready(function() {
    // Boton Start / Stop
    $('input[type=checkbox][name=test]').change(function(e) {
        if (this.checked) {
            /* START */
            console.log("START");
            var i = 0;

            // Variables de los sliders
            var selectElement = document.querySelector('#stateSelect');
            var state = selectElement.value;
            var n = numero.value,
                vel = velocidad.value,
                Y = [];

            // Obtener los puntos para dibujar la gaussiana
            var traceGauss = calculaGaussiana(n, state);

            interval = setInterval(function() {
                /* LOOP */
                // Obtener un nuevo Y y añadirlo al array
                var j = 0;
                j = randomCompound(n, state);
                Y.push(j);

                plotDistr(Y, traceGauss);
                console.log(i++ + ": " + j);
            }, (1 / vel) * 100);
        } else {
            /* STOP */
            console.log("STOP"); // Esta forma de parar el bucle tiene un bug.
            clearInterval(interval);
        }
    });
});


// Funciones de plot
function plotDistr(nums, gauss) { // Funcion cutre. Arreglar que repita todo el proceso de plot cada vez.
    // También, quitar la interactividad de las gráficas.

    var trace = {
        x: nums,
        type: "histogram",
        histnorm: 'probability',
    };
    var data = [trace, gauss];

    var layout = {
        title: "Distribution Graph"
    };

    Plotly.newPlot("myDiv", data, layout);
}


// Funciones de calculo
function randomCompound(n, tipo) {
    var X, Y = 0;
    if (tipo == "0") {
        for (var i = 0; i < n; i++) {
            X = Math.floor(Math.random() * 6) + 1;
            Y += X;
        }
        return Y;
    } else if (tipo == "1") {
        for (var i = 0; i < n; i++) {
            X = Math.floor(Math.random() * 2);
            Y += X;
        }
        return Y;
    } else {
        for (var i = 0; i < n; i++) {
            X = Math.random();
            Y += X;
        }
        return Y;
    };
}

function calculaGaussiana(n, state) {
    var mu, s2, Ymu, Ys2, max, scale, k,
        X = [],
        Y = [];
    if (state == "0") {
        mu = 3.5;
        s2 = 105 / 36; //Esta fraccion es la varianza de un solo dado
        max = 6 * n;
        scale = 6;
    } else if (state == "1") {
        mu = 0.5;
        s2 = 1 / 2;
        max = n;
        scale = 10;
    } else {
        mu = 0.5;
        s2 = 1 / 12;
        max = n;
        scale = 10;
    };
    Ymu = mu * n;
    Ys2 = s2 / n;
    k = 0;
    for (var i = 0; i < max; i++) {
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