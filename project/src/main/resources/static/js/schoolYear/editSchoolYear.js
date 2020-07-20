var schoolYearId, oldFromYear, oldFromDate, oldToDate;
$(document).ready(function () {
    schoolYearId = sessionStorage.getItem("schoolYearId")
    var info = {
        schoolYearId: schoolYearId
    }
    if (schoolYearId == null) {
        $('.editSchoolYear-err').append(`Hãy chọn năm học cần chỉnh sửa <a href="schoolYearList">TẠI ĐÂY</a>.`);
        $("#editInfo").prop('disabled', true);
    } else {
        $("#editInfo").prop('disabled', false);
        $.ajax({
            url: '/api/admin/getschoolyear',
            type: 'POST',
            data: JSON.stringify(info),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                var messageCode = data.message.messageCode;
                var message = data.message.message;
                if (messageCode == 0) {
                    oldFromYear = data.fromYear;
                    oldToYear = data.toYear;
                    oldFromDate = data.fromDate;
                    oldToDate = data.toDate;
                    $('#fromYear').val(oldFromYear);
                    $('#toYear').val(oldToYear);
                    $('#fromDate').val(oldFromDate);
                    $("#toDate").val(oldToDate);
                } else {
                    $('#editSchoolYear-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('#editSchoolYear-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});

$(document).change(function () {
    limitedDate();
});

$("#editInfo").click(function (e) {
    var fromYear = $('#fromYear').val().trim();
    var toYear = $('#toYear').val().trim();
    var fromDate = $('#fromDate').val().trim();
    var toDate = $('#toDate').val().trim();

    if (fromYear == "") {
        $('.editSchoolYear-err').text("Hãy điền năm học.");
        return false;
    } else if (fromDate == "") {
        $('.editSchoolYear-err').text("Hãy điền thời gian bắt đầu năm học.");
        return false;
    } else if (toDate == "") {
        $('.editSchoolYear-err').text("Hãy điền thời gian kết thúc năm học.");
        return false;
    } else if (fromYear == oldFromYear && fromDate == oldFromDate && toDate == oldToDate) {
        $('.editSchoolYear-err').text("Hãy thay đổi thông tin.");
        return false;
    } else {
        var newInfo = {
            schoolYearId: schoolYearId,
            fromDate: fromDate,
            toDate: toDate,
            fromYear: fromYear,
            toYear: toYear
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/editschoolyear',
            type: 'POST',
            data: JSON.stringify(newInfo),
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
                    $('.editSchoolYear-err').text("");
                    $('#editInfoSuccess').modal('show');
                } else {
                    $('.editSchoolYear-err').text(message);
                }
            },
            failure: function (errMsg) {
                $('.editSchoolYear-err').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});