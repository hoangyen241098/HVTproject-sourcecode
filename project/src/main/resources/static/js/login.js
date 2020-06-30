<!--Send data to JSON-->
$(document).ready(function () {
    $("#signin").click(function (e) {
        $('.errorTxt').text("");
        var username = $('#username').val();
        var password = $('#password').val();

        if (username.trim() == "" && password.trim() == "") {
            $('.errorTxt').text("Hãy điền tên đăng nhập và mật khẩu.");
            return false;
        } else if (username.trim() == "") {
            $('.errorTxt').text("Hãy điền tên đăng nhập.");
            return false;
        } else if (password.trim() == "") {
            $('.errorTxt').text("Hãy điền mật khẩu.");
            return false;
        } else {
            $('#error-username').text("");
            $('#error-password').text("");
            var user = {
                username: $('#username').val(),
                password: $('#password').val(),
            };
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: "/api/user/login",
                data: JSON.stringify(user),
                success: function (data) {
                    var messageCode = data.message.messageCode;
                    var message = data.message.message;
                    var roleID = data.roleid;
                    var account = {
                        username,
                        password,
                        roleID
                    }
                    if (messageCode == 0) {
                        $('#loginSuccess').css('display', 'block');
                        localStorage.setItem("username", account.username);
                        localStorage.setItem("loginSuccess", messageCode);
                        localStorage.setItem("roleID", account.roleID);
                        $("#loginSuccess-menu").addClass("show");
                        $('#login').css('display', 'none');
                        if (roleID == 1) {
                            $("#admin").addClass("show");
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
        }
    })
});
