var identifier, fullName, phone, email;
var emailRegex = '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$';
var phoneRegex = '^[0-9\\-\\+]{9,15}$';

$("#submit").click(function (e) {
    fullName = $('#fullName').val().trim();
    identifier = $('#identifier').val().trim();
    phone = $('#phone').val().trim();
    email = $('#email').val().trim();

    if (identifier == "") {
        $('.createTeacher-err').text("Hãy điền tên định danh.");
        return false;
    } else if (phone != "" && !phone.match(phoneRegex)) {
        $('.createTeacher-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (email != "" && !email.match(emailRegex)) {
        $('.createTeacher-err').text("Email không đúng định dạng.");
        return false;
    } else {
        var teacher = {
            teacherIdentifier: identifier,
            fullName: fullName,
            phone: phone,
            email: email,
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/addteacher',
            type: 'POST',
            data: JSON.stringify(teacher),
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
                    $('#createSuccess .modal-body').html('');
                    $('#createSuccess .modal-body').append(`
                        <img class="mb-3 mt-3" src="https://img.icons8.com/material/100/007bff/ok--v1.png"/>
                        <h5>Thêm giáo viên thành công!</h5>
                    `);
                    $('.createTeacher-err').text("");
                } else {
                    $('.createTeacher-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.createTeacher-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});

