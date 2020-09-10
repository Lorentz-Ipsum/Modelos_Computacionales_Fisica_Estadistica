var canvasBak = document.getElementById('canvasBaker');
var ctxBak = canvasBak.getContext('2d');
var canvas2 = document.getElementById('canvasBaker2');
var ctx2 = canvas2.getContext('2d');
var img = new Image();
img.crossOrigin = "Anonymous";

var imageSelect = document.querySelector('#imageSelect');

function reImage() {
    switch (Number(imageSelect.value)) {
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
    img.onload = function() {
        draw(img);
    };
};
reImage();

function draw(img) {
    ctxBak.clearRect(0, 0, canvasBak.width, canvasBak.height);
    ctx2.clearRect(0, 0, canvas2.width, canvas2.height);
    ctxBak.drawImage(img, 0, 0, img.width, img.height, 0, 0, canvasBak.width, canvasBak.height);
    img.style.display = 'none';
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
    draw(img);
    img.style.display = 'none';
}
var bakerbtn = document.getElementById('bakerBtn');
bakerBtn.addEventListener('click', baker);
var remapbtn = document.getElementById('remapBtnBaker');
remapBtnBaker.addEventListener('click', remap);

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
//         Math.min(Math.max(0, x - 5), img.width - 10),
//         Math.min(Math.max(0, y - 5), img.height - 10),
//         10, 10,
//         0, 0,
//         200, 200);
// };
//
// canvasBak.addEventListener('mousemove', zoom);