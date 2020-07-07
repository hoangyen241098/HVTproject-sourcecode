var violationTypeID, oldName, oldTotalGrade, newName, newTotalGrade;
$(document).ready(function () {
    violationTypeID = localStorage.getItem("violationTypeID")
    var editRequest = {
        typeId: violationTypeID
    }
    if (violationTypeID == null) {
        $('.violation-err').append(`Hãy chọn vi phạm cần chỉnh sửa ở trang quản lý vi phạm.`);
        $("#editInfo").prop('disabled', true);
    } else {
        $("#editInfo").prop('disabled', false);
        $.ajax({
            url: '/api/admin/getviolationtype',
            type: 'POST',
            data: JSON.stringify(editRequest),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                if (data.messageDTO.messageCode == 0) {
                    oldName = data.name;
                    oldTotalGrade = data.totlaGrade;
                    $('#description').attr('value', oldName);
                    $('#totalGrade').attr('value', oldTotalGrade);
                } else {
                $('.violation-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.violation-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});

$("#editInfo").click(function (e) {
    newName = $('#description').val().trim();
    newTotalGrade = $('#totalGrade').val().trim();

    if (newName == "") {
        $('.violation-err').text("Hãy nhập mô tả nội quy!");
        return false;
    } else if (newTotalGrade == "") {
        $('.violation-err').text("Hãy nhập điểm cho nội quy!");
        return false;
    }else if (newTotalGrade == oldTotalGrade && newName == oldName) {
        $('.violation-err').text("Hãy thay đổi thông tin!");
        return false;
    } else {
        $('.violation-err').text("");
        $("#confirmEditModal .modal-body").html("");
        $('#confirmEditModal .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>Bạn có chắc muốn <b>CẬP NHẬT</b> thông tin của nội quy này không?</h5>
        `);
        $('#confirmEditModal').addClass('fade');
        $('#editInfo').attr('data-target', '#confirmEditModal');
        $("#editModal").click(function (e) {
            $('#confirmEditModal').addClass('hide');
            editViolationType(e);
        });
    }
});

function editViolationType(e){
    var model ={
        typeId : violationTypeID,
        name : newName,
        totlaGrade : newTotalGrade
    }
    e.preventDefault();
    $.ajax({
        url: '/api/admin/editviolationtype',
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
                oldTotalGrade = newTotalGrade;
                oldName = newName;
                $('#editInfoSuccess').css('display', 'block');
                $('.violation-err').text("");
            } else {
                $('.violation-err').text(message);
            }
        },
        failure: function (errMsg) {
            $('.violation-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}
