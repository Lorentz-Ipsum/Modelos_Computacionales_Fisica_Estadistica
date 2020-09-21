var canvas = document.getElementById('canvasMacro'),
    ctx = canvas.getContext('2d'),
    wMacro = canvas.width,
    hMacro = canvas.height,
    defRadius = document.getElementById('defradius');

var canvasRect = document.getElementById('canvasMacroCuadrado'),
    ctx2 = canvasRect.getContext('2d'),
    drag = false,
    client = canvasRect.getBoundingClientRect();

var kTMacro = tempMacro.value,
    checkBoxMacro = conInterMacro.checked;

var runningMacro = false;

var optsMacro = {
    bg: "white",
    ballsMacroAmount: nMacro.value,
    lineWidth: 3
};
var mouse = {
    x: wMacro,
    y: hMacro,
};
var rect = {
    startX: 0,
    startY: 0,
    endX: 0,
    endY: 0,
}
var isDrawing = false;

canvas.addEventListener('mousemove', function(e) {
    mouse.x = e.offsetX;
    mouse.y = e.offsetY;
});

canvas.addEventListener('mousedown', function(e) {
    isDrawing = true;
    rect.startX = mouse.x;
    rect.startY = mouse.y;
});
canvas.addEventListener('mouseup', function(e) {
    isDrawing = false;
    rect.endX = mouse.x; // Estas son las coordenadas absolutas, para dibujar el cuadrado hay que restar startX
    rect.endY = mouse.y;
    drawBackMacro();
    ctx2.setLineDash([6]);
    ctx2.fillStyle = "gray";
    ctx2.fillRect(rect.startX, rect.startY, rect.endX - rect.startX, rect.endY - rect.startY);
    ctx2.strokeRect(rect.startX, rect.startY, rect.endX - rect.startX, rect.endY - rect.startY);
    if (rect.endX - rect.startX < 0 || rect.endY - rect.startY < 0) {
        var tempX = rect.startX,
            tempY = rect.startY;
        rect.startX = Math.min(tempX, rect.endX);
        rect.startY = Math.min(tempY, rect.endY);
        rect.endX = Math.max(tempX, rect.endX);
        rect.endY = Math.max(tempY, rect.endY);
    };
});



var ballsMacro = [];

var NinSquare = [],
    FramesMacro = [];
var tMacro = 0;

var palette = [
    // 'rgba(93, 211, 158, 1)',
    // 'rgba(52, 138, 167, 1)',
    // 'rgba(82, 81, 116, 1)',
    'blue'
];
var radius = 6;


Ball.prototype = {
    draw: function() {
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2, false);
        ctx.fillStyle = this.color;
        ctx.strokeStyle = this.color;
        ctx.lineWidth = optsMacro.lineWidth;
        ctx.fill();
        // ctx.stroke();
        ctx.closePath();
    },
    update: function() {
        this.x += this.direction.x * this.speed;
        this.y += this.direction.y * this.speed;

        if (checkBoxMacro) {
            for (var i = 0; i < ballsMacro.length; i++) {
                if (ballsMacro[i] === this) continue;
                var distance = getDistance(this.x, this.y, ballsMacro[i].x, ballsMacro[i].y);
                if (distance - (this.radius + ballsMacro[i].radius) < 0) {
                    this.direction.x = (this.x - ballsMacro[i].x) / distance; //get new cosinus
                    this.direction.y = (this.y - ballsMacro[i].y) / distance; //get new sinus
                }
            }
        };

        if (this.x - this.radius < 0 || this.x + this.radius > wMacro) {
            this.direction.x = -this.direction.x;
        };
        if (this.y - this.radius < 0 || this.y + this.radius > hMacro) {
            this.direction.y = -this.direction.y;
        };
    }
};


resetMacro();
plotPartInSquare(FramesMacro, NinSquare);
simulateMacro();

drawBackMacro();









// Funcion para empezar o pausar la simulacion
function startStopMacro() {
    runningMacro = !runningMacro;
    if (runningMacro) {
        console.log("START");
        i = 0;
        startBtnMacro.value = " Pause ";
        // reset();
    } else {
        startBtnMacro.value = "Resume";
    }
}

function resetMacro() {
    kTMacro = tempMacro.value;
    checkBoxMacro = conInterMacro.checked;
    optsMacro.ballsMacroAmount = nMacro.value;
    ballsMacro = [];

    NinSquare = [];
    FramesMacro = [];
    tMacro = 0;
    for (var i = 0; i < optsMacro.ballsMacroAmount; i++) {
        var x = randomIntFromRange(radius, wMacro - radius);
        var y = randomIntFromRange(radius, hMacro - radius);
        var color = getRandomColor(palette);

        if (i !== 0) {
            for (var j = 0; j < ballsMacro.length; j++) {
                if (getDistance(x, y, ballsMacro[j].x, ballsMacro[j].y) - (radius + ballsMacro[j].radius) < 0) {
                    x = randomIntFromRange(radius, wMacro - radius);
                    y = randomIntFromRange(radius, hMacro - radius);
                    j = -1;
                }
            }

        }
        ballsMacro.push(new Ball(x, y, radius, color));
    }
};

