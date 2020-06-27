$('.errMsg').text("");
var username = localStorage.getItem("username");
user = '{"username":"' + username + '"}';
$.ajax({
    url: '/api/user/viewinformation',
    type: 'POST',
    dataType: user,
    success: function (data) {
        console.log(data);
        // $.each(data, function (i, item) {
        //     $.each(item, function (i, list) {
        //         $('#position-role').append(`<option value="` + list.roleId + `">` + list.roleName + `</option>`);
        //     });
        // });
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});