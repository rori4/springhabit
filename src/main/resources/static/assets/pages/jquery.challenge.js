let challenge = (() => {
    function addOnChallengeClickEvent() {
        $(".challenge").click(function () {
            let id = $(this).closest('[id]').attr('id');
            swal({
                title: "Are you sure you want to challenge this opponent?",
                text: "The more challengers you have the more damage you will take and when you die you will lose half of your money",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, challenge him!",
                cancelButtonText: "No, I am scared!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/challenge?id=" + id,
                    }).done((data) => {
                        console.log(data);
                        swal("Challenged!", "Your opponent has been challenged. Let's wait to see if he will accept your challenge or is he cared 😱", "success");
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
    return {
        addOnChallengeClickEvent
    }
})();

challenge.addOnChallengeClickEvent();
