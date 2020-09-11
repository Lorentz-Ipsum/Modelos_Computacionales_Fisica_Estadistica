var canvas = document.getElementById('canvasFluctuaciones'),
    ctx = canvas.getContext('2d'),
    canvasMarco = document.getElementById('canvasFluctuacionesMarco'),
    ctxMarco = canvas.getContext('2d'),
    w = canvas.width = window.innerWidth,
    h = canvas.height = window.innerHeight,
    defRadius = document.getElementById('defradius');

var opts = {
    // bg: "white",
    ballsAmount: nFluctuaciones.value,
    lineWidth: 3
};

drawMarco();

function drawMarco() {
    ctxMarco.fillStyle = 'white';
    ctxMarco.fillRect(0, 0, w, h);
    ctxMarco.beginPath();
    ctxMarco.moveTo(w / 2, 0);
    ctxMarco.lineTo(w / 2, h);
    ctx.setLineDash([5, 15]);
    ctxMarco.lineWidth = 3;
    ctxMarco.strokeStyle = 'black';
    ctxMarco.stroke();
}

startBtnFluc.onclick = function() {
    'use strict';

    drawMarco();
    var kT = tempFluctuaciones.value;

    var mouse = {
        x: w / 2,
        y: h / 2
    };
    canvas.addEventListener('mousemove', function(e) {
        mouse.x = e.pageX;
        mouse.y = e.pageY;
    });

    var balls = [];

    var Ndif = [],
        Nleft = [],
        Nright = [],
        frames = [];
    var f = 0;

    var palette = [
        // 'rgba(93, 211, 158, 1)',
        // 'rgba(52, 138, 167, 1)',
        // 'rgba(82, 81, 116, 1)',
        'rgba(81, 59, 86, 1)'
    ];

    function randomIntFromRange(min, max) {
        return Math.floor(min + Math.random() * (max - min + 1));
    }

    function getRandomColor(colors) {
        return colors[Math.floor(Math.random() * colors.length)];
    }

    function getDistance(x1, y1, x2, y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    function Boltzmann(kT) {
        var vel = Math.sqrt(-2.0 * Math.log(1.0 - Math.random()));
        return vel * Math.sqrt(kT);
        // var x = A * Math.cos(B) * Math.sqrt(kT);
        // var y = A * Math.cos(B) * Math.sqrt(kT);
    }

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
        this.speed = Boltzmann(kT); //5; //randomIntFromRange(1, 2) / 2;
    }


    Ball.prototype = {
        draw: function() {
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2, false);
            ctx.fillStyle = this.color;
            ctx.strokeStyle = this.color;
            ctx.lineWidth = opts.lineWidth;
            ctx.fill();
            // ctx.stroke();
            ctx.closePath();
        },
        update: function() {
            this.x += this.direction.x * this.speed;
            this.y += this.direction.y * this.speed;

            for (var i = 0; i < balls.length; i++) {
                if (balls[i] === this) continue;
                var distance = getDistance(this.x, this.y, balls[i].x, balls[i].y);
                if (distance - (this.radius + balls[i].radius) < 0) {
                    this.direction.x = (this.x - balls[i].x) / distance; //get new cosinus
                    this.direction.y = (this.y - balls[i].y) / distance; //get new sinus
                }

            }

            if (this.x - this.radius < 0 || this.x + this.radius > w) {
                this.direction.x = -this.direction.x;
            }
            if (this.y - this.radius < 0 || this.y + this.radius > h) {
                this.direction.y = -this.direction.y;
            }
        }
    };

    function init() {
        for (var i = 0; i < opts.ballsAmount; i++) {
            var radius = 10; // randomIntFromRange(10, h / 10);
            var x = randomIntFromRange(radius, w / 2 - radius);
            var y = randomIntFromRange(radius, h - radius);
            var color = getRandomColor(palette);

            if (i !== 0) {
                for (var j = 0; j < balls.length; j++) {
                    console.log(j);
                    if (getDistance(x, y, balls[j].x, balls[j].y) - (radius + balls[j].radius) < 0) {
                        x = randomIntFromRange(radius, w / 2 - radius);
                        y = randomIntFromRange(radius, h - radius);
                        j = -1;
                    }
                }

            }

            balls.push(new Ball(x, y, radius, color));
        }
    };

    function plotPart(frames, Ndif) {
        // Trazas
        var numsTrace = {
            x: frames,
            y: Ndif,
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
            title: 'Diferencia de entre ambos lados',
            font: {
                size: 15
            },
            plot_bgcolor: 'rgb(223, 223, 223)'
        };

        var config = {
            staticPlot: true,
            responsive: true,
        }

        Plotly.newPlot('graphFluctuaciones', data, layout, config);
    }

    function animate() {
        window.requestAnimationFrame(animate);
        Ndif[f] = 0;
        Nleft[f] = 0;
        Nright[f] = 0;
        frames[f] = f;
        ctx.clearRect(0, 0, w, h);
        drawMarco();
        // ctx.fillStyle = opts.bg;
        // ctx.fillRect(0, 0, w, h);
        balls.forEach(function(ball) {
            ball.draw();
            ball.update();
            if (ball.x + ball.radius / 2 <= w / 2) {
                Nleft[f]++;
            } else {
                Nright[f]++;
            }
        });
        Ndif[f] = Nleft[f] - Nright[f];
        plotPart(frames, Ndif);
        f++;
    };
    init();
    animate();

};