function simulateMacro() {
    if (runningMacro) {
        animateMacro();
    }
    window.setTimeout(simulateMacro, 0.1); //Aqui iria el ajuste de velocidad
}

function animateMacro() {
    NinSquare[tMacro] = 0;
    FramesMacro[tMacro] = tMacro;
    ctx.clearRect(0, 0, wMacro, hMacro);
    ballsMacro.forEach(function(ball) {
        ball.draw();
        ball.update();
        if (ball.x > rect.startX && ball.y > rect.startY && ball.x < rect.endX && ball.y < rect.endY) {
            NinSquare[tMacro]++;
        }
    });
    plotPartInSquare(FramesMacro, NinSquare);
    tMacro++;
};









function Ball(x, y, radius, color) {
    this.x = x;
    this.y = y;
    this.directionAngle = Math.random() * 360;
    this.direction = {
        x: Math.cos(this.directionAngle),
        y: Math.sin(this.directionAngle)
    };
    this.radius = radius;
    this.color = color;
    this.speed = Boltzmann(kTMacro); //5; //randomIntFromRange(1, 2) / 2;
}

function randomIntFromRange(min, max) {
    return Math.floor(min + Math.random() * (max - min + 1));
}

function getRandomColor(colors) {
    return colors[Math.floor(Math.random() * colors.length)];
}

function getDistance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

function Boltzmann(kTMacro) {
    var vel = Math.sqrt(-2.0 * Math.log(1.0 - Math.random()));
    return vel * Math.sqrt(kTMacro);
    // var x = A * Math.cos(B) * Math.sqrt(kTMacro);
    // var y = A * Math.cos(B) * Math.sqrt(kTMacro);
}


function factorial(n) {
    var factor = 1;
    for (var i = 1; i < n + 1; i++) {
        factor *= i;
    };
    return factor;
}

function poisson() {
    var mu, s2, area, scale = 5,
        n = optsMacro.ballsMacroAmount,
        max = Math.floor(n / 2 + 1),
        X = [],
        Y = [];
    var areaA = (rect.endX - rect.startX) * (rect.endY - rect.startY) / (wMacro * hMacro);
    area = areaA * 100;
    var k = 0;
    for (var i = 1; i < max; i++) {
        X.push(i);
        Y.push((1 / factorial(i)) * Math.pow(area, i) * Math.pow(Math.E, -area));
        k++;
    };
    var poissonTrace = {
        x: X,
        y: Y,
        mode: 'lines',
        line: {
            color: 'rgb(255,0,0)',
            width: 1,
            opacity: 0.7
        }
    };
    return poissonTrace;
}





///////////////////////
// FUNCIONES DE PLOT //
///////////////////////

function drawBackMacro() {
    ctx2.clearRect(0, 0, wMacro, hMacro);
    ctx2.fillStyle = 'lightgray';
    ctx2.fillRect(0, 0, wMacro, hMacro);
}

function plotPartInSquare(FramesMacro, NinSquare) {
    // Trazas
    var numsTrace = {
        x: FramesMacro,
        y: NinSquare,
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
        title: 'Partículas en el recinto',
        xaxis: {
            title: 't',
        },
        yaxis: {
            title: 'N',
        },
        font: {
            size: 15,
        },
        margin: {
            u: 30,
            l: 60,
            r: 30,
            d: 20,
        },
        font: {
            size: 15,
        },
        plot_bgcolor: 'rgb(223, 223, 223)'
    };

    var config = {
        staticPlot: true,
        responsive: true,
    }
    Plotly.newPlot('graphMacro', data, layout, config);
}

function plotHistMacro() {
    // Trazas
    var numsTrace = {
        x: NinSquare,
        type: "histogram",
        histnorm: 'probability',
        name: 'Diferencia',
    };
    var poissonTrace = poisson();
    var data = [numsTrace, poissonTrace];
    var layout = {
        width: 450,
        title: 'Distribución de partículas',
        xaxis: {
            title: 'N',
        },
        yaxis: {
            title: 'P(N)',
        },
        font: {
            size: 15,
        },
        margin: {
            u: 30,
            l: 60,
            r: 30,
            d: 20,
        },
        font: {
            size: 15,
        },
        showlegend: false,
        plot_bgcolor: 'rgb(223, 223, 223)'
    };

    var config = {
        staticPlot: true,
        responsive: true,
    }

    Plotly.newPlot('graphHistMacro', data, layout, config);
}