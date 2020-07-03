var editClassId, oldIdentifierName, oldStatus, newStatus;
$(document).ready(function () {
    editClassId = localStorage.getItem("classId")
    var classRequest = {
        classId: editClassId
    }
    $.ajax({
        url: '/api/admin/viewclassinfor',
        type: 'POST',
        data: JSON.stringify(classRequest),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            if (data.message.messageCode == 0) {
                oldIdentifierName = data.classIdentifier;
                $('#identifier').attr('value', data.classIdentifier);
                $('#giftedClassName').attr('value', data.giftedClassName);
                $('#grade').attr('value', data.grade);
                if (data.status != null && data.status == 1) {
                    $("input[name=optradio][value='1']").prop('checked', true);
                    oldStatus = 1;
                } else {
                    $("input[name=optradio][value='0']").prop('checked', true);
                    oldStatus = 0;
                }
            } else {
                $('tbody').append(
                    `<tr>
                    <td colspan="7" class="userlist-result">
                        ` + data.message.message + `
                    </td>
                </tr>`
                )
            }

        },
        failure: function (errMsg) {
            console.log(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
})

//edit information of class
$("#editInfo").click(function (e) {
    newStatus = 1;
    newClassIdentifier = $('#identifier').val().trim();
    var radioValue = $("input[name=optradio]:checked").val();
    if (radioValue == '0') {
        newStatus = 0;
    }

    if (newClassIdentifier == "") {
        $('.classInfo-err').text("Hãy nhập tên định danh!");
        return false;
    } else if (newStatus == oldStatus && newClassIdentifier == oldIdentifierName) {
        $('.classInfo-err').text("Hãy thay đổi thông tin !");
        return false;
    } else {
        if (newStatus != oldStatus) {
            $("#confirmEditModal .modal-body").html("");
            $('#confirmEditModal .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>Bạn có chắc muốn <b>CẬP NHẬT</b> trạng thái của lớp này không?</h5>
            `);
            $('#confirmEditModal .modal-footer .btn-danger').removeClass('hide');
            $('#confirmEditModal .modal-footer .btn-primary').attr('value', 'KHÔNG');
            $('#confirmEditModal').css('display', 'block');
            $('#editClassModal').click(function (e) {
                $('#confirmEditModal').css('display', 'none');
                editClass(e);
            })
            $('#closeModal').click(function (e) {
                $('#confirmEditModal').css('display', 'none');
            })
        } else {
            editClass(e);
        }
    }
});


function editClass(e) {
    var editClass = {
        classId: editClassId,
        classIdentifier: newClassIdentifier,
        status: newStatus.toString()
    }
    e.preventDefault();
    $.ajax({
        url: '/api/admin/editclass',
        type: 'POST',
        data: JSON.stringify(editClass),
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
                if(oldStatus != newStatus){
                    $('#editInfoSuccess .modal-body').append(`
                        <h5>Trạng thái của tài khoản cờ đỏ và lớp trưởng của lớp cũng đã thay đổi thành công.</h5>
                    `);
                }
                oldIdentifierName = newClassIdentifier;
                oldStatus = newStatus;
                $('#editInfoSuccess').css('display', 'block');
                $('.classInfo-err').text("");
            } else {
                $('.classInfo-err').text(message);
            }
        },
        failure: function (errMsg) {
            $('.classInfo-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}
