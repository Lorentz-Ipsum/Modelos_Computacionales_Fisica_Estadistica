var canvas = document.getElementById('vis');
var preview = document.getElementById('preview');
var ctx = canvas.getContext("2d");

var imageSelect = document.querySelector('#imageSelectArnold');
var w = 0;

var iterations = 0,
    stopIteration = 0;
var imageData, data, imgNext, originalImage;
var play = false;

var t11, t12, t21, t22;
changeValsT();

function redondea(num, places) {
    return +(Math.round(num + "e+" + places) + "e-" + places);
}

function iteration() {
    var i = stopIteration;
    var diff = Math.abs(iterations - i);
    if (diff == 0 && !play) return;
    if (!imageData) return;
    var data = imageData.data;
    var nextData = imgNext.data;
    var src = 0;
    var dataWidth = Math.sqrt(data.length >> 2);
    for (var y = 0; y < dataWidth; y++) {
        for (var x = 0; x < dataWidth; x++) {
            var xNew = (t11 * x + t21 * y) % dataWidth;
            var yNew = (t12 * x + t22 * y) % dataWidth;
            if (yNew < 0) {
                yNew = dataWidth + yNew;
            }
            var dst = (yNew * dataWidth + xNew) * 4;
            if (iterations < i) {
                for (var j = 0; j < 4; j++) nextData[dst++] = data[src++];
            } else {
                for (var j = 0; j < 4; j++) nextData[src++] = data[dst++];
            }
        }
    }
    var tmp = imageData;
    imageData = imgNext;
    imgNext = tmp;
    iterations += (iterations < i) ? 1 : -1;
    $('#iteration-count').text(iterations);
    if (play) {
        for (var j = 0; j < 20; j++) {
            if (imageData.data[j] !== originalImage.data[j]) break;
            if (j === 19 || iterations == stopIteration) {
                stopIteration = iterations;
                play = false;
                $('#iterations').slider('enable').focus();
                $('#step-back, #step-forward').removeAttr('disabled');
            }
        }
        $('#iterations').slider('value', iterations);
    }
    var diff = Math.abs(iterations - stopIteration);
    if (diff < 10 || diff % 10 == 0) ctx.putImageData(imageData, 0, 0);
}
setInterval(iteration, 1);
resetData();
$('#play').click(function() {
    play = true;
    stopIteration = 3 * w;
    $('#step-back, #step-forward').attr('disabled', 'disabled');
});
$('#step-back').click(function() {
    stopIteration--;
});
$('#step-forward').click(function() {
    stopIteration++;
});

function createImage() {
    var img = new Image();
    img.onload = function() {
        w = 400;
        ctx.width = canvas.width;
        ctx.height = canvas.height;
        ctx.drawImage(img, 0, 0, ctx.width, ctx.height);
        imageData = ctx.getImageData(0, 0, ctx.width, ctx.height);
        originalImage = ctx.getImageData(0, 0, ctx.width, ctx.height);
        imgNext = ctx.getImageData(0, 0, ctx.width, ctx.height);
    }
    return img;
}

function changeValsT() {
    resetData();
    t11 = document.getElementById('t11').value;
    t12 = document.getElementById('t12').value;
    t21 = document.getElementById('t21').value;
    t22 = redondea((t12 * t21 + 1) / t11, 4);
}


function resetData() {
    var img = createImage();
    play = false;
    stopIteration = 0;
    iterations = 0;
    $('#iteration-count').text(iterations);
    switch (Number(imageSelectArnold.value)) {
        case 0:
            img.src = 'imagen.png';
            break;
        case 1:
            img.src = 'Ising-tartan.png';
            break;
        case 2:
            img.src = 'catmap.jpg';
            break;
    };
}