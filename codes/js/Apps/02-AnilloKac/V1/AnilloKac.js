// Contadores para los slider
$(document).on('input', 'input[type="range"]', function(e) {
    var changes = this.id + 'Out';
    document.getElementById(changes).innerHTML = e.target.value;
});

// CODIGO PRINCIPAL
$(document).ready(function() {
    $('input[type=checkbox][name=test]').change(function(e) {
        if (this.checked) {
            /* START */
            console.log("START");
            var f = 0;

            // Inicializar los valores de los sliders
            var selectElement = document.querySelector('#stateSelect');
            var tipo = selectElement.value;
            var n = numero.value,
                m = sectores.value,
                vel = velocidad.value;
            // Generar el anillo externo y las divisiones, dibujarlos
            var ring = generaAnillo(n, m, tipo);
            var S = ring[0],
                X0 = ring[1];
            var ext = transformaX(X0, S);
            var blueExt = ext[0],
                redExt = ext[1];
            var radBlueExt = generaRadio(blueExt.length, 2),
                radRedExt = generaRadio(redExt.length, 2),
                thBlueExt = getTheta(blueExt, n),
                thRedExt = getTheta(redExt, n);
            // DIVISIONES
            var radDiv = generaRadio(S.length, 1.7),
                thDiv = getTheta(S, n);
            // OFFSET para que las divisiones aparezcan ENTRE puntos
            for (var i = 0; i < thDiv.length; i++) {
                thDiv[i] -= 360 / (n * 2);
            }
            //PARA LA PRIMERA ITERACION
            var blueInt = blueExt,
                redInt = redExt,
                X = X0;
            var frames = [],
                dif = [];

            frames.push(f);

            // LOOP DE PLOT
            // Empezar el loop en si
            interval = setInterval(function() {
                console.log(f++);

                var radBlueInt = generaRadio(blueInt.length, 1.5),
                    radRedInt = generaRadio(redInt.length, 1.5),
                    thBlueInt = getTheta(blueInt, n),
                    thRedInt = getTheta(redInt, n);

                // Dibuja el anillo interior y la diferencia de numeros
                plotAnillo(radBlueExt, thBlueExt, radRedExt, thRedExt, radBlueInt, thBlueInt, radRedInt, thRedInt, radDiv, thDiv);

                dif.push(blueInt.length - redInt.length);
                plotNumber(frames, dif);

                frames.push(f);

                // Avanza un paso
                var N = anilloStep(X, S);

                var int = transformaX(N, S);
                blueInt = int[0];
                redInt = int[1];

                X = N;
                // Repite
            }, (1 / vel) * 100);
        } else {
            /* Act on the event */
            console.log("STOP");
            clearInterval(interval);
        }
    });
}); // Fin de document.ready





// DEFINICION DE FUNCIONES

function transformaX(X) {
    var blue = [],
        red = [];
    for (var i = 0; i < X.length; i++) {
        if (X[i]) {
            blue.push(i);
        } else {
            red.push(i);
        }
    }
    return [blue, red];
}

function getTheta(arr, n) {
    var thArr = [];
    for (var i = 0; i < arr.length; i++) {
        thArr.push(360 * (arr[i]) / n);
    }
    return thArr;
}

function generaRadio(n, r) {
    var rad = [];
    for (var i = 0; i < n; i++) {
        rad.push(r);
    }
    return rad;
}






