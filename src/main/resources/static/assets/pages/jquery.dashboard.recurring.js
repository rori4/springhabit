let recurring = (() => {

    function prepareModalAndLoad() {
        preloadActiveRecurringTasks();
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
                preloadActiveRecurringTasks();
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


    function preloadActiveRecurringTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/recurring?status=ACTIVE",
        }).done((data) => {
            $("#recurring-list").empty();
            $("#recurring-status").text('Active')
                .addClass('label-success')
                .removeClass('label-warning')
                .removeClass('label-info');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#recurring-template").html();
                let template = Handlebars.compile(source);
                $("#recurring-list").prepend(template(value)).fadeIn();
            });
            addOnCheckboxClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function preloadCompletedRecurringTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/recurring?status=DONE",
        }).done((data) => {
            $("#recurring-list").empty();
            $("#recurring-status").text('Completed')
                .addClass('label-info')
                .removeClass('label-warning')
                .removeClass('label-success');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#recurring-done-template").html();
                let template = Handlebars.compile(source);
                $("#recurring-list").prepend(template(value)).fadeIn();
            });
            addOnCheckboxClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function preloadArchivedRecurringTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/recurring?status=ARCHIVED",
        }).done((data) => {
            $("#recurring-list").empty();
            $("#recurring-status").text('Archived')
                .addClass('label-warning')
                .removeClass('label-success')
                .removeClass('label-info');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#recurring-archived-template").html();
                let template = Handlebars.compile(source);
                $("#recurring-list").prepend(template(value)).fadeIn();
            });
            addOnDeleteEvents();
            addActivateClickEvents();
        }).fail((err) => {
            console.log(err);
        });
    }
    
    function addOnDeleteEvents() {
        $(".recurring-delete").click(function () {
            let id = $(this).closest('[recurring-id]').attr('recurring-id');
            swal({
                title: "Are you sure you want to delete this recurring task?",
                text: "You will delete this!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                cancelButtonText: "No, cancel!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/recurring/delete?id=" + id,
                    }).done((data) => {
                        $('#recurring-' + id).fadeOut();
                        swal("Deleted!", "Your task has been deleted", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your to do task is safe :)", "error");
                }
            });
        });
    }

    function addActivateClickEvents() {
        $(".recurring-activate").click(function () {
            let id = $(this).closest('[recurring-id]').attr('recurring-id');
            swal({
                title: "Are you sure you want to activate this recurring task?",
                text: "You will activate this!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, activate it!",
                cancelButtonText: "No, cancel!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/recurring/activate?id=" + id,
                    }).done((data) => {
                        $('#recurring-' + id).fadeOut();
                        swal("Activated!", "Your task has been activated", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your to do task is still archived :)", "error");
                }
            });
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
            let id = el.target.id;
            if (this.checked) {
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
            } else {
                activateRecurringTask(id);
            }
        });
    }

    function activateRecurringTask(id) {
        $.ajax({
            type: 'POST',
            url: "/api/recurring/activate?id="+id,
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
        preloadActiveRecurringTasks,
        preloadCompletedRecurringTasks,
        preloadArchivedRecurringTasks,
        removeErrors,
    }

})();

recurring.prepareModalAndLoad();
