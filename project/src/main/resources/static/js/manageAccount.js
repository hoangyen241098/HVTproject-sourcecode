$(document).ready(function () {
    var settings = {
        "url": "http://localhost:8080/api/admin/rolelist",
        "method": "POST",
        "timeout": 0,
        "format": "json",
    };

    $.getJSON(settings).done(function (data) {
        $.each(data, function (i, item) {
            for (let prop in item[0]) {
                $("#search-role").innerText = prop
            }
        });
    });
});