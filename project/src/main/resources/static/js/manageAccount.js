$(document).ready(function () {
    $.ajax({
        url: '/api/admin/rolelist',
        type: 'POST',
        dataType: 'JSON',
        success: function (data) {
            $.each(data, function (i, item) {
                $.each(item, function (i, list) {
                    $('#search-role').append(`<li value="` + list.roleId + `">` + list.roleName + `</li>`);
                });
            });
        },
        failure: function (errMsg) {
            console.log(errMsg);
        }
    });
});

$("#search").click(function (e) {
    console.log("username = " + $('#searchByUsername input').val());
    console.log("sortById = " + $('#sortBy input').attr("value"));
    console.log("roleId = " + $('#role-name input').attr("value"));
    console.log("sortId = " + $('#sort input').attr("value"));
});