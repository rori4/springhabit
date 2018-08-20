$('#todo-submit').click(function (e) {
    let title = $('#todo-title').val();
    let note = $('#model').val();
    let dueDate = $('#image').val();

    $.ajax({
        type: 'POST',
        url: 'http://127.0.0.1:8000/api/todo/add',
        data: {
            title: title,
            note: note,
            dueDate: dueDate
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseText);
            alert(thrownError);
        }
    }).done((data) => {
        console.log(data);
    }).fail((err) => {
        console.log(err);
    });
});