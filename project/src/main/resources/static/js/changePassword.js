$(document).ready(function () {
    $("#confirm").click(function (e) {
        $('.errorTxt').text("");
        var oldpassword = $('#old-password').val();
        var newpassword = $('#new-password').val();
        var confirmpassword = $('#confirm-password').val();


        if (oldpassword.trim() == "" && newpassword.trim() == "" && confirmpassword.trim() == "" ||
            oldpassword.trim() == "" && newpassword.trim() == "" ||
            newpassword.trim() == "" && confirmpassword.trim() == "" ||
            oldpassword.trim() == "" && confirmpassword.trim() == "") {
            $('.errorTxt').text("Hãy điền đầy đủ tất cả các trường.");
            return false;
        } else if (oldpassword.trim() == "") {
            $('.errorTxt').text("Hãy điền mật khẩu cũ.");
            return false;
        } else if (newpassword.trim() == "") {
            $('.errorTxt').text("Hãy điền mật khẩu mới.");
            return false;
        } else if (confirmpassword.trim() == "") {
            $('.errorTxt').text("Hãy xác nhận lại mật khẩu.");
            return false;
        } else if (newpassword != confirmpassword) {
            $('.errorTxt').text("Mật khẩu xác nhận không đúng.");
            return false;
        } else {
            $('.errorTxt').text("");
            var password = {
                oldpassword,
                newpassword
            };
            e.preventDefault();
            $.ajax({
                type: 'POST',
                url: "http://localhost:8080/api/user/changepassword",
                data: JSON.stringify(password),
                success: function (data) {
                    var messageCode = data.message.messageCode;
                    var message = data.message.message;
                    console.log(messageCode);
                    console.log(message);
                    if (messageCode == 0) {
                        $('#changePassword').css('display', 'block');
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