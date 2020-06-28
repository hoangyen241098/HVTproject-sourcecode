$(document).ready(function () {
    var username = localStorage.getItem("username");
    var password = localStorage.getItem("password");
    var roleID = localStorage.getItem("roleID");
    var user = {
        username,
        password,
        roleID
    }
    $.ajax({
        type: 'POST',
        url: "/api/user/login",
        data: JSON.stringify(user),
        success: function (data) {
            var messageCode = data.message.messageCode;
            var message = data.message.message;
            if (messageCode == 0) {
                $("#loginSuccess-menu").addClass("show");
                $('#login').css('display', 'none');
                if (roleID == 1) {
                    $("#admin").addClass("show");
                }
                if (roleID == 2) {
                    $("#schedule-manager").addClass("show");
                }
            } else {
                $('.errorTxt').text(message);
            }
        },
        failure: function (errMsg) {
            $('.errorTxt').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
    $("#logout").click(function () {
        localStorage.clear();

    })
});