$(document).ready(function () {
    $("#login-form").validate({
        rules: {
            username: "required",
            password: "required"
        },
        messages: {
            username: "Hãy điền tên đăng nhập.",
            password: "Hãy điền mật khẩu."
        },

        errorPlacement: function (error, element) {
            if (element.attr("name") == "username") {
                element.parent('div').parent('div').find("label.error-text").addClass("error").text($(error).text());
            }
            if (element.attr("name") == "password") {
                element.parent('div').parent('div').find("label.error-text").addClass("error").text($(error).text());
            } else {
                return true;
            }
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit();
        }
    });
});