$(document).ready(function () {
    var settings = {
        "url": "http://localhost:8080/api/admin/rolelist",
        "method": "POST",
        "timeout": 0,
        "format": "json",
    };
    $.getJSON(settings).done(function (data) {
        $.each(data, function (i, item) {
            $.each(item, function (i, list) {
                $('#search-role').append(`<li value="` + list.roleId + `">` + list.roleName + `</li>`);
            });
        });
    });
});

$("#search").click(function (e) {
    console.log("username = " + $('#searchByUsername input').val());
    console.log("sortById = " + $('#sortBy input').attr("value"));
    console.log("roleId = " + $('#role-name input').attr("value"));
    console.log("sortId = " + $('#sort input').attr("value"));
});