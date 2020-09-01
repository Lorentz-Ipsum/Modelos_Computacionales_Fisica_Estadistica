// And here's the JavaScript to bring it to life...

// Global variables:
var canvas = document.getElementById('theCanvas');
var context = canvas.getContext('2d');
var startButton = document.getElementById('startButton');
var tempSlider = document.getElementById('tempSlider');
var tempReadout = document.getElementById('tempReadout');
var sizeSelect = document.getElementById('sizeSelect');
var speedReadout = document.getElementById('speedReadout');
var spfSlider = document.getElementById('spfSlider');
var spfReadout = document.getElementById('spfReadout');
var pixelCheck = document.getElementById('pixelCheck');
var mobile = navigator.userAgent.match(/iPhone|iPad|iPod|Android|BlackBerry|Opera Mini|IEMobile/i)
var image = context.createImageData(canvas.width, canvas.height); // for direct pixel manipulation (faster than fillRect)
for (var i = 0; i < image.data.length; i += 4) {
    //image.data[i] = 0;					// set red to zero (not needed when set in colorSquare)
    //image.data[i+1] = 0;					// set green to zero
    //image.data[i+2] = 255;				// set blue to max
    image.data[i + 3] = 255; // set all alpha values to opaque
}

var maxSize = canvas.width;
sizeSelect.selectedIndex = 2;
if (mobile) sizeSelect.selectedIndex = 1; // smaller works better on mobile platforms
var size = Number(sizeSelect.options[sizeSelect.selectedIndex].text);
var squareWidth = canvas.width / size; // width of each lattice site in pixels

var stepsPerFrame;
if (mobile) spfSlider.value = 9;
else spfSlider.value = 12; // optimize a bit for mobile
adjustSPF();

var running = false; // will be true when running
var pixelGraphics = false;
var stepCount = 0;
var startTime = 0;

// Create the 2D array of dipoles, initially random (1 for up, -1 for down):
var s = new Array(size);
for (var i = 0; i < size; i++) {
    s[i] = new Array(size); // a 2D array is just an array of arrays
    for (var j = 0; j < size; j++) {
        if (Math.random() < 0.5) {
            s[i][j] = 1;
        } else {
            s[i][j] = -1;
        }
        colorSquare(i, j);
    }
}

simulate(); // let 'er rip!

// Function to carry out the simulation:
function simulate() {
    if (running) {
        var T = Number(tempSlider.value);
        // Execute a bunch of Monte Carlo steps:
        for (var step = 0; step < stepsPerFrame; step++) {
            var i = Math.floor(Math.random() * size); // choose a random site...
            var j = Math.floor(Math.random() * size);
            var eDiff = deltaU(i, j);
            if ((eDiff <= 0) || (Math.random() < Math.exp(-eDiff / T))) { // Metropolis algorithm
                s[i][j] *= -1;
                colorSquare(i, j);
            }
        }
        if (pixelGraphics) context.putImageData(image, 0, 0); // blast the image to the screen
        stepCount += stepsPerFrame;
        var elapsedTime = ((new Date()).getTime() - startTime) / 1000; // time in seconds
        speedReadout.innerHTML = Number(stepCount / elapsedTime).toFixed(0);
    }
    window.setTimeout(simulate, 1); // come back in 1 ms
}

// Given a lattice site, compute energy change from hypothetical flip; note pbc:
function deltaU(i, j) {
    var leftS, rightS, topS, bottomS; // values of neighboring spins
    if (i == 0) leftS = s[size - 1][j];
    else leftS = s[i - 1][j];
    if (i == size - 1) rightS = s[0][j];
    else rightS = s[i + 1][j];
    if (j == 0) topS = s[i][size - 1];
    else topS = s[i][j - 1];
    if (j == size - 1) bottomS = s[i][0];
    else bottomS = s[i][j + 1];
    return 2.0 * s[i][j] * (leftS + rightS + topS + bottomS);
}

