var roleId = localStorage.getItem("roleID");
$(document).ready(function () {
    $('.userInfo-err').text("");
    var account = {
        userName: localStorage.getItem("username")
    }
    if(roleId == 3 || roleId == 4){
        $("#editInfo").addClass('hide');
        $("#fullNameDiv").addClass('hide');
        $("#phoneDiv").addClass('hide');
        $("#emailDiv").addClass('hide');
    }
    $.ajax({
        url: '/api/user/viewinformation',
        type: 'POST',
        data: JSON.stringify(account),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
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
    if(roleId != 3 && roleId != 4) {
        $('#fullName').prop('disabled', false);
        $('#phone').prop('disabled', false);
        $('#email').prop('disabled', false);
        $('#editInfo').addClass('hide');
        $('#editInfo').removeClass('show');
        $('#confirmEdit').addClass('show');
        $('#confirmEdit').removeClass('hide');
    }
});

$("#confirmEdit").click(function (e) {
    var emailRegex = '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$';
    var phoneRegex = '^[0-9\\-\\+]{9,15}$';
    var fullName = $('#fullName').val().trim();
    var phone = $('#phone').val().trim();
    var email = $('#email').val().trim();

    if (phone != "" && !phone.match(phoneRegex)) {
        $('.userInfo-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (email != "" && !email.match(emailRegex)) {
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
        e.preventDefault();
        $.ajax({
            url: '/api/user/editinformation',
            type: 'POST',
            data: JSON.stringify(info),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
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