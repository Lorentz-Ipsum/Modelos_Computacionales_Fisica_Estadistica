document.addEventListener("DOMContentLoaded", function() {
  function Model(canvasElement) {
    this.canvas = canvasElement;
    this.context = this.canvas.getContext('2d');
    this.image = this.context.createImageData(this.canvas.width, this.canvas.height);
    this.upRGB = JSON.parse(this.canvas.dataset.up);
    this.downRGB = JSON.parse(this.canvas.dataset.down);
    this.size = 100; // lattice is 100 x 100
    this.sizeMax = this.canvas.width;
    this.step = 0;
    this.xMax = this.size;
    this.yMax = this.size;
    this.sqWidth = this.canvas.width / this.size;

    this.j = 1;
    this.k = 1;
    this.temp = 2.269;
    this.stepsPerFrame = 100;

    this.running = false;
  }

  Model.prototype = {
    constructor: Model,

    wrapCanvas: function() {
      this.wrapper = document.createElement('div');
      this.wrapper.classList.add('ising-model');

      this.canvas.insertAdjacentElement('beforebegin', this.wrapper);
      this.wrapper.appendChild(this.canvas);

      this.addButtons();
    },

    addButtons: function() {
      this.buttons = document.createElement('div');
      this.buttons.classList.add('buttons')
      this.wrapper.appendChild(this.buttons);

      var buttonGroup = document.createElement('div');
      buttonGroup.classList.add('button-group');

      buttonGroup = buttonGroup.cloneNode();
      this.playPause = document.createElement('button');
      this.playPause.setAttribute('type', 'button');
      this.playPause.setAttribute('name', 'playPause');
      this.playPause.setAttribute('class', 'playPause');
      this.playPause.innerHTML = 'Start';
      buttonGroup.appendChild(this.playPause)

      this.resetButton = document.createElement('button');
      this.resetButton.setAttribute('id', 'reset');
      this.resetButton.innerHTML = "Reset";
      buttonGroup.appendChild(this.resetButton);
      this.buttons.appendChild(buttonGroup);

      buttonGroup = buttonGroup.cloneNode();
      var tempLabel = document.createElement('label');
      tempLabel.setAttribute('for', 'tempReadout');
      tempLabel.innerHTML = "Temperature";
      buttonGroup.appendChild(tempLabel);

      this.tempReadout = document.createElement('input');
      this.tempReadout.setAttribute('type', 'number');
      this.tempReadout.setAttribute('min', '0.01');
      this.tempReadout.setAttribute('max', '10');
      this.tempReadout.setAttribute('step', '0.001');
      this.tempReadout.value = this.temp;
      buttonGroup.appendChild(this.tempReadout)

      this.tempSlider = this.tempReadout.cloneNode();
      this.tempSlider.setAttribute('type', 'range');
      this.tempSlider.setAttribute('id', 'tempSlider');
      buttonGroup.appendChild(this.tempSlider);
      this.buttons.appendChild(buttonGroup);

      buttonGroup = buttonGroup.cloneNode();
      var jLabel = document.createElement('label');
      jLabel.setAttribute('for', 'jSelect');
      jLabel.innerHTML = "J";
      buttonGroup.appendChild(jLabel);

      this.jSelect = document.createElement('select');
      this.jSelect.setAttribute('id', 'jSelect');

      [1, -1].forEach(function(j) {
        var option = document.createElement('option');
        option.value = j;
        option.innerHTML = option.value;
        if (j === this.j) {
          option.setAttribute('selected', true);
        }
        this.jSelect.appendChild(option);
      }.bind(this));

      buttonGroup.appendChild(this.jSelect);

      this.buttons.appendChild(buttonGroup);

      buttonGroup = buttonGroup.cloneNode();
      var sizeLabel = document.createElement('label');
      sizeLabel.setAttribute('for', 'sizeSelect');
      sizeLabel.innerHTML = "Size";
      buttonGroup.appendChild(sizeLabel);

      this.sizeSelect = document.createElement('select');
      this.sizeSelect.setAttribute('id', 'sizeSelect');

      var sizes = [];
      for (var i = 1; i <= 30; i++) {
        if (this.sizeMax % i === 0) {
          sizes.unshift(this.sizeMax / i);
        }
      }

      sizes.forEach(function(size, index) {
        var option = document.createElement('option');
        option.value = size;
        option.innerHTML = option.value;
        if (index === Math.floor(sizes.length / 2)) {
          option.setAttribute('selected', true);
          // this.size = size;
          this.setSize();
        }
        this.sizeSelect.appendChild(option);
      }.bind(this));

      buttonGroup.appendChild(this.sizeSelect);
      this.buttons.appendChild(buttonGroup);

      buttonGroup = buttonGroup.cloneNode();
      var stepsPerFrameLabel = document.createElement('label');
      stepsPerFrameLabel.setAttribute('for', 'stepsPerFrameSelect');
      stepsPerFrameLabel.innerHTML = "Steps per Frame";
      buttonGroup.appendChild(stepsPerFrameLabel);

      this.stepsPerFrameSelect = document.createElement('select');
      this.stepsPerFrameSelect.setAttribute('id', 'stepsPerFrameSelect');

      [1, 2, 5, 20, 50, 100, 1000, 10000, 100000, 1000000].forEach(function(stepsPerFrame) {
        var option = document.createElement('option');
        option.value = stepsPerFrame;
        option.innerHTML = option.value;
        if (stepsPerFrame === this.stepsPerFrame) {
          option.setAttribute('selected', true);
        }
        this.stepsPerFrameSelect.appendChild(option);
      }.bind(this));

      buttonGroup.appendChild(this.stepsPerFrameSelect);
      this.buttons.appendChild(buttonGroup);

      buttonGroup = buttonGroup.cloneNode();
      this.specificHeat = document.createElement('div');
      this.specificHeat.innerHTML = 'Specific Heat (C<sub>v</sub>)';
      buttonGroup.appendChild(this.specificHeat);

      this.specificHeatValue = document.createElement('span');
      this.specificHeatValue.innerHTML = '';
      this.specificHeat.appendChild(this.specificHeatValue);
      this.buttons.appendChild(buttonGroup);

    },

    makeOpaque: function() {
      for (var i = 0; i < this.image.data.length; i += 4) {
        this.image.data[i + 3] = 255;
      }
      this.context.putImageData(this.image, 0, 0);
    },

    initDipoles: function() {
      var size = this.size;
      this.dipoles = new Array(size);
      for (var y = 0; y < size; y++) {
        this.dipoles[y] = new Array(size);
        for (var x = 0; x < size; x++) {
          if (Math.random() < 0.5) {
            this.dipoles[y][x] = 1;
          } else {
            this.dipoles[y][x] = -1;
          }
        }
      }
    },

    initImage: function() {
      for (var y = 0; y < this.size; y++) {
        for (var x = 0; x < this.size; x++) {

          this.paintSquare([x, y]);
        }
      }
      this.context.putImageData(this.image, 0, 0);
    },

    randomSite: function() {
      var x = Math.floor(Math.random() * this.xMax);
      var y = Math.floor(Math.random() * this.yMax);

      return [x, y];
    },

    resize: function() {
      var upSample = this.upSample();
      this.setSize();
      this.downSample(upSample, this.size);
    },

    upSample: function() {
      var tempDipoles = new Array(this.sizeMax);
      for (var y = 0; y < this.sizeMax; y++) {
        tempDipoles[y] = new Array(this.sizeMax);
        for (var x = 0; x < this.sizeMax; x++) {
          var yOld = Math.floor(y / this.sqWidth);
          var xOld = Math.floor(x / this.sqWidth);
          tempDipoles[y][x] = this.dipoles[yOld][xOld];
        }
      }
      return tempDipoles;
    },

    downSample: function(upSample, size) {
      this.dipoles = new Array(size);
      for (var yNew = 0; yNew < size; yNew++) {
        this.dipoles[yNew] = new Array(size);
        for (var xNew = 0; xNew < size; xNew++) {
          var site = [xNew, yNew];
          var dipoleTotal = this.downSampleTotal(upSample, site);

          this.setSite(site, dipoleTotal);
          this.paintSquare(site);
        }
      }
    },

    downSampleTotal: function(array, siteNew) {
      var total = 0;
      var xNew = siteNew[0];
      var yNew = siteNew[1];
      for (var y = yNew * this.sqWidth; y < (yNew + 1) * this.sqWidth; y++) {
        for (var x = xNew * this.sqWidth; x < (xNew + 1) * this.sqWidth; x++) {
          total += array[y][x];
        }
      }
      return total;
    },

    setSite: function(site, dipoleTotal) {
      var xNew = site[0];
      var yNew = site[1];

      if (dipoleTotal > 0) {
        this.dipoles[yNew][xNew] = 1;
      } else if (dipoleTotal < 0) {
        this.dipoles[yNew][xNew] = -1;
      } else {
        if (Math.random() < 0.5) {
          this.dipoles[yNew][xNew] = 1;
        } else {
          this.dipoles[yNew][xNew] = -1;
        }
      }
    },

    setSize: function() {
      this.size = Number(this.sizeSelect.value);
      this.sqWidth = this.canvas.width / this.size;
      this.xMax = this.size;
      this.yMax = this.size;
    },

    energy: function(site) {
      var x = site[0];
      var y = site[1];
      // Periodic Boundary Conditions
      var leftS, rightS, topS, bottomS;
      if (y === 0) {
        leftS = this.dipoles[this.yMax - 1][x];
      } else {
        leftS = this.dipoles[y - 1][x];
      }

      if (y === this.yMax -1) {
        rightS = this.dipoles[0][x];
      } else {
        rightS = this.dipoles[y + 1][x];
      }

      if (x === 0) {
        topS = this.dipoles[y][this.xMax - 1];
      } else {
        topS = this.dipoles[y][x - 1];
      }

      if (x === this.xMax - 1) {
        bottomS = this.dipoles[y][0];
      } else {
        bottomS = this.dipoles[y][x + 1];
      }

      // Energy per spin converges to U/N = -2J
      // in the limit that size => Infinity
      // var u = 2 * this.dipoles[y][x] * (leftS + rightS + topS + bottomS);
      var e = -this.j * this.dipoles[y][x] * (leftS + rightS + topS + bottomS);
      return e;
    },

    paintSquare: function(site) {
      var x = site[0];
      var y = site[1];
      var r, g, b;
      var colors;
      if (this.dipoles[y][x] === 1) {
        colors = this.upRGB;
      } else {
        colors = this.downRGB;
      }

      // Many pixels are part of square
      for (var px = x * this.sqWidth; px < (x + 1) * this.sqWidth; px++) {
        for (var py = y * this.sqWidth; py < (y + 1) * this.sqWidth; py++) {
          var index = (py + px * this.image.width) * 4;
          this.image.data[index] = colors.r;
          this.image.data[index + 1] = colors.g;
          this.image.data[index + 2] = colors.b;
        }
      }
    },

    calcCv: function() {
      // Formula: Cv = Cv = 1/kT^2 * [<E^2> - <E>^2]
      var t = this.temp;
      var k = this.k;
      var eTotal = 0;
      var e2Total = 0;
      var e2Avg;
      var avgE2;
      var energy;

      for (var y = 0; y < this.size; y++) {
        for (var x = 0; x < this.size; x++) {
          energy = this.energy([x, y]);
          eTotal += energy;
          e2Total += energy * energy;
        }
      };


      e2Avg = e2Total / (this.size * this.size);
      aveE2 = Math.pow(eTotal / (this.size * this.size), 2);

      var Cv = (1 / (k * t * t)) * (e2Avg - aveE2);
      return Cv.toFixed(3);

    },

    simulate: function() {
      var step, site;
      if (this.running) {
        for (step = 0; step < this.stepsPerFrame; step++) {
          this.step++
          site = this.randomSite();
          var deltaE = -2 * this.energy(site);

          if ((deltaE <= 0) || (Math.random() < Math.exp(-deltaE / this.temp))) {
            // Metropolis Algorithm
            this.dipoles[site[1]][site[0]] *= -1 // flip spin
            this.paintSquare(site);
          }

          if (this.step % (this.stepsPerFrame * 10) === 0) {
            this.specificHeatValue.innerHTML = this.calcCv();
          }
        }
        this.context.putImageData(this.image, 0, 0);


        setTimeout(this.simulate.bind(this), 1);
      }

    },

    updateTempFromSlider: function(e) {
      this.tempReadout.value = this.tempSlider.value;
      this.temp = this.tempSlider.value;
    },

    updateTempFromReadout: function(e) {
      if (this.tempReadout.value > 10) {
        this.tempReadout.value = 10;
      }
      this.tempSlider.value = this.tempReadout.value;
      this.temp = this.tempReadout.value;
    },

    updateSize: function(e) {
      this.size = this.sizeSelect.value;
      this.xMax = this.size;
      this.yMax = this.size;
      this.sqWidth = this.canvas.width / this.size;
      this.initDipoles();
      this.initImage();
    },

    updateStepsPerFrame: function(e) {
      this.stepsPerFrame = Number(this.stepsPerFrameSelect.value);
    },

    updateJ: function(e) {
      this.j = Number(this.jSelect.value);
    },

    startStop: function(e) {
      this.running = !this.running;
      if (this.running) {
        this.playPause.innerHTML = "Stop";
      } else {
        this.playPause.innerHTML = "Start";
      }
      this.simulate();
    },

    reset: function(e) {
      this.running = false;
      this.playPause.innerHTML = "Start";
      this.initDipoles();
      this.initImage();
    },

    bindEvents: function() {
      this.playPause.addEventListener('click', this.startStop.bind(this));
      this.tempSlider.addEventListener('change', this.updateTempFromSlider.bind(this));
      this.tempReadout.addEventListener('input', this.updateTempFromReadout.bind(this));
      this.resetButton.addEventListener('click', this.reset.bind(this));
      this.sizeSelect.addEventListener('change', this.resize.bind(this));
      this.stepsPerFrameSelect.addEventListener('change', this.updateStepsPerFrame.bind(this));
      this.jSelect.addEventListener('change', this.updateJ.bind(this));
    },

    init: function() {
      this.wrapCanvas();
      this.makeOpaque();
      this.initDipoles();
      this.initImage();
      this.bindEvents();
      this.simulate();
    }
  };

  var model = new Model(document.getElementById('canvas'));
  model.init();

  var model2 = new Model(document.getElementById('canvas2'));
  model2.init();
});