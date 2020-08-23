// Comportamiento al hacer  clic en el boton
var startMove = function() {
    // var winHeight = $(window).height(),
    //     winWidth = $(window).width(),
    //     newX = Math.random() * winHeight,
    //     newY = Math.random() * winWidth,
    //     r = radio.value;;
    //
    // console.log(newX + ", " + newY);
    //
    // $("#b").css({
    //     "position": "fixed",
    //     "width": r,
    //     "height": r,
    //     "border-radius": r / 2,
    //     "left": newX,
    //     "top": newY,
    //     "opacity": 100
    // });
};

$(document).ready(function() {

    $("#clickable").click(function(event) {
        var r = radio.value;
        var x = event.clientX - r / 2;
        var y = event.clientY - r / 2;
        // console.log(x + ", " + y);
        $("#d").css({
            "position": "fixed",
            "width": r,
            "height": r,
            "border-radius": r / 2,
            "left": x,
            "top": y,
            "opacity": 100
        });
        $("#d").animate({
            opacity: '0'
        }, 500);
    });

    $("button").click(function(event) {
        var winHeight = $(window).height(),
            winWidth = $(window).width(),
            r = radio.value,
            vel = velocidad.value;

        $("#b").css({
            "position": "fixed",
            "width": r,
            "height": r,
            "border-radius": r / 2,
            "left": oldX,
            "top": oldY,
            "opacity": 100
        });
        for (var i = 0; i < 10; i++) {
            var newX = Math.floor(Math.random() * winHeight),
                newY = Math.floor(Math.random() * winWidth);

            $("#b").animate({
                "left": newX,
                "top": newY
            }, vel * 100);
            var oldX = newX,
                oldY = newY;
        }

    });
    // document.getElementById("start").onclick = startMove;
});

$(document).on('input', 'input[type="range"]', function(e) {
    document.querySelector('output.' + this.id).innerHTML = e.target.value;
});