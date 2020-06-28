var fullName, username, password, roleID, phone, email, className;
var current_fs, next_fs, previous_fs;
var left, opacity, scale;
var animating;

$('.errMsg').text("");
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
        console.log(errMsg);
    }
});

function changeSelected() {
    className = 0;
    roleID = $('#position-role option:selected').val();
    if (roleID == 3 || roleID == 4) {
        $('#position-class').removeClass('hide');
    } else {
        $('#position-class').addClass('hide');
    }
    className = $('#class option:selected').val();
    console.log(roleID, className);
}

$("#next").click(function () {
    $('.errMsg').text("");

    if (animating) return false;
    animating = true;

    current_fs = $(this).parent();
    next_fs = $(this).parent().next();

    // /*Validate*/
    if (roleID == 0 || roleID == null) {
        $('.errMsg').text("Hãy chọn chức vụ.");
        return false;
    } else if (roleID == 3 && className == 0 || roleID == 4 && className == 0) {
        $('.errMsg').text("Hãy chọn lớp.");
        return false;
    } else {
        if (roleID == 1 || roleID == 2 || roleID == 5) {
            $('.fullName').removeClass('hide');
            $('.full-info').removeClass('hide');

        } else if (roleID == 6) {
            $('.fullName').removeClass('hide');
        } else {
            $('.fullName').addClass('hide');
            $('.full-info').addClass('hide');
            $('.username').prop('disabled', true);
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

// $(".submit").click(function () {
//     var account = {
//         fullName, username, password, roleID, phone, email, className
//     }
//     e.preventDefault();
//     $.ajax({
//         url: '/api/admin/createaccount',
//         type: 'POST',
//         dataType: JSON.stringify(account),
//         success: function (data) {
//             $('.errMsg').text("Tài khoản đã được tạo thành công!");
//         },
//         failure: function (errMsg) {
//             console.log(errMsg);
//         }
//     });
// });

$(".previous").click(function () {
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

