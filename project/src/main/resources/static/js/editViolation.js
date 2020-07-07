var violationID;
$(document).ready(function () {
    violationID = localStorage.getItem("violationId")
    var model = {
        violationId: violationID
    }
    if (violationTypeID == null) {
        $('.violation-err').append(`Hãy chọn vi phạm cần chỉnh sửa ở trang quản lý vi phạm.`);
        $("#editInfo").prop('disabled', true);
    } else {
        $("#editInfo").prop('disabled', false);
        $.ajax({
            url: '/api/admin/getviolation',
            type: 'POST',
            data: JSON.stringify(model),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                if (data.messageDTO.messageCode == 0) {
                    $('#description').attr('value', oldName);
                    $('#totalGrade').attr('value', oldTotalGrade);
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
    }
});