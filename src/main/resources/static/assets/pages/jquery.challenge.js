let challenge = (() => {
    function addOnChallengeClickEvent() {
        $(document).on('click', '.challenge',function () {
            let id = $(this).attr('usr-id');
            let row = $(this).closest('tr');
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
                        url: "/api/challenge/send?id=" + id,
                    }).done((data) => {
                        console.log(data);
                        if(row.hasClass('child')){
                            let parentRow = row.prev()[0];
                            console.log(parentRow);
                            $(parentRow).fadeOut();
                        }
                        row.fadeOut();
                        stats.updateChallengersStats();
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

    function addOnCancelClickEvent() {
        $(document).on('click', '.challenge-cancel',function () {
            let id = $(this).attr('usr-id');
            let row = $(this).closest('tr');
            swal({
                title: "Are you sure you want to cancel this challenge?",
                text: "The more challengers you have the more damage you will take and when you die you will lose half of your money",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, withdraw challenge!",
                cancelButtonText: "No, I am scared!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/challenge/cancel?id=" + id,
                    }).done((data) => {
                        console.log(data);
                        if(row.hasClass('child')){
                            let parentRow = row.prev()[0];
                            console.log(parentRow);
                            $(parentRow).fadeOut();
                        }
                        row.fadeOut();
                        stats.updateChallengersStats();
                        swal("Challenged canceled!", "Your challenge request has been canceled. It seems you are scared 😱", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your to do challenge hasn't been withdrawn :)", "error");
                }
            });
        });
    }

    function addOnApproveClickEvent() {
        $(document).on('click', '.challenge-approve',function () {
            let id = $(this).attr('usr-id');
            let row = $(this).closest('tr');
            swal({
                title: "Are you sure you want to approve this opponents challenge?",
                text: "The more challengers you have the more damage you will take and when you die you will lose half of your money",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, approve it!",
                cancelButtonText: "No, I am scared!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/challenge/approve?id=" + id,
                    }).done((data) => {
                        console.log(data);
                        if(row.hasClass('child')){
                            let parentRow = row.prev()[0];
                            console.log(parentRow);
                            $(parentRow).fadeOut();
                        }
                        row.fadeOut();
                        stats.updateChallengersStats();
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


    function addOnDeclineClickEvent() {
        $(document).on('click', '.challenge-decline',function () {
            let id = $(this).attr('usr-id');
            let row = $(this).closest('tr');
            swal({
                title: "Are you sure you want to decline this challenge?",
                text: "The more challengers you have the more damage you will take and when you die you will lose half of your money",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, DECLINE challenge!",
                cancelButtonText: "No, I am NOT scared!",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: "/api/challenge/decline?id=" + id,
                    }).done((data) => {
                        console.log(data);
                        if(row.hasClass('child')){
                            let parentRow = row.prev()[0];
                            console.log(parentRow);
                            $(parentRow).fadeOut();
                        }
                        row.fadeOut();
                        stats.updateChallengersStats();
                        swal("Challenged canceled!", "Your challenge request has been declined. It seems you are scared 😱", "success");
                        console.log(data);
                    }).fail((err) => {
                        //Add notify in corner
                        console.log(err);
                    });
                } else {
                    swal("Cancelled", "Your to do challenge hasn't been declined :)", "error");
                }
            });
        });
    }

    return {
        addOnChallengeClickEvent,
        addOnApproveClickEvent,
        addOnCancelClickEvent,
        addOnDeclineClickEvent
    }
})();
stats.updateChallengersStats();
challenge.addOnChallengeClickEvent();
challenge.addOnApproveClickEvent();
challenge.addOnCancelClickEvent();
challenge.addOnDeclineClickEvent();
