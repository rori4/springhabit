let admin = (() => {
    let userId = $('#user-id');
    let userName = $('#user-name');
    let userLevel = $('#user-level');
    let userExp = $('#user-experience');
    let userHealth = $('#user-health');
    let userMaxHealth = $('#user-maxHealth');
    let userGold = $('#user-gold');
    let userRoles = $('#user-roles');

    async function preloadRolesIntoModal() {
        await $.ajax({
            type: 'GET',
            url: "/api/admin/roles",
        }).done((data) => {
            console.log(data);
            $.each(data, function (index, value) {
                let newOption = new Option(value.name, value.id, false, false);
                userRoles.append(newOption).trigger('change');
            });
        }).fail((err) => {
            //Add notify in corner
            console.log(err);
        });
    }

    function openModal() {
        $('.edit-user').click(function () {
            let id = $(this).attr('usr-id');
            removeErrors();
            $.ajax({
                type: 'GET',
                url: "/api/admin/user?id=" + id,
            }).done((data) => {
                console.log(data);
                userId.val(data.id);
                userName.val(data.name);
                userLevel.val(data.level);
                userExp.val(data.experience);
                userHealth.val(data.health);
                userMaxHealth.val(data.maxHealth);
                userGold.val(data.gold);
                let roleIds = data.roles.map(function (obj) {
                    return obj.id;
                });
                userRoles.val(roleIds).trigger('change');

            }).fail((err) => {
                //Add notify in corner
                console.log(err);
            });

            let modal = new Custombox.modal({
                content: {
                    effect: 'fadein',
                    target: '#user-edit-modal'
                }
            });
            modal.open();
        })
    }

    function submitUserEdit() {
        $('#user-edit-submit').click(function (e) {
            e.preventDefault();
            console.log('Clicked on submit');
            console.log(userRoles.select2('data'));
            console.log('Jquery on submit');
            console.log(userRoles.val());
            let selected = userRoles.select2('data');
            let selectedSerialized = [];
            $.each(selected, function (key, val) {
                selectedSerialized.push({id: val.id, name: val.text});
            });
            console.log('Serialized');
            console.log(selectedSerialized);
            let formData = {
                id: userId.val(),
                name: userName.val(),
                level: userLevel.val(),
                experience: userExp.val(),
                health: userHealth.val(),
                maxHealth: userMaxHealth.val(),
                gold: userGold.val(),
                roles: selectedSerialized
            };
            console.log(formData);
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: 'POST',
                url: "/api/admin/user",
                data: JSON.stringify(formData),
                dataType: 'json',
            }).done((data) => {
                console.log(data);
                location.reload();
            }).fail((err) => {
                removeErrors();
                let source = $("#error-field").html();
                let template = Handlebars.compile(source);
                let errors = err.responseJSON.errors;
                $.each(errors, function (index, value) {
                    let field = $("#user-" + value.field);
                    field.addClass('parsley-error');
                    field.after(template(value));
                });
                console.log(err);
            })
        });
    }

    function removeErrors() {
        $(".parsley-errors-list").remove();
        $(".form-control").removeClass("parsley-error");
    }

    return {
        preloadRolesIntoModal,
        openModal,
        submitUserEdit
    }
})();

admin.preloadRolesIntoModal();
admin.openModal();
admin.submitUserEdit();
