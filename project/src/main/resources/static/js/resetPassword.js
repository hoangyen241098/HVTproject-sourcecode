$(document).ready(function () {
    $("#confirm").click(function (e) {
        $('.errorTxt').text("");
        var newpassword = $('#new-password').val();
        var confirmpassword = $('#confirm-password').val();

        if (newpassword.trim() == "" && confirmpassword.trim() == "") {
            $('.errorTxt').text("Hãy điền đầy đủ tất cả các trường.");
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
            $('#changePassword').css('display', 'block');
            // var password = {
            //     newpassword,
            //     confirmpassword
            // };
            e.preventDefault();
            // $.ajax({
            //     type: 'POST',
            //     url: '/api/admin/resetpassword',
            //     data: JSON.stringify(password),
            //     success: function (data) {
            //         var messageCode = data.message.messageCode;
            //         var message = data.message.message;
            //         console.log(messageCode);
            //         console.log(message);
            //         if (messageCode == 0) {
            //             $('#changePassword').css('display', 'block');
            //         } else {
            //             $('.errorTxt').text(message);
            //         }
            //     },
            //     failure: function (errMsg) {
            //         $('.errorTxt').text(errMsg);
            //     },
            //     dataType: "json",
            //     contentType: "application/json"
            // });
        }
    })
});