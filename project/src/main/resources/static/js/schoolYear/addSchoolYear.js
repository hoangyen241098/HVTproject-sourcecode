$(document).change(function () {
    limitedDate();
});

$(document).ready(function () {
    limitedDate();
    $('#submit').on('click', function () {
        if ($('#fromYear').val().trim() == "" &&
            $('#fromDate').val().trim() == "" &&
            $('#toDate').val().trim() == "") {
            $('.addSchoolYear-err').text('Hãy điền đầy đủ các trường bắt buộc.');
            return false;
        } else if ($('#fromYear').val().trim() == "") {
            $('.addSchoolYear-err').text('Hãy điền năm học.');
            return false;
        } else if ($('#fromDate').val().trim() == "" &&
            $('#toDate').val().trim() == "") {
            $('.addSchoolYear-err').text('Hãy điền thời gian.');
            return false;
        } else if ($('#fromDate').val().trim() == "") {
            $('.addSchoolYear-err').text('Hãy điền thời gian bắt đầu năm học.');
            return false;
        } else if ($('#toDate').val().trim() == "") {
            $('.addSchoolYear-err').text('Hãy điền thời gian kết thúc năm học.');
            return false;
        } else {
            var schoolYear = {
                fromDate: $('#fromDate').val(),
                toDate: $('#toDate').val(),
                fromYear: $('#fromYear').val(),
                toYear: $('#toYear').val(),
            }
            console.log(JSON.stringify(schoolYear))
            $('#createSuccess').addClass('fade');
            $('#submit').attr('data-target', '#createSuccess');
            $.ajax({
                url: '/api/admin/addschoolyear',
                type: 'POST',
                data: JSON.stringify(schoolYear),
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
                            <h5>Thêm năm học mới thành công!</h5>
                        `);
                    } else {
                        $('#createSuccess .modal-body').html('');
                        $('#createSuccess .modal-body').append(`
                            <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                            <h5>` + message + `</h5>
                        `);
                    }
                },
                failure: function (errMsg) {
                    $('#createSuccess .modal-body').html('');
                    $('#createSuccess .modal-body').append(`
                        <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                        <h5>` + errMsg + `</h5>
                    `);
                },
                dataType: "json",
                contentType: "application/json"
            });

        }
    })
})
