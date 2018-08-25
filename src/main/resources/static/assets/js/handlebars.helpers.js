var DateFormats = {
    short: "MM/DD/YYYY",
    long: "MM/DD/YYYY hh:mm:ss"
};

Handlebars.registerHelper("formatDate", function (datetime, format) {
    if(datetime!=null){
        if (moment) {
            // can use other formats like 'lll' too
            format = DateFormats[format] || format;
            return moment(datetime).format(format);
        }
        return datetime;
    }
    return datetime;
});