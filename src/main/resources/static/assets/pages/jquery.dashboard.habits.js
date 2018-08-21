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
            addPlusClickEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addPlusClickEvents() {
        $("#habit-list button.btn-danger").change(function (el) {
                let id = el.target.id;
                console.log(id);
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
