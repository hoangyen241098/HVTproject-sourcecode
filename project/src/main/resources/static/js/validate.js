// function validateForm() {
//     var username = $('#username').val();
//     var password = $('#password').val();
//
//     if (username == null) {
//         $("#error-username").text(username);
//         return false;
//     }
//     if (password == null) {
//         $("#error-password").text("Hãy điền mật khẩu");
//         return false;
//     }
//     return false;
// }


$(document).ready(function () {
    $("#login-form").validate({
        rules: {
            username: "required",
            password: "required"
        },
        messages: {
            username: $("#username").text("Hãy điền tên đăng nhập."),
            password: $("#password").text("Hãy điền mật khẩu.")
        },

        // errorPlacement: function (error, element) {
        //     if (element.attr("name") == "username") {
        //         element.parent('div').parent('div').find("label.error-text").addClass("error").text(error);
        //     }
        //     if (element.attr("name") == "password") {
        //         element.parent('div').parent('div').find("label.error-text").addClass("error").text(error);
        //     } else {
        //         return true;
        //     }
        // },
        submitHandler: function (form) {
            $(form).ajaxSubmit();
        }
    });
});