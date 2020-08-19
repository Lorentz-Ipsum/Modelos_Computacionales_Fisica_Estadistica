var img = new Image();
img.crossOrigin = "Anonymous";
img.src = './imagen.png';
img.onload = function() {
    draw(this);
};

function draw(img) {
    var canvas = document.getElementById('canvas');
    var stretch = document.getElementById('stretch');
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    img.style.display = 'none';
    var imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    var data = imageData.data;
    var zoomctx = document.getElementById('zoom').getContext('2d');
    var stretchctx = document.getElementById('stretch').getContext('2d');

    var invert = function() {
        for (var i = 0; i < data.length; i += 4) {
            data[i] = 255 - data[i]; // red
            data[i + 1] = 255 - data[i + 1]; // green
            data[i + 2] = 255 - data[i + 2]; // blue
        }
        ctx.putImageData(imageData, 0, 0);
    };

    var grayscale = function() {
        for (var i = 0; i < data.length; i += 4) {
            var avg = (data[i] + data[i + 1] + data[i + 2]) / 3;
            data[i] = avg; // red
            data[i + 1] = avg; // green
            data[i + 2] = avg; // blue
        }
        ctx.putImageData(imageData, 0, 0);
    };

    var baker1 = function() {
        stretchctx.drawImage(canvas,
            0, 0,
            img.width / 2, img.height,
            0, img.height / 2,
            img.width, img.height / 2);
    };

    var baker2 = function() {
        stretchctx.drawImage(canvas,
            img.width / 2, 0,
            img.width / 2, img.height,
            0, 0,
            img.width, img.height / 2);
    };

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


    // Codigo del zoom
    var smoothbtn = document.getElementById('smoothbtn');
    var toggleSmoothing = function(event) {
        zoomctx.imageSmoothingEnabled = this.checked;
        zoomctx.mozImageSmoothingEnabled = this.checked;
        zoomctx.webkitImageSmoothingEnabled = this.checked;
        zoomctx.msImageSmoothingEnabled = this.checked;
    };
    smoothbtn.addEventListener('change', toggleSmoothing);

    var zoom = function(event) {
        var x = event.layerX;
        var y = event.layerY;
        zoomctx.drawImage(canvas,
            Math.min(Math.max(0, x - 5), img.width - 10),
            Math.min(Math.max(0, y - 5), img.height - 10),
            10, 10,
            0, 0,
            200, 200);
    };

    canvas.addEventListener('mousemove', zoom);

    // Para usar los botones
    var invertbtn = document.getElementById('invertbtn');
    invertbtn.addEventListener('click', invert);
    var grayscalebtn = document.getElementById('grayscalebtn');
    grayscalebtn.addEventListener('click', grayscale);
    var bakerbtn = document.getElementById('bakerbtn');
    bakerbtn.addEventListener('click', baker1);
    bakerbtn.addEventListener('click', baker2);
    var remapbtn = document.getElementById('remapbtn');
    remapbtn.addEventListener('click', remap);
}