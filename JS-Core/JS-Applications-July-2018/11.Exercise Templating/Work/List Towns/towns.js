function attachEvents() {
    $('#btnLoadTowns').click(function () {
        let towns = $('#towns')
            .val()
            .split(', ')
            .map(e => ({name: e}));
        renderTowns(towns);
    });

    function renderTowns(towns) {
        let template = $('#towns-template').html;
        let compiled = Handlebars.compile(template);
        let remdered = compiled({
            towns: towns
        });
        $('#root').html(remdered);
    }
}