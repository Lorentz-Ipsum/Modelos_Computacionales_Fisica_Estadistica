// CODIGO PRINCIPAL
$(document).ready(function() {
    // Boton Start / Stop
    calculaDebBtn.onclick = function() {
        /* START */
        console.log("START");

        // Variables de los sliders
        var Tdebye = temperatura.value;
        var dens = [];

        dens = calculaDeb(Tdebye);
        plotDistrDeb(dens[0], dens[1]);
    };
});


// Funciones de plot
function plotDistrDeb(nums, dens) { // Funcion cutre. Arreglar que repita todo el proceso de plot cada vez.
    // También, quitar la interactividad de las gráficas.

    var trace = {
        x: nums,
        y: dens,
        type: "lines",
    };
    var data = [trace];

    var layout = {
        title: "Distribution Graph"
    };

    Plotly.newPlot("divDeb", data, layout);
}


// Funciones de calculo
function calculaDeb(Tdebye) {
    var d = Tdebye / 100;
    var d2 = 0;
    var X = [],
        densidad = [];
    for (var d3 = d; d3 <= 3 * Tdebye; d3 += d) {
        var d4 = integ(d3, Tdebye);
        X.push(d3 - d);
        X.push(d3);
        densidad.push(d2);
        densidad.push(d4);
        d2 = d4;
    }
    return [X, densidad];
}


function integ(d, d2) {
    var arrd = [];
    arrd[0] = 0.1488743389;
    arrd[1] = 0.4333953941;
    arrd[2] = 0.6794095682;
    arrd[3] = 0.8650633666;
    arrd[4] = 0.97390652;
    var arrd2 = arrd;
    var arrd3 = [];
    arrd3[0] = 0.2955242247;
    arrd3[1] = 0.2692667193;
    arrd3[2] = 0.2190863625;
    arrd3[3] = 0.1494513491;
    arrd3[4] = 0.06667134;
    var arrd4 = arrd3;
    var d3 = 0.5 * d2 / d;
    var d4 = 0.5 * d2 / d;
    var d5 = 0.0;
    for (var i = 0; i <= 4; i++) {
        var d6 = d4 * arrd2[i];
        var d7 = d3 + d6;
        d5 += arrd4[i] * d7 * d7 * d7 * d7 * Math.exp(d7) / (Math.exp(d7) - 1.0) / (Math.exp(d7) - 1.0);
        d7 = d3 - d6;
        d5 += arrd4[i] * d7 * d7 * d7 * d7 * Math.exp(d7) / (Math.exp(d7) - 1.0) / (Math.exp(d7) - 1.0);
    }
    return d5 * 9.0 * (d / d2) * (d / d2) * (d / d2) * d4;
}