// CODIGO PRINCIPAL
$(document).ready(function() {
    // Boton Start / Stop
    calculaBtn.onclick = function() {
        /* START */
        console.log("START");

        // Variables de los sliders
        var Trot = tempRot.value,
            Tvib = tempVib.value;
        var dens = [];

        dens = calcula(Trot, Tvib);
        plotDistr(dens[0], dens[1]);
    };
});


// Funciones de plot
function plotDistr(nums, dens) { // Funcion cutre. Arreglar que repita todo el proceso de plot cada vez.
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

    Plotly.newPlot("myDiv", data, layout);
}


// Funciones de calculo
function calcula(Trot, Tvib) {
    var var1 = 0,
        tmin;
    if (Trot <= Tvib) {
        tmin = Math.log10(Trot / 50);
    } else {
        tmin = Math.log10(Tvib / 50);
    }

    var var3 = tmin + Math.log10(50);
    var tmax = Math.log10(5.0 * (Trot + Tvib));
    var tstep = (tmax - tmin) / 200;

    // Todo esto comentado es para fijar escalas y dibujar las rectas

    // densidad.fijaEscalas(Math.floor(var3) - 1.0 D, Math.floor(tmax) + 0.0 D, 1.0 D, 4.0 D);
    // densidad.Limpia();
    // densidad.setCol(Color.lightGray);
    // densidad.Recta(Math.log10(Trot), 1.0 D, Math.log10(Trot), 4.0 D);
    // densidad.Recta(Math.log10(Tvib), 1.0 D, Math.log10(Tvib), 4.0 D);
    // densidad.Recta(Math.floor(var3) - 1.0 D, 1.5 D, Math.floor(this.tmax) + 1.0 D, 1.5 D);
    // densidad.Recta(Math.floor(var3) - 1.0 D, 2.5 D, Math.floor(this.tmax) + 1.0 D, 2.5 D);
    // densidad.Recta(Math.floor(var3) - 1.0 D, 3.5 D, Math.floor(this.tmax) + 1.0 D, 3.5 D);
    // densidad.setCol(Color.black);

    var X = [],
        dens = [];

    for (var tln = tmin; tln <= tmax; tln += tstep) {
        var t = Math.pow(10, tln);
        var ttr = Trot / t;
        var ttv = Tvib / t;
        var cv = 1.5 + cvvib(ttv) + cvrot(ttr);
        X.push(tln - tstep);
        X.push(tln);
        dens.push(var1);
        dens.push(cv);
        // densidad.Recta(this.tln - this.tstep, var1, this.tln, this.cv);
        var1 = cv;
    }
    return [X, dens]
}

function cvrot(var1) {
    var var3;
    if (var1 < 0.1) {
        var3 = 1.0;
    } else {
        var var5 = 0.0;
        var var7 = 0.0;
        var var9 = 0.0;

        for (j = 0; j <= 1000; j++) {
            var var11 = Math.exp(-var1 * j * (j + 1));
            var5 += (2 * j + 1) * var11;
            var7 += ((2 * j + 1) * j * (j + 1)) * var11;
            var9 += ((2 * j + 1) * j * (j + 1) * j * (j + 1)) * var11;
        }

        var3 = (var9 / var5 - var7 * var7 / var5 / var5) * var1 * var1;
    }

    return var3;
}

function cvvib(var1) {
    var var3;
    if (var1 > 100.0) {
        var3 = 0.0;
    } else {
        var3 = var1 * var1 * Math.exp(var1) / (Math.exp(var1) - 1.0) / (Math.exp(var1) - 1.0);
    }

    return var3;
}