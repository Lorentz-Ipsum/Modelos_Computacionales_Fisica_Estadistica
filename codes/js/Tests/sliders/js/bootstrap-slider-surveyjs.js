Survey
    .StylesManager
    .applyTheme("stone");

var json = {
    elements: [{
        "type": "bootstrapslider",
        "name": "bootstrapslider-widget",
        "step": 50,
        "rangeMin": 100,
        "rangeMax": 1000
    }]
};

window.survey = new Survey.Model(json);

survey
    .onComplete
    .add(function(result) {
        document
            .querySelector('#surveyResult')
            .textContent = "Result JSON:\n" + JSON.stringify(result.data, null, 3);
    });

$("#surveyElement").Survey({
    model: survey
});