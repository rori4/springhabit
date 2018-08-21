let todo = (() => {
    function prepareModalAndLoad() {
        preloadAllToDoTaks();
        $('#todo-submit').click(function (e) {
            let prefix = "todo-";
            e.preventDefault();
            let title = $('#todo-title').val();
            let notes = $('#todo-notes').val();
            let dueDate = $('#todo-dueDate').val();
            $.ajax({
                type: 'POST',
                url: "/api/todo",
                data: {
                    title: title,
                    notes: notes,
                    dueDate: dueDate
                }
            }).done((data) => {
                removeErrors();
                Custombox.close();
                $('.form-control').val('');
                preloadAllToDoTaks();
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


    function preloadAllToDoTaks() {
        $.ajax({
            type: 'GET',
            url: "/api/todo",
        }).done((data) => {
            $("#todo-list").empty();
            $.each(JSON.parse(data), function (index, value) {
                prependOnToDoBoard(value)
            });
            addOnCheckboxClickEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addOnCheckboxClickEvents() {
        $("#todo-list input[type=checkbox]").change(function (el) {
            if (this.checked) {
                let id = el.target.id;
                $.ajax({
                    type: 'POST',
                    url: "/api/todo/done?id="+id,
                }).done((data) => {
                    $('#todo-'+id).fadeOut();
                    console.log(data)
                }).fail((err) => {
                    //Add notify in corner
                    console.log(err);
                });
            }
        });
    }

    function prependOnToDoBoard(data) {
        let source = $("#todo-template").html();
        let template = Handlebars.compile(source);
        $("#todo-list").prepend(template(data)).fadeIn();
    }

    function removeErrors() {
        $(".parsley-errors-list").remove();
        $(".form-control").removeClass("parsley-error");
    }

    return {
        prepareModalAndLoad,
        preloadAllToDoTaks,
        removeErrors,
    }

})();

todo.prepareModalAndLoad();
