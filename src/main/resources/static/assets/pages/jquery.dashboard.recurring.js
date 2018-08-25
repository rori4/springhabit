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
                Custombox.modal.close();
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
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addOnArchiveEvents() {
        $(".recurring-archive").click(function () {
            let id = $(this).closest('[recurring-id]').attr('recurring-id');
            swal({
                title: "Are you sure you want to archive this recurring task?",
                text: "You will archive this!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, archive it!",
                cancelButtonText: "No, cancel!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/recurring/archive?id=" + id,
                    }).done((data) => {
                        $('#recurring-' + id).fadeOut();
                        swal("Archived!", "Your recurring task has been archived", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your recurring task is safe :)", "error");
                }
            });
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
                    console.log(data);
                    stats.reloadStats();
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
