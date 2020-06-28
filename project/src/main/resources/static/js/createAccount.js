var fullName, userName, passWord, roleId, phone, email, classId;
var current_fs, next_fs, previous_fs;
var left, opacity, scale;
var emailRegex = '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$';
var phoneRegex = '^[0-9\\-\\+]{9,15}$';
/*Call API for Role List*/
$('.createAccount-err').text("");
$.ajax({
    url: '/api/admin/rolelist',
    type: 'POST',
    dataType: 'JSON',
    success: function (data) {
        $.each(data, function (i, item) {
            $.each(item, function (i, list) {
                $('#position-role').append(`<option value="` + list.roleId + `">` + list.roleName + `</option>`);
            });
        });
    },
    failure: function (errMsg) {
        $('.createAccount-err').text(errMsg);
    }
});
/*Call API for Class List*/
// $.ajax({
//     url: '/api/admin/classlist',
//     type: 'POST',
//     dataType: 'JSON',
//     success: function (data) {
//         $.each(data, function (i, item) {
//             $.each(item, function (i, list) {
//                 $('#class').append(`<option value="` + list.classID + `">` + list.className + `</option>`);
//             });
//         });
//     },
//     failure: function (errMsg) {
//         $('.createAccount-err').text(errMsg);
//     }
// });

function changeSelected() {
    classId = null;
    roleId = $('#position-role option:selected').val();
    if (roleId == 3 || roleId == 4) {
        $('#position-class').removeClass('hide');
    } else {
        $('#position-class').addClass('hide');
    }
    classId = $('#class option:selected').val();
    console.log(roleId, classId);
}

$("#next").click(function () {
    $('.createAccount-err').text("");

    var animating;
    if (animating) return false;
    animating = true;

    current_fs = $(this).parent();
    next_fs = $(this).parent().next();

    // /*Validate*/
    if (roleId == 0 || roleId == null) {
        $('.createAccount-err').text("Hãy chọn chức vụ.");
        return false;
    } else if (roleId == 3 && classId == 0 || roleId == 4 && classId == 0) {
        $('.createAccount-err').text("Hãy chọn lớp.");
        return false;
    } else {
        if (roleId == 1 || roleId == 2 || roleId == 5) {
            $('.fullName').removeClass('hide');
            $('.full-info').removeClass('hide');
            $('#username').prop('disabled', false);
            classId = null;

        } else if (roleId == 6) {
            $('.fullName').removeClass('hide');
            $('.full-info').addClass('hide');
            $('#username').prop('disabled', false);
            classId = null;
        } else {
            $('.fullName').addClass('hide');
            $('.full-info').addClass('hide');
            $('#username').prop('disabled', true);
        }

        $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
        next_fs.show();
        current_fs.animate({opacity: 0}, {
            step: function (now, mx) {
                scale = 1 - (1 - now) * 0.2;
                left = (now * 50) + "%";
                opacity = 1 - now;
                current_fs.css({
                    'transform': 'scale(' + scale + ')',
                    'position': 'absolute'
                });
                next_fs.css({'left': left, 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                current_fs.hide();
                animating = false;
            },
        });
    }
});

$("#submit").click(function (e) {
    fullName = $('#fullName').val();
    userName = $('#username').val();
    passWord = $('#password').val();
    var confirmPassword = $('#confirm-password').val();
    phone = $('#phone').val();
    email = $('#email').val();
    if (fullName.trim() == "") {
        $('.createAccount-err').text("Hãy điền họ và tên.");
        return false;
    }
    if (userName.trim() == "") {
        $('.createAccount-err').text("Hãy điền tên đăng nhập.");
        return false;
    } else if (passWord.trim() == "") {
        $('.createAccount-err').text("Hãy điền mật khẩu.");
        return false;
    } else if (confirmPassword.trim() == "") {
        $('.createAccount-err').text("Hãy xác nhận lại mật khẩu.");
        return false;
    } else if (passWord != confirmPassword) {
        $('.createAccount-err').text("Mật khẩu xác nhận không đúng.");
        return false;
    } else if (!phone.match(phoneRegex)) {
        $('.createAccount-err').text("SĐT không đúng định dạng.");
        return false;
    } else if (!email.match(emailRegex)) {
        $('.createAccount-err').text("Email không đúng định dạng.");
        return false;
    } else {
        var account = {
            userName: userName,
            passWord: passWord,
            fullName: fullName,
            roleId: roleId,
            phone: phone,
            email: email,
            classId: classId
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/createaccount',
            type: 'POST',
            data: JSON.stringify(account),
            success: function (data) {
                var messageCode = data.messageCode;
                var message = data.message;
                if (messageCode == 0) {
                    $('#createSuccess').css('display', 'block');
                    $('.createAccount-err').text("");
                } else {
                    $('.createAccount-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.createAccount-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });

    }
});

$(".previous").click(function () {
    var animating;
    if (animating) return false;
    animating = true;

    current_fs = $(this).parent();
    previous_fs = $(this).parent().prev();

    $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
    previous_fs.show();
    current_fs.animate({opacity: 0}, {
        step: function (now, mx) {
            scale = 0.8 + (1 - now) * 0.2;
            left = ((1 - now) * 50) + "%";
            opacity = 1 - now;
            current_fs.css({'left': left});
            previous_fs.css({'transform': 'scale(' + scale + ')', 'opacity': opacity, 'position': 'relative'});
        },
        duration: 800,
        complete: function () {
            current_fs.hide();
            animating = false;
        },
    });
});

