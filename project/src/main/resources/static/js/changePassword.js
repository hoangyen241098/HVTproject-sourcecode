$(document).ready(function () {
    $("#confirm").click(function (e) {
        $('.changePassword-err').text("");
        var oldpassword = $('#old-password').val();
        var newpassword = $('#new-password').val();
        var confirmpassword = $('#confirm-password').val();

        if (oldpassword.trim() == "" && newpassword.trim() == "" && confirmpassword.trim() == "" ||
            oldpassword.trim() == "" && newpassword.trim() == "" ||
            newpassword.trim() == "" && confirmpassword.trim() == "" ||
            oldpassword.trim() == "" && confirmpassword.trim() == "") {
            $('.changePassword-err').text("Hãy điền đầy đủ tất cả các trường.");
            return false;
        } else if (oldpassword.trim() == "") {
            $('.changePassword-err').text("Hãy điền mật khẩu cũ.");
            return false;
        } else if (newpassword.trim() == "") {
            $('.changePassword-err').text("Hãy điền mật khẩu mới.");
            return false;
        } else if (confirmpassword.trim() == "") {
            $('.changePassword-err').text("Hãy xác nhận lại mật khẩu.");
            return false;
        } else if (newpassword != confirmpassword) {
            $('.changePassword-err').text("Mật khẩu xác nhận không đúng.");
            return false;
        } else {
            $('.changePassword-err').text("");
            var password = {
                userName: localStorage.getItem('username'),
                oldPassword: oldpassword,
                newPassword: newpassword
            };
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: "/api/user/changepassword",
                data: JSON.stringify(password),
                beforeSend: function () {
                    $('body').addClass("loading")
                },
                complete: function () {
                    $('body').removeClass("loading")
                },
                success: function (data) {
                    console.log(JSON.stringify(password));
                    var messageCode = data.messageCode;
                    var message = data.message;
                    if (messageCode == 0) {
                        $('#changePassword').css('display', 'block');
                    } else {
                        $('.changePassword-err').text(message);
                    }
                },
                failure: function (errMsg) {
                    $('.changePassword-err').text(errMsg);
                },
                dataType: "json",
                contentType: "application/json"
            });
        }
    })
});