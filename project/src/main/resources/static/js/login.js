<!--Send data to JSON-->
$(document).ready(function () {
    $("#signin").click(function (e) {
        $('.errorTxt').text("");
        var username = $('#username').val();
        var password = $('#password').val();

        if (username.trim() == "" && password.trim() == "") {
            $('#error-username').text("Hãy điền tên đăng nhập.");
            $('#error-password').text("Hãy điền mật khẩu.");
            return false;
        } else if (username.trim() == "") {
            $('#error-username').text("Hãy điền tên đăng nhập.");
            $('#error-password').text("");
            return false;
        } else if (password.trim() == "") {
            $('#error-username').text("");
            $('#error-password').text("Hãy điền mật khẩu.");
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
                        if (message.includes("Tên đăng nhập")) {
                            $('#error-username').text(message);
                        } else {
                            $('#error-password').text(message);
                        }
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
//
// rules: {
//     username: "required",
//     password: "required"
// },
// messages: {
//     username: "Hãy điền tên đăng nhập.",
//     password: "Hãy điền mật khẩu."
// },
//
// errorPlacement: function (error, element) {
//     if (element.attr("name") == "username") {
//         element.parent('div').parent('div').find("label.error-text").addClass("error").text($(error).text());
//     }
//     if (element.attr("name") == "password") {
//         element.parent('div').parent('div').find("label.error-text").addClass("error").text($(error).text());
//     } else {
//         return true;
//     }
// },
// submitHandler: function (form) {
//         var user = {
//             username: $('#username').val(),
//             password: $('#password').val(),
//         };
//
//         e.preventDefault();
//         $.ajax({
//             type: 'POST',
//             url: "http://localhost:8080/api/user/login",
//             data: JSON.stringify(user),
//             success: function (data) {
//                 var messageCode = data.message.messageCode;
//                 var message = data.message.message;
//                 if (messageCode == 0) {
//                     $('#loginSuccess').css('display', 'block');
//                 } else {
//                     $('.errorTxt').text(message);
//                 }
//             },
//             failure: function (errMsg) {
//                 $('.errorTxt').text(errMsg);
//             },
//             dataType: "json",
//             contentType: "application/json"
//         });
//     }
// });
// $("#signin").click(function (e) {
//     var user = {
//         username: $('#username').val(),
//         password: $('#password').val(),
//     };
//
//     e.preventDefault();
//     $.ajax({
//         type: 'POST',
//         url: "http://localhost:8080/api/user/login",
//         data: JSON.stringify(user),
//         success: function (data) {
//             var messageCode = data.message.messageCode;
//             var message = data.message.message;
//             if (messageCode == 0) {
//                 $('#loginSuccess').css('display', 'block');
//             } else {
//                 $('.errorTxt').text(message);
//             }
//         },
//         failure: function (errMsg) {
//             $('.errorTxt').text(errMsg);
//         },
//         dataType: "json",
//         contentType: "application/json"
//     });
//
// });
// })
// ;


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