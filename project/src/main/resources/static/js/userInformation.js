$(document).ready(function () {
    $('.userInfo-err').text("");
    var account = {
        userName: localStorage.getItem("username")
    }
    $.ajax({
        url: '/api/user/viewinformation',
        type: 'POST',
        data: JSON.stringify(account),
        success: function (data) {
            $('#fullName').attr('value', data.fullName);
            $('#username').attr('value', data.userName);
            $('#phone').attr('value', data.phone);
            $('#email').attr('value', data.email);
            $('#className').attr('value', data.className);
            $('#roleName').attr('value', data.roleName);
        },
        failure: function (errMsg) {
            $('.userInfo-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
});

$("#editInfo").click(function () {
    $('#fullName').prop('disabled', false);
    $('#phone').prop('disabled', false);
    $('#email').prop('disabled', false);
    $('#editInfo').addClass('hide');
    $('#editInfo').removeClass('show');
    $('#confirmEdit').addClass('show');
    $('#confirmEdit').removeClass('hide');

});

$("#confirmEdit").click(function (e) {
    var emailRegex = '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$';
    var phoneRegex = '^[0-9\\-\\+]{9,15}$';
    var fullName = $('#fullName').val();
    var phone = $('#phone').val();
    var email = $('#email').val();

    if (fullName.trim() == "") {
        $('.userInfo-err').text("Hãy điền họ và tên.");
        return false;
    } else if (!phone.match(phoneRegex)) {
        $('.userInfo-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (!email.match(emailRegex)) {
        $('.userInfo-err').text("Email không đúng định dạng.");
        return false;
    } else {
        $('.userInfo-err').text("");
        $('#fullName').prop('disabled', true);
        $('#phone').prop('disabled', true);
        $('#email').prop('disabled', true);
        $('#confirmEdit').addClass('hide');
        $('#confirmEdit').removeClass('show');
        $('#editInfo').addClass('show');
        $('#editInfo').removeClass('hide');
        var info = {
            userName: localStorage.getItem("username"),
            fullName: fullName,
            phone: phone,
            email: email
        }
        console.log(JSON.stringify(info));
        e.preventDefault();
        $.ajax({
            url: '/api/user/editinformation',
            type: 'POST',
            data: JSON.stringify(info),
            success: function (data) {
                console.log(data);
                var messageCode = data.messageCode;
                var message = data.message;
                if (messageCode == 0) {
                    $('#editInfoSuccess').css('display', 'block');
                } else {
                    $('.userInfo-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.userInfo-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
})