function generaAnillo(n, m, tipo) {
    var X = [],
        S = [],
        blue = [],
        red = [];

    // Genera los sectores
    for (var i = 0; i < m; i++) {
        var l = Math.floor(n * Math.random(1, n + 1));
        if (S.includes(l)) {
            i -= i;
        } else {
            S.push(l);
        }
    };
    switch (Number(tipo)) {
        case 0:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.5);
                X.push(j);
                if (j) {
                    blue.push(i);
                } else {
                    red.push(i);
                }
            }
            break;
        case 1:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.3);
                X.push(j);
                if (j) {
                    blue.push(i);
                } else {
                    red.push(i);
                }
            }
            break;
        case 2:
            for (var i = 0; i < n; i++) {
                var j = (Math.random() >= 0.7);
                X.push(j);
                if (j) {
                    blue.push(i);
                } else {
                    red.push(i);
                }
            }
            break;
        case 3:
            var j = true;
            for (var i = 0; i < n; i++) {
                X.push(j);
                blue.push(i);
            }
            break;
        case 4:
            for (var i = 0; i < n; i++) {
                var j = (i % 2 == 0);
                X.push(j);
                if (j) {
                    blue.push(i);
                } else {
                    red.push(i);
                }
            }
            break;
    };
    return [S, X, blue, red];
}

function anilloStep(X, S) {
    var X_s = [],
        L = X.length;
    for (var i = 0; i < L; i++) {
        if (S.includes(i, 0) && i == 0) {
            X_s.push(!X[L - 1]);
        } else if (S.includes(i, 0) && i != 0) {
            X_s.push(!X[i - 1]);
        } else if (!S.includes(i, 0) && i == 0) {
            X_s.push(X[L - 1]);
        } else if (!S.includes(i, 0) && i != 0) {
            X_s.push(X[i - 1]);
        }
    }
    return X_s;
}






function plotNumber(frames, dif) {
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
    var data = [numsTrace]; // Cargamos las trazas

    var layout = {
        autosize: false,
        width: 500,
        height: 500,
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

function plotAnillo(radBlueExt, thBlueExt, radRedExt, thRedExt, radBlueInt, thBlueInt, radRedInt, thRedInt, radDiv, thDiv) {
    // Trazas
    var extBlueTrace = {
        r: radBlueExt,
        t: thBlueExt,
        mode: 'markers',
        name: 'extBlue',
        marker: {
            color: 'rgb(0,0,255)',
            size: 110,
            opacity: 0.7
        },
        type: 'scatter'
    };
    var extRedTrace = {
        r: radRedExt,
        t: thRedExt,
        mode: 'markers',
        name: 'extRed',
        marker: {
            color: 'rgb(255,0,0)',
            size: 110,
            opacity: 0.7
        },
        type: 'scatter'
    };


    var intBlueTrace = {
        r: radBlueInt,
        t: thBlueInt,
        mode: 'markers',
        name: 'inBlue',
        marker: {
            color: 'rgb(0,0,255)',
            size: 110,
            opacity: 0.7
        },
        type: 'scatter'
    };
    var intRedTrace = {
        r: radRedInt,
        t: thRedInt,
        mode: 'markers',
        name: 'inRed',
        marker: {
            color: 'rgb(255,0,0)',
            size: 110,
            opacity: 0.7
        },
        type: 'scatter'
    };


    var divTrace = {
        r: radDiv,
        t: thDiv,
        mode: 'markers',
        name: 'div',
        marker: {
            color: 'rgb(0,255,0)',
            size: 110,
            opacity: 0.7
        },
        type: 'scatter'
    };

    var data = [extBlueTrace, extRedTrace, intBlueTrace, intRedTrace, divTrace]; // Cargamos las trazas
    //
    // Plotly.restyle('anillo', data, layout);
    var layout = {
        autosize: false,
        width: 300,
        height: 300,
        title: 'Anillo de Kac',
        font: {
            size: 15
        },
        plot_bgcolor: 'rgb(223, 223, 223)',
        radialaxis: {
            tickcolor: 'rgb(253,253,253)',
            showgrid: false,
            zeroline: false,
            showline: false,
            autotick: false,
            ticks: '',
            showticklabels: false
        },
        angularaxis: {
            tickcolor: 'rgb(253,253,253)',
            showgrid: false,
            zeroline: false,
            showline: false,
            autotick: false,
            ticks: '',
            showticklabels: false
        },
        showlegend: false,
        showticklabels: false
    };

    var config = {
        staticPlot: true
    }

    Plotly.newPlot('anillo', data, layout, config);
}