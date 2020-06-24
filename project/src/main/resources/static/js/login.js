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
            console.log(user.username);
            console.log(user.password);
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: "http://localhost:8080/api/user/login",
                data: JSON.stringify(user),
                success: function (data) {
                    var messageCode = data.message.messageCode;
                    var message = data.message.message;
                    if (messageCode == 0) {
                        $('#loginSuccess').css('display', 'block');
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

// <!--Get data from JSON-->
// (function () {
//     var settings = {
//         "url": "http://localhost:8080/api/user/login",
//         "method": "POST",
//         "timeout": 0,
//         "format": "json",
//     };
//
//     $.getJSON(settings).done(function (data) {
//         $.each(data, function (i, item) {
//             $("#json-item").append(item.name + ": Username: " + item.username + "   |   Password: " + item.password);
//         });
//     });
// })();