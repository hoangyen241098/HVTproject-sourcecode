$(document).ready(function () {
    var settings = {
        "url": "http://localhost:8080/api/admin/rolelist",
        "method": "POST",
        "timeout": 0,
        "format": "json",
    };

    $.getJSON(settings).done(function (data) {
        // var list = JSON.parse(data);
        // console.log(list.length);
        $.each(data, function (i, item) {
            $.each(item, function (i, list) {
                $('#search-role').append(`<li><span>` + list.roleName + `</span></li>`);
                console.log(list.roleName);
            });
        });
    });
});