$(document).ready(function () {
    $('.teacherInfo-err').text("");
    var teacher = {
        teacherId: localStorage.getItem("teacherId"),
    }
    console.log(JSON.stringify(teacher))
    $.ajax({
        url: '/api/admin/viewteacherinformation',
        type: 'POST',
        data: JSON.stringify(teacher),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            $('#fullName').attr('value', data.fullName);
            $('#identifier').attr('value', data.identifier);
            $('#phone').attr('value', data.phone);
            $('#email').attr('value', data.email);
        },
        failure: function (errMsg) {
            $('.teacherInfo-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
});

$("#editInfo").click(function () {
    $('#fullName').prop('disabled', false);
    $('#identifier').prop('disabled', false);
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
    var identifier = $('#identifier').val();
    var fullName = $('#fullName').val();
    var phone = $('#phone').val();
    var email = $('#email').val();

    if (fullName.trim() == "") {
        $('.teacherInfo-err').text("Hãy điền họ và tên.");
        return false;
    } else if (identifier.trim() == "") {
        $('.teacherInfo-err').text("Hãy điền tên định danh.");
        return false;
    } else if (phone.trim() != "" && !phone.match(phoneRegex)) {
        $('.teacherInfo-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (email.trim() != "" && !email.match(emailRegex)) {
        $('.teacherInfo-err').text("Email không đúng định dạng.");
        return false;
    } else {
        $('.teacherInfo-err').text("");
        $('#fullName').prop('disabled', true);
        $('#identifier').prop('disabled', true);
        $('#phone').prop('disabled', true);
        $('#email').prop('disabled', true);
        $('#confirmEdit').addClass('hide');
        $('#confirmEdit').removeClass('show');
        $('#editInfo').addClass('show');
        $('#editInfo').removeClass('hide');
        var info = {
            teacherId: localStorage.getItem("teacherId"),
            teacherIdentifier: identifier,
            fullName: fullName,
            phone: phone,
            email: email
        }
        console.log(JSON.stringify(info));
        e.preventDefault();
        $.ajax({
            url: '/api/admin/editteacherinformation',
            type: 'POST',
            data: JSON.stringify(info),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                console.log(data);
                var messageCode = data.messageCode;
                var message = data.message;
                if (messageCode == 0) {
                    $('#editInfoSuccess').css('display', 'block');
                } else {
                    $('.teacherInfo-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.teacherInfo-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
    localStorage.removeItem("teacherId");
})