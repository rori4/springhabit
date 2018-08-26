let todo = (() => {

    function prepareModalAndLoad() {
        preloadActiveToDoTasks();
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
                Custombox.modal.close();
                $('.form-control').val('');
                preloadActiveToDoTasks();
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

    function openTaks(el) {
        let id = $(el.closest('[todo-id]')).attr('todo-id');
    }

    function preloadActiveToDoTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/todo?status=ACTIVE",
        }).done((data) => {
            $("#todo-list").empty();
            $("#todo-status").text('Active')
                .addClass('label-success')
                .removeClass('label-warning')
                .removeClass('label-info');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#todo-template").html();
                let template = Handlebars.compile(source);
                $("#todo-list").prepend(template(value));
            });
            addOnCheckboxClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function preloadCompletedToDoTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/todo?status=DONE",
        }).done((data) => {
            $("#todo-list").empty();
            $("#todo-status").text('Completed')
                .addClass('label-info')
                .removeClass('label-warning')
                .removeClass('label-success');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#todo-done-template").html();
                let template = Handlebars.compile(source);
                $("#todo-list").prepend(template(value));
            });
            addOnCheckboxClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function preloadArchivedToDoTasks() {
        $.ajax({
            type: 'GET',
            url: "/api/todo?status=ARCHIVED",
        }).done((data) => {
            $("#todo-list").empty();
            $("#todo-status").text('Archived')
                .addClass('label-warning')
                .removeClass('label-success')
                .removeClass('label-info');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#todo-archived-template").html();
                let template = Handlebars.compile(source);
                $("#todo-list").prepend(template(value));
            });
            addOnDeleteEvents();
            addActivateFromArchivedEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addOnCheckboxClickEvents() {
        $("#todo-list input[type=checkbox]").change(function (el) {
            let id = el.target.id;
            if (this.checked) {
                $.ajax({
                    type: 'POST',
                    url: "/api/todo/done?id=" + id,
                }).done((data) => {
                    $('#todo-' + id).fadeOut();
                    noty.handleData(data);
                    console.log(data);
                    stats.reloadStats();
                }).fail((err) => {
                    //Add notify in corner
                    console.log(err);
                });
            } else {
                activateToDoTask(id);
            }
        });
    }

    function addActivateFromArchivedEvents(){
        $(".todo-activate").click(function () {
            let id = $(this).closest('[todo-id]').attr('todo-id');
            swal({
                title: "Are you sure you want to activate this to do?",
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
                        url: "/api/todo/activate?id=" + id,
                    }).done((data) => {
                        $('#todo-' + id).fadeOut();
                        swal("Activated!", "Your task has been activate", "success");
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

    function activateToDoTask(id) {
        $.ajax({
            type: 'POST',
            url: "/api/todo/activate?id=" + id,
        }).done((data) => {
            $('#todo-' + id).fadeOut();
            noty.handleData(data);
            console.log(data);
            stats.reloadStats();
        }).fail((err) => {
            //Add notify in corner
            console.log(err);
        });
    }

    function addOnDeleteEvents() {
        $(".todo-delete").click(function () {
            let id = $(this).closest('[todo-id]').attr('todo-id');
            swal({
                title: "Are you sure you want to delete this to do?",
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
                        url: "/api/todo/archive?id=" + id,
                    }).done((data) => {
                        $('#todo-' + id).fadeOut();
                        swal("Archived!", "Your task has been deleted", "success");
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

    function addOnArchiveEvents() {
    $(".todo-archive").click(function () {
            let id = $(this).closest('[todo-id]').attr('todo-id');
            swal({
                title: "Are you sure you want to archive this to do?",
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
                        url: "/api/todo/archive?id=" + id,
                    }).done((data) => {
                        $('#todo-' + id).fadeOut();
                        swal("Archived!", "Your task has been archived", "success");
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
        preloadActiveToDoTasks,
        preloadArchivedToDoTasks,
        preloadCompletedToDoTasks,
        removeErrors,
        openTaks
    }

})();

todo.prepareModalAndLoad();
