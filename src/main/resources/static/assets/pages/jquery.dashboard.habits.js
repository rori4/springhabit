let habits = (() => {

    function prepareModalAndLoad() {
        preloadAllActiveHabits();
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
                Custombox.modal.close();
                $('.form-control').val('');
                preloadAllActiveHabits();
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

    function preloadAllActiveHabits() {
        $.ajax({
            type: 'GET',
            url: "/api/habit?status=ACTIVE",
        }).done((data) => {
            $("#habit-list").empty();
            $("#habit-status").text('Active').addClass('label-success').removeClass('label-warning');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#habit-template").html();
                let template = Handlebars.compile(source);
                $("#habit-list").prepend(template(value)).fadeIn();
            });
            addButtonsClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function preloadAllArchivedHabits() {
        $.ajax({
            type: 'GET',
            url: "/api/habit?status=ARCHIVED",
        }).done((data) => {
            $("#habit-list").empty();
            $("#habit-status").text('Archived').addClass('label-warning').removeClass('label-success');
            $.each(JSON.parse(data), function (index, value) {
                let source = $("#habit-template").html();
                let template = Handlebars.compile(source);
                $("#habit-list").prepend(template(value)).fadeIn();
            });
            addButtonsClickEvents();
            addOnArchiveEvents();
        }).fail((err) => {
            console.log(err);
        });
    }

    function addOnArchiveEvents() {
        $(".habit-archive").click(function () {
            let id = $(this).closest('[habit-id]').attr('habit-id');
            swal({
                title: "Are you sure you want to archive this habit?",
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
                        url: "/api/habit/archive?id=" + id,
                    }).done((data) => {
                        $('#habit-' + id).fadeOut();
                        swal("Archived!", "Your habit has been archived", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your habit is safe :)", "error");
                }
            });
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
                    console.log(data);
                    stats.reloadStats();
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
                    console.log(data);
                    stats.reloadStats();
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
        preloadAllActiveHabits,
        prependOnHabitBoard,
        removeErrors
    }

})();

habits.prepareModalAndLoad();
