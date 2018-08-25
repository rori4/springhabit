
/**
* Theme: Adminto Admin Template
* Author: Coderthemes
* Dashboard
*/

$(document).ready(function() {
    "use strict";

    var Dashboard1 = function() {
    	this.$realData = []
    };

    //creates Bar chart
    Dashboard1.prototype.createBarChart  = function(element, data, xkey, ykeys, labels, lineColors) {
        Morris.Bar({
            element: element,
            data: data,
            xkey: xkey,
            ykeys: ykeys,
            labels: labels,
            hideHover: 'auto',
            resize: true, //defaulted to true
            gridLineColor: '#eeeeee',
            barSizeRatio: 0.2,
            barColors: lineColors
        });
    },

    //creates line chart
    Dashboard1.prototype.createLineChart = function(element, data, xkey, ykeys, labels, opacity, Pfillcolor, Pstockcolor, lineColors) {
        Morris.Line({
          element: element,
          data: data,
          xkey: xkey,
          ykeys: ykeys,
          labels: labels,
          fillOpacity: opacity,
          pointFillColors: Pfillcolor,
          pointStrokeColors: Pstockcolor,
          behaveLikeLine: true,
          gridLineColor: '#eef0f2',
          hideHover: 'auto',
          resize: true, //defaulted to true
          pointSize: 0,
          lineColors: lineColors
        });
    },

    //creates Donut chart
    Dashboard1.prototype.createDonutChart = function(element, data, colors) {
        Morris.Donut({
            element: element,
            data: data,
            resize: true, //defaulted to true
            colors: colors
        });
    },

    
    Dashboard1.prototype.init = function() {

        //creating bar chart
        // var $barData  = [
        //     { y: '2010/08/26', a: 5 },
        //     { y: '2010/08/27', a: -3 },
        //     { y: '2010/08/28', a: 4 },
        //     { y: '2010/08/29', a: -2 },
        //     { y: '2010/08/30', a: 4 },
        //     { y: '2010/08/31', a: -10 },
        //     { y: '2010/09/1', a: 20 },
        // ];
        // this.createLineChart('morris-habit-stats', $barData, 'y', ['a'], ['Statistics'], ['#188ae2']);

        // //create line chart
        var $data  = [
            { y: '2008', a: 50, b: 0 },
            { y: '2009', a: 75, b: 50 },
            { y: '2010', a: 30, b: 80 },
            { y: '2011', a: 50, b: 50 },
            { y: '2012', a: 75, b: 10 },
            { y: '2013', a: 50, b: 40 },
            { y: '2014', a: 75, b: 50 },
            { y: '2015', a: 100, b: 70 }
          ];
        this.createLineChart('morris-habit-stats', $data, 'y', ['a'], ['Series A','Series B'],['0.9'],['#ffffff'],['#999999'], ['#10c469','#188ae2']);
        //
        // //creating donut chart
        // var $donutData = [
        //         {label: "Download Sales", value: 12},
        //         {label: "In-Store Sales", value: 30},
        //         {label: "Mail-Order Sales", value: 20}
        //     ];
        // this.createDonutChart('morris-donut-example', $donutData, ['#ff8acc', '#5b69bc', "#35b8e0"]);
    },
    //init
    $.Dashboard1 = new Dashboard1, $.Dashboard1.Constructor = Dashboard1
}(window.jQuery),

//initializing 
function($) {
    "use strict";
    $.Dashboard1.init();
}(window.jQuery));