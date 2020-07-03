$(document).ready(function () {
    var teacherId = localStorage.getItem("teacherId");
    $('.teacherInfo-err').text("");
    var teacher = {
        teacherId: teacherId,
    }
    if(teacherId == null){
        $('.teacherInfo-err').append(`Hãy quay lại chọn giáo viên mà bạn muốn sửa thông tin tại <a href="manageTeacher">ĐÂY</a>`);
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
            $('#identifier').attr('value', data.teacherIdentifier);
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

$("#editInfo").click(function (e) {
    var emailRegex = '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$';
    var phoneRegex = '^[0-9\\-\\+]{9,15}$';
    var identifier = $('#identifier').val().trim();
    var fullName = $('#fullName').val().trim();
    var phone = $('#phone').val().trim();
    var email = $('#email').val().trim();
    $('.teacherInfo-err').text("");
    if (fullName == "") {
        $('.teacherInfo-err').text("Hãy điền họ và tên.");
        return false;
    } else if (identifier == "") {
        $('.teacherInfo-err').text("Hãy điền tên định danh.");
        return false;
    } else if (phone != "" && !phone.match(phoneRegex)) {
        $('.teacherInfo-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (email != "" && !email.match(emailRegex)) {
        $('.teacherInfo-err').text("Email không đúng định dạng.");
        return false;
    } else {
        $('.teacherInfo-err').text("");
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
                    localStorage.removeItem("teacherId");
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
})