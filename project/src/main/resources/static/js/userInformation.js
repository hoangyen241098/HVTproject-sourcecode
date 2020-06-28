$('.errMsg').text("");
var username = localStorage.getItem("username");
var account = '{"userName":' + JSON.stringify(username) + '}'
$(document).ready(function () {
    $.ajax({
        url: '/api/user/viewinformation',
        type: 'POST',
        data: account,
        success: function (data) {
            console.log(data);
            $('#fullName').attr('value', data.fullName);
            $('#username').attr('value', data.userName);
            $('#phone').attr('value', data.phone);
            $('#email').attr('value', data.email);
            $('#className').attr('value', data.className);
            $('#roleName').text(data.roleName);
        },
        failure: function (errMsg) {
            console.log(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
});