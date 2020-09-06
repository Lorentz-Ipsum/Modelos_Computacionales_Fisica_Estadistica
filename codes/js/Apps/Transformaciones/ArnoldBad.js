var imgArnold = new Image();
imgArnold.crossOrigin = "Anonymous";

var imageSelect = document.querySelector('#imageSelect');
imgArnold.src = 'imagen.png';
// Añadir un selector de imagen
imgArnold.onload = function() {
    draw(this);
};

function draw(imgArnold) {
    var canvas = document.getElementById('canvas');
    var stretch = document.getElementById('stretch');
    var ctx = canvas.getContext('2d');
    var stretchctx = stretch.getContext('2d');
    ctx.drawImage(imgArnold, 0, 0);
    imgArnold.style.display = 'none';
    var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    var data = imageData.data;
    var imgArnoldNext = ctx.getImageData(0, 0, canvas.width, canvas.height);
    var t11 = document.getElementById('t11').value,
        t12 = document.getElementById('t12').value,
        t21 = document.getElementById('t21').value;
    var t22 = (t12 * t21 + 1) / t11;
    // if (this.t11 == 0.0) {
    //     this.t11 = 2.0;
    // }
    var iterations = 0,
        stopIteration = 0;

    var itera = function() {
        var i = stopIteration;
        var diff = Math.abs(iterations - i);
        var data = imageData.data;
        var nextData = imgArnoldNext.data;
        var src = 0;
        var dataWidth = Math.sqrt(data.length >> 2);
        for (var y = 0; y < dataWidth; y++) {
            for (var x = 0; x < dataWidth; x++) {
                var xNew = (2 * x + y) % dataWidth;
                var yNew = (x + y) % dataWidth;
                var dst = (yNew * dataWidth + xNew) * 4;
                if (iterations < i) {
                    for (var j = 0; j < 4; j++) nextData[dst++] = data[src++];
                } else {
                    for (var j = 0; j < 4; j++) nextData[src++] = data[dst++];
                }
            }
        }
        var tmp = imageData;
        imageData = imgArnoldNext;
        imgArnoldNext = tmp;
        iterations += (iterations < i) ? 1 : -1;
        // var data2 = [];
        // for (var i = 0; i < data.length; i += 4) {
        //     var X = indexToPixel(i);
        //     var X2 = arnold(X);
        //     var j = pixelToIndex(X2);
        //
        //     data2[j] = data[i]; // red
        //     data2[j + 1] = data[i + 1]; // green
        //     data2[j + 2] = data[i + 2]; // blue
        //     data2[j + 3] = data[i + 3]; // blue
        // }
        // stretchctx.putImageData(imageData2, 0, 0);
    }

    var arnold = function(X) {
        var x = X[0],
            y = X[1];
        var x2 = t11 * x + t12 * y;
        var y2 = t21 * x + t22 * y;
        return [x2, y2];
    };

    var indexToPixel = function(i) {
        var x = (i % 4) % imgArnold.width + 1;
        var y = Math.floor((i % 4) / imgArnold.width) + 1;
        return [x, y];
    }

    var pixelToIndex = function(X) {
        var x = X[0],
            y = X[1];
        var i = x - 1 + (y - 1) * imgArnold.width;
        return i * 4;
    }

    var remap = function() {
        var remapImageData = stretchctx.getImageData(0, 0, stretch.width, stretch.height);
        var remapData = remapImageData.data;
        for (var i = 0; i < data.length; i += 4) {
            data[i] = remapData[i]; // red
            data[i + 1] = remapData[i + 1]; // green
            data[i + 2] = remapData[i + 2]; // blue
        }
        ctx.putImageData(imageData, 0, 0);
    };
    var arnoldbtn = document.getElementById('arnoldbtn');
    arnoldbtn.addEventListener('click', itera);
    var remapbtn = document.getElementById('remapbtn');
    remapbtn.addEventListener('click', remap);
}