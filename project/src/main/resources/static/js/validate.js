$(document).ready(function () {
    $("#login-form").validate({
        errorPlacement: function (error, element) {
            if (element.attr("name") == "username") {
                $("#error-username").text("Hãy nhập tên đăng nhập");
            } else if (element.attr("name") == "password") {
                $("#error-password").text("Hãy nhập mật khẩu");
            }
            else {
                error.append($('.error-text span'));
            }
        },
    });
});