// Color a given square according to its alignment:
function colorSquare(i, j) {
    if (pixelGraphics) { // faster method, at least when squares are small
        var r, g, b;
        if (s[i][j] == 1) {
            r = 0;
            g = 0;
            b = 255; // blue
        } else {
            r = 255;
            g = 255;
            b = 0; // yellow
        }
        // Loop over all pixels that are part of the square:
        for (py = j * squareWidth; py < (j + 1) * squareWidth; py++) {
            for (px = i * squareWidth; px < (i + 1) * squareWidth; px++) {
                var index = (px + py * image.width) * 4;
                image.data[index + 0] = r;
                image.data[index + 1] = g;
                image.data[index + 2] = b; // (doesn't affect screen until we call putImageData)
            }
        }
    } else { // easier but slower graphics method
        if (s[i][j] == 1) {
            context.fillStyle = '#0000ff'; // blue
        } else {
            context.fillStyle = '#ffff00'; // yellow
        }
        context.fillRect(i * squareWidth, j * squareWidth, squareWidth, squareWidth);
    }
}

// Function to start or pause the simulation:
function startStop() {
    running = !running;
    if (running) {
        startButton.value = " Pause ";
        reset();
    } else {
        startButton.value = "Resume";
    }
}

// Function to update the temperature readout:
function showTemp() {
    tempReadout.value = Number(tempSlider.value).toFixed(2);
}

// Resize the lattice (block-spin transformaiton):
function resize() {
    // First up-sample the lattice into a temporary array at max resolution:
    var tempS = new Array(maxSize);
    for (var i = 0; i < maxSize; i++) {
        tempS[i] = new Array(maxSize);
        for (var j = 0; j < maxSize; j++) {
            var iOld = Math.floor(i / squareWidth);
            var jOld = Math.floor(j / squareWidth);
            tempS[i][j] = s[iOld][jOld];
        }
    }
    // Get new size from GUI selector:
    size = Number(sizeSelect.options[sizeSelect.selectedIndex].text);
    squareWidth = canvas.width / size;
    // Now re-create the spin array, down-sampling from temporary array
    s = new Array(size);
    for (var iNew = 0; iNew < size; iNew++) {
        s[iNew] = new Array(size);
        for (var jNew = 0; jNew < size; jNew++) {
            var sTotal = 0;
            for (var i = iNew * squareWidth; i < (iNew + 1) * squareWidth; i++) {
                for (var j = jNew * squareWidth; j < (jNew + 1) * squareWidth; j++) {
                    sTotal += tempS[i][j];
                }
            }
            if (sTotal > 0) {
                s[iNew][jNew] = 1; // average determines down-sampled value
            } else if (sTotal < 0) {
                s[iNew][jNew] = -1;
            } else { // resolve ties with a random number
                if (Math.random() < 0.5) s[iNew][jNew] = 1;
                else s[iNew][jNew] = -1;
            }
            colorSquare(iNew, jNew);
        }
    }
    reset();
}

// Function to adjust the number of steps per frame according to slider position:
// Slider values 0 through 18 correspond to 1, 2, 5, 10, 20, 50, 100, 200, 500, ... 1000000.
function adjustSPF() {
    var sliderPos = Number(spfSlider.value);
    if (sliderPos > 18) sliderPos = 18;
    if (sliderPos < 0) sliderPos = 0;
    var spf = Math.pow(10, Math.floor(sliderPos / 3));
    if (sliderPos % 3 == 1) {
        spf *= 2;
    } else if (sliderPos % 3 == 2) {
        spf *= 5;
    }
    stepsPerFrame = spf;
    spfReadout.value = spf;
    reset();
}

// Function to select the graphics mode, based on checkbox state:
function selectGraphicsMode() {
    pixelGraphics = pixelCheck.checked;
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            colorSquare(i, j);
        }
    }
    reset();
}

// Function to reset the step count and start time, for accurate benchmarking:
function reset() {
    stepCount = 0;
    startTime = (new Date()).getTime();
}