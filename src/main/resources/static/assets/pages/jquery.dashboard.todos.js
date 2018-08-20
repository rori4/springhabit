$('#todo-submit').click(function (e) {
    e.preventDefault();
    let title = $('#todo-title').val();
    let notes = $('#todo-notes').val();
    let dueDate = $('#todo-due-date').val();
    $.ajax({
        type: 'POST',
        url: "/api/todo",
        data: {
            title: title,
            notes: notes,
            dueDate: dueDate
        }
    }).done((data) => {
        alert(data);
    }).fail((err) => {
        console.log(err);
    });
});