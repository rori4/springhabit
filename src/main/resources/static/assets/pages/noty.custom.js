let noty = (() => {
    let timeout = 1000;
    let timeoutSlow = 4000;

    function handleData(data) {
        Object.keys(data).forEach(function (reward) {
            if (data[reward] !== null) {
                switch (reward) {
                    case 'experience':
                        expNotification(data[reward]);
                        break;
                    case 'gold':
                        goldNotification(data[reward]);
                        break;
                    case 'health':
                        if (data[reward] > 0) {
                            maxHealthNotification(data[reward]);
                        } else {
                            damageNotification(data[reward]);
                        }
                        break;
                    case 'level':
                        if (data[reward] > 0) {
                            levelNotification();
                        } else {
                            deadNotification();
                        }
                        break;
                    case 'kills':
                        if (data[reward] > 0) {
                            killsNotification(data[reward]);
                        }
                        break;
                }
            }
        });
    }

    function handleWebSocketNotifications(data) {
        console.log(data);
        Object.keys(data).forEach(function (type) {
            console.log(data[type]);
            switch (data[type]) {
                case 'damage':
                    wsDamageNotification(data);
                    break;
                case 'reward':
                    wsRewardNotification(data);
                    break;
            }
        })
    }

    function wsDamageNotification(message) {
        console.log(message);
        new Noty({
            text: '<div class="text-left">💥 <strong>-' + message.val + 'hp</strong> ' + message.msg + ' by ' + message.opponentName + '</div>',
            type: 'error',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show();
    }

    function wsRewardNotification(message) {
        new Noty({
            text: '<div class="text-left">🤣 <strong>+' + message.val + ' BTC</strong> ' + message.opponentName + ' has killed himself! ' + message.msg + '</div>',
            type: 'warning',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeoutSlow,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show();
    }

    function goldNotification(gold) {
        new Noty({
            text: '<div class="text-left"><strong>💰 +' + gold + 'BTC</strong> You earned some gold!</div>',
            type: 'warning',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    function levelNotification() {
        new Noty({
            text: '<div class="text-left">🍄 You have LEVELED UP!</div>',
            type: 'success',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    function deadNotification() {
        new Noty({
            text: '<div class="text-left">💀 You have DIED and lost your level and half your gold!</div>',
            type: 'error',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    function killsNotification(data) {
        new Noty({
            text: '<div class="text-left">🏆 <strong>' + data + ' kills </strong><br>You have killed ' + data + ' challengers and got from each half of their gold! CONGRATULATIONS!</div>',
            type: 'information',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeoutSlow,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }


    function expNotification(xp) {
        new Noty({
            text: '<div class="text-left"><strong>⚡ +' + xp + 'xp</strong> You earned some experience!</div>',
            type: 'success',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    function maxHealthNotification(health) {
        new Noty({
            text: '<div class="text-left"><strong>💖 +' + health + 'hp</strong> to max health!</div>',
            type: 'success',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    function damageNotification(health) {
        new Noty({
            text: '<div class="text-left"><strong>💖 ' + health + 'hp</strong> You have taken damage!</div>',
            type: 'error',
            theme: 'mint',
            layout: 'topRight',
            timeout: timeout,
            animation: {
                open: mojsShow,
                close: mojsClose
            }
        }).show()
    }

    return {
        handleData,
        goldNotification,
        levelNotification,
        maxHealthNotification,
        damageNotification,
        handleWebSocketNotifications
    }
})();


var mojsShow = function (promise) {
    var n = this;
    var Timeline = new mojs.Timeline();
    var body = new mojs.Html({
        el: n.barDom,
        x: {500: 0, delay: 0, duration: 500, easing: 'elastic.out'},
        isForce3d: true,
        onComplete: function () {
            promise(function (resolve) {
                resolve()
            })
        }
    });

    var parent = new mojs.Shape({
        parent: n.barDom,
        width: 200,
        height: n.barDom.getBoundingClientRect().height,
        radius: 0,
        x: {[150]: -150},
        duration: 1.2 * 500,
        isShowStart: true
    })

    n.barDom.style['overflow'] = 'visible'
    parent.el.style['overflow'] = 'hidden'

    var burst = new mojs.Burst({
        parent: parent.el,
        count: 10,
        top: n.barDom.getBoundingClientRect().height + 75,
        degree: 90,
        radius: 75,
        angle: {[-90]: 40},
        children: {
            fill: '#EBD761',
            delay: 'stagger(500, -50)',
            radius: 'rand(8, 25)',
            direction: -1,
            isSwirl: true
        }
    });

    var fadeBurst = new mojs.Burst({
        parent: parent.el,
        count: 2,
        degree: 0,
        angle: 75,
        radius: {0: 100},
        top: '90%',
        children: {
            fill: '#EBD761',
            pathScale: [.65, 1],
            radius: 'rand(12, 15)',
            direction: [-1, 1],
            delay: .8 * 500,
            isSwirl: true
        }
    });

    Timeline.add(body, burst, fadeBurst, parent)
    Timeline.play()
};

var mojsClose = function (promise) {
    var n = this
    new mojs.Html({
        el: n.barDom,
        x: {0: 500, delay: 10, duration: 500, easing: 'cubic.out'},
        isForce3d: true,
        onComplete: function () {
            promise(function (resolve) {
                resolve()
            })
        }
    }).play()
};

var bouncejsShow = function (promise) {
    var n = this;
    new Bounce()
        .translate({
            from: {x: 450, y: 0}, to: {x: 0, y: 0},
            easing: 'bounce',
            duration: 1000,
            bounces: 4,
            stiffness: 3
        })
        .scale({
            from: {x: 1.2, y: 1}, to: {x: 1, y: 1},
            easing: 'bounce',
            duration: 1000,
            delay: 100,
            bounces: 4,
            stiffness: 1
        })
        .scale({
            from: {x: 1, y: 1.2}, to: {x: 1, y: 1},
            easing: 'bounce',
            duration: 1000,
            delay: 100,
            bounces: 6,
            stiffness: 1
        })
        .applyTo(n.barDom, {
            onComplete: function () {
                promise(function (resolve) {
                    resolve()
                })
            }
        })
};

var bouncejsClose = function (promise) {
    var n = this
    new Bounce()
        .translate({
            from: {x: 0, y: 0}, to: {x: 450, y: 0},
            easing: 'bounce',
            duration: 500,
            bounces: 4,
            stiffness: 1
        })
        .applyTo(n.barDom, {
            onComplete: function () {
                promise(function (resolve) {
                    resolve()
                })
            }
        })
};


