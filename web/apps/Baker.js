var canvasBak = document.getElementById('canvasBaker');
var ctxBak = canvasBak.getContext('2d');
var canvas2 = document.getElementById('canvasBaker2');
var ctx2 = canvas2.getContext('2d');
var imgBaker = new Image();
imgBaker.crossOrigin = "Anonymous";

var imageSelect = document.querySelector('#imageSelect');
var bakerbtn = document.getElementById('bakerBtn');
bakerBtn.addEventListener('click', baker);
var remapbtn = document.getElementById('remapBtnBaker');
remapBtnBaker.addEventListener('click', reImage);
reImage();

function reImage() {
    var imgBaker = new Image();
    switch (Number(imageSelect.value)) {
        case 0:
            imgBaker.src = 'imagen.png';
            break;
        case 1:
            imgBaker.src = 'Ising-tartan.png';
            break;
        case 2:
            imgBaker.src = 'catmap.jpg';
            break;
    };
    imgBaker.onload = function() {
        draw(imgBaker);
        imgBaker.style.display = 'none';
    };
};

function draw(imgBaker) {
    ctxBak.clearRect(0, 0, canvasBak.width, canvasBak.height);
    ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
    ctxBak.drawImage(imgBaker, 0, 0, imgBaker.width, imgBaker.height, 0, 0, canvasBak.width, canvasBak.height);
    imgBaker.style.display = 'none';
    var imageData = ctxBak.getImageData(0, 0, canvasBak.width, canvasBak.height);
    var data = imageData.data;
};

function baker() {
    ctx2.drawImage(canvasBak,
        0, 0, Math.floor(canvasBak.width / 2), Math.floor(canvasBak.height),
        0, Math.floor(canvasBak.height / 2), Math.floor(canvasBak.width), Math.floor(canvasBak.height / 2));
    ctx2.drawImage(canvasBak,
        Math.floor(canvasBak.width / 2), 0, Math.floor(canvasBak.width / 2), Math.floor(canvasBak.height),
        0, 0, Math.floor(canvasBak.width), Math.floor(canvasBak.height / 2));
    ctxBak.drawImage(canvas2, 0, 0, canvas2.width, canvas2.height);
    ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
    // ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
};

function remap() {
    draw(imgBaker);
    imgBaker.style.display = 'none';
}

// var remap = function() {
//     var remapImageData = stretchctxBak.getImageData(0, 0, stretch.width, stretch.height);
//     var remapData = remapImageData.data;
//     for (var i = 0; i < data.length; i += 4) {
//         data[i] = remapData[i]; // red
//         data[i + 1] = remapData[i + 1]; // green
//         data[i + 2] = remapData[i + 2]; // blue
//     }
//     ctxBak.putImageData(imageData, 0, 0);
// };

// Para usar los botones
// var invertbtn = document.getElementById('invertbtn');
// invertbtn.addEventListener('click', invert);
// var grayscalebtn = document.getElementById('grayscalebtn');
// grayscalebtn.addEventListener('click', grayscale);
// iterabtn.addEventListener('click', remap);


// Codigo del zoom
// var smoothbtn = document.getElementById('smoothbtn');
// var toggleSmoothing = function(event) {
//     zoomctxBak.imageSmoothingEnabled = this.checked;
//     zoomctxBak.mozImageSmoothingEnabled = this.checked;
//     zoomctxBak.webkitImageSmoothingEnabled = this.checked;
//     zoomctxBak.msImageSmoothingEnabled = this.checked;
// };
// smoothbtn.addEventListener('change', toggleSmoothing);
//
// var zoom = function(event) {
//     var x = event.layerX;
//     var y = event.layerY;
//     zoomctxBak.drawImage(canvas,
//         Math.min(Math.max(0, x - 5), imgBaker.width - 10),
//         Math.min(Math.max(0, y - 5), imgBaker.height - 10),
//         10, 10,
//         0, 0,
//         200, 200);
// };
//
// canvasBak.addEventListener('mousemove', zoom);