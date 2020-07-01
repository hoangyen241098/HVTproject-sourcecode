var grade, giftedClassId, classIdentifier, isRedStar, isMonitor;
gradeCombobox();
$.ajax({
    url: '/api/admin/giftedclasslist',
    type: 'POST',
    success: function (data) {
        $.each(data.giftedClassList, function (i, item) {
            $('#classIdentifier').append(`<option value="` + item.giftedClassId + `">` + item.name + `</option>`);
        });
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

function gradeCombobox() {
    $('#grade').append(`<option value="10">10</option>`);
    $('#grade').append(`<option value="11">11</option>`);
    $('#grade').append(`<option value="12">12</option>`);
}

function changeSelected() {
    grade = $('#grade option:selected').val();
    giftedClassId = $('#classIdentifier option:selected').val();
}

$("#submit").click(function (e) {
    isRedStar = false;
    isMonitor = false;
    classIdentifier = $('#identifier').val().trim();
    if ($('#isRedStar').is(":checked"))
    {
        isRedStar = true;
    }
    if ($('#isMonitor').is(":checked"))
    {
        isMonitor = true;
    }

    if (grade == 0 || grade == null) {
        $('.createClass-err').text("Hãy chọn khối lớp!");
        return false;
    } else if (giftedClassId == 0 || giftedClassId == null) {
        $('.createClass-err').text("Hãy chọn hệ chuyên!");
        return false;
    }
    else if(classIdentifier == ""){
        $('.createClass-err').text("Hãy nhập tên định danh!");
        return false;
    }
    else {
        var addClass = {
            classIdentifier: classIdentifier,
            grade: grade,
            giftedClassId: giftedClassId,
            isRedStar: isRedStar,
            isMonitor: isMonitor
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/addclass',
            type: 'POST',
            data: JSON.stringify(addClass),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                var messageCode = data.message.messageCode;
                var message = data.message.message;
                var listUserText = "";
                if (messageCode == 0) {
                    //$('#createSuccess').css('display', 'block');
                    if(data.userList.length != 0) {
                        $.each(data.userList, function (i, item) {
                            listUserText += item.username + " " + item.password;
                        });
                    }
                    $('.createClass-err').text(listUserText);
                } else {
                    $('.createClass-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.createClass-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});

