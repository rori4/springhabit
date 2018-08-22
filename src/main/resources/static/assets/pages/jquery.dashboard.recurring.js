let recurring = (() => {

    function prepareModalAndLoad() {
        preloadAllRecurringTaks();
        $('#recurring-submit').click(function (e) {
            let prefix = "recurring-";
            e.preventDefault();
            let title = $('#recurring-title').val();
            let notes = $('#recurring-notes').val();
            let resetPeriod = $('#recurring-resetPeriod').val();
            $.ajax({
                type: 'POST',
                url: "/api/recurring",
                data: {
                    title: title,
                    notes: notes,
                    resetPeriod: resetPeriod
                }
            }).done((data) => {
                removeErrors();
                Custombox.close();
                $('.form-control').val('');
                preloadAllRecurringTaks();
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


    function preloadAllRecurringTaks() {
        $.ajax({
            type: 'GET',
            url: "/api/recurring",
        }).done((data) => {
            $("#recurring-list").empty();
            $.each(JSON.parse(data), function (index, value) {
                prependOnToDoBoard(value)
            });
            addOnCheckboxClickEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addOnCheckboxClickEvents() {
        $("#recurring-list input[type=checkbox]").change(function (el) {
            if (this.checked) {
                let id = el.target.id;
                $.ajax({
                    type: 'POST',
                    url: "/api/recurring/done?id="+id,
                }).done((data) => {
                    $('#recurring-'+id).fadeOut();
                    noty.handleData(data);
                    console.log(data)
                }).fail((err) => {
                    //Add notify in corner
                    console.log(err);
                });
            }
        });
    }

    function prependOnToDoBoard(data) {
        let source = $("#recurring-template").html();
        let template = Handlebars.compile(source);
        $("#recurring-list").prepend(template(data)).fadeIn();
    }

    function removeErrors() {
        $(".parsley-errors-list").remove();
        $(".form-control").removeClass("parsley-error");
    }

    return {
        prepareModalAndLoad,
        preloadAllRecurringTaks,
        removeErrors,
    }

})();

recurring.prepareModalAndLoad();
