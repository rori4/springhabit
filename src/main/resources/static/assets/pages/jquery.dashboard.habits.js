let habits = (() => {

    function prepareModalAndLoad() {
        preloadAllHabitTaks();
        $('#habit-submit').click(function (e) {
            let prefix = "habit-";
            e.preventDefault();
            let title = $('#habit-title').val();
            let notes = $('#habit-notes').val();

            $.ajax({
                type: 'POST',
                url: "/api/habit",
                data: {
                    title: title,
                    notes: notes
                }
            }).done((data) => {
                console.log(data);
                removeErrors();
                Custombox.close();
                $('.form-control').val('');
                preloadAllHabitTaks();
            }).fail((err) => {
                removeErrors();
                let source = $("#error-field").html();
                let template = Handlebars.compile(source);
                let errors = err.responseJSON.errors;
                $.each(errors, function (index, value) {
                    let field = $("#" + prefix + value.field);
                    field.addClass('parsley-error');
                    field.after(template(value));
                });
                console.log(err);
            });
        });
    }

    function preloadAllHabitTaks() {
        $.ajax({
            type: 'GET',
            url: "/api/habit",
        }).done((data) => {
            $("#habit-list").empty();
            $.each(JSON.parse(data), function (index, value) {
                prependOnHabitBoard(value);
            });
            addButtonsClickEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addButtonsClickEvents() {
        $("#habit-list button").click(function (el) {
            let id = $(el.target).parent().attr('id');
            if($(el.target).hasClass('btn-danger')){
                console.log("clicked on minus");
                console.log(id);
                $.ajax({
                    type: 'POST',
                    url: "/api/habit/minus?id="+id,
                }).done((data) => {
                    noty.handleData(data);
                    console.log(data)
                }).fail((err) => {
                    //Add notify in corner
                    console.log(err);
                });
            } else if($(el.target).hasClass('btn-success')){
                console.log("clicked on plus");
                console.log(id);
                $.ajax({
                    type: 'POST',
                    url: "/api/habit/plus?id="+id,
                }).done((data) => {
                    noty.handleData(data);
                    console.log(data)
                }).fail((err) => {
                    //Add notify in corner
                    console.log(err);
                });
            }
        });
    }

    function prependOnHabitBoard(data) {
        let source = $("#habit-template").html();
        let template = Handlebars.compile(source);
        $("#habit-list").prepend(template(data)).fadeIn();
    }

    function removeErrors() {
        $(".parsley-errors-list").remove();
        $(".form-control").removeClass("parsley-error");
    }

    return {
        prepareModalAndLoad,
        prependOnHabitBoard,
        removeErrors
    }

})();

habits.prepareModalAndLoad();
