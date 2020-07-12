$(document).ready(function () {
    $.ajax({
        url: '/api/admin/getlistviolationtype',
        type: 'POST',
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            $.each(data.violationTypeList, function (i, item) {
                $('#violationTypeName').append(`<option value="` + item.typeId + `">` + item.name + `</option>`);
            });
        },
        failure: function (errMsg) {
            $('.addViolation-err').text(errMsg);
        },
        dataType: 'JSON',
        contentType: "application/json"
    });
});

$("#submit").click(function (e) {
    var newTypeId = $('#violationTypeName').find('option:selected').val();
    var newDescription = $('#description').val().trim();
    var newSubstractGrade = $('#substractGrade').val().trim();

    if (newTypeId == null || newTypeId == 0) {
        $('.addViolation-err').text("Hãy lựa chọn nội quy!");
        return false;
    }else if(newDescription == ""){
        $('.addViolation-err').text("Hãy nhập mô tả lỗi vi phạm!");
        return false;
    }else if(newSubstractGrade == ""){
        $('.addViolation-err').text("Hãy nhập điểm trừ của lỗi vi phạm!");
        return false;
    }else if(newSubstractGrade <= 0){
        $('.addViolation-err').text("Hãy nhập điểm trừ của lỗi vi phạm lớn hơn 0!");
        return false;
    }else {
        var model = {
            typeId: newTypeId,
            description: newDescription,
            substractGrade: newSubstractGrade
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/addviolation',
            type: 'POST',
            data: JSON.stringify(model),
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
                    $('#createSuccess .modal-body').append(`
                        <img class="mb-3 mt-3" src="https://img.icons8.com/material/100/007bff/ok--v1.png"/>
                        <h5>Tạo lỗi vi phạm thành công!</h5>
                    `);
                    $('#createSuccess').css('display', 'block');
                    $('.addViolation-err').text("");
                } else {
                    $('.addViolation-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.addViolation-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});