let recurring = (() => {


    function prependOnRecurringBoard() {
        let source = $("#recurring-template").html();
        let template = Handlebars.compile(source);
        let data = {
            title: "This is the first test habit",
            notes: "Lets test also to give a note here",
            resetPeriod: "WEEKLY"
        };
        $("#recurring-list").prepend(template(data)).fadeIn();
    }

    function removeErrors() {
        $(".parsley-errors-list").remove();
        $(".form-control").removeClass("parsley-error");
    }

    return {
        prependOnRecurringBoard,
        removeErrors,
    }

})();

recurring.prependOnRecurringBoard();
