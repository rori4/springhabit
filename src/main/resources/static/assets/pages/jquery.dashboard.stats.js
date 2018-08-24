let stats = (() => {

    function reloadStats() {
            $.ajax({
                type: 'GET',
                url: "/api/user",
            }).done((data) => {
                console.log(data);
                updateElements(data);
            }).fail((err) => {
                console.log(err);
            });
    }

    function updateElements(data){
        console.log("WHAT I GET: " + data);
        //Level

        console.log('MIN: ' + data.levelStartExp);
        console.log('MAX: ' + data.nextLevelExp);
        $('.widget-chart-box-1 input').trigger('configure', {
            min: data.levelStartExp,
            max: data.nextLevelExp,
        });

        $('.widget-chart-box-1 input')
            .val(data.experience)
            .trigger('change');

        $('#level-data-h2').text("Level " + data.level);
        let experienceLeft = data.nextLevelExp - data.experience;
        $('#level-data-p').text(experienceLeft  + " experience until next level");

        //Health
        let percentageHealth = (data.health/data.maxHealth)*100;
        console.log(percentageHealth);

        $('#health-data-bar').attr('aria-valuenow', percentageHealth).css('width',percentageHealth+'%');
        $('#health-data').html(percentageHealth + '% <i class="zmdi zmdi-plus-circle"></i>');
        $('#health-data-h2').text(data.health);
        $('#health-data-p').text(percentageHealth+'% of max health');

        //Gold
        $('#gold-data').text(data.gold);

        //Challenger
        $('#challenge-data').text(data.challengesAccepted.length);

    }


    return {
        reloadStats
    }
})();

stats.reloadStats();
