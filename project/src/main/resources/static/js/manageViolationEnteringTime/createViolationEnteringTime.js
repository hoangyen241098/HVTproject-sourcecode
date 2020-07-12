var list = [];

/*Get roleName list*/
$.ajax({
    url: '/api/admin/rolelist',
    type: 'POST',
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
            if (data.listRole != null) {
                $('#roleName').html('');
                $('#roleName').append(`
                    <option value="0" selected>Tất cả</option>
                `);
                $.each(data.listRole, function (i, list) {
                    $('#roleName').append(`
                        <option value="` + list.roleId + `" name="` + list.roleName + `">` + list.roleName + `</option>
                    `);
                });
            } else {
                $('#roleName').html('');
                $('#roleName').append(`
                    <option selected>Danh sách trống.</option>
                `);
            }
        } else {
            $('#roleName').html('');
            $('#roleName').append(`
                <option selected>` + message + `</option>
            `);
        }
    },
    failure: function (errMsg) {
        $('#roleName').html('');
        $('#roleName').append(`
            <option selected>` + errMsg + `</option>
        `);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Get all day*/
$.ajax({
    url: '/api/admin/getallday',
    type: 'POST',
    beforeSend: function () {
        $('body').addClass("loading")
    },
    complete: function () {
        $('body').removeClass("loading")
    },
    success: function (data) {
        var messageCode = data.messageDTO.messageCode;
        var message = data.messageDTO.message;
        if (messageCode == 0) {
            if (data.listDay != null) {
                $('#allDay').html('');
                $('#allDay').append(`
                    <h6>Các ngày áp dụng <span class="text-red">*</span></h6>
                `);
                $.each(data.listDay, function (i, list) {
                    $('#allDay').append(`
                        <div class="form-check text-left">
                            <span class="custom-checkbox">
                                <input type="checkbox" name="options" value="` + list.dayId + `">
                                <label for="` + list.dayId + `">` + list.dayName + `</label>
                            </span>
                        </div>
                    `);
                });
            } else {
                $('#allDay').html('');
                $('#allDay').append(`
                    <h6 class="text-red">Không có ngày áp dụng nào!</h6>
                `);
            }
        } else {
            $('#allDay').html('');
            $('#allDay').append(`
                <h6 class="text-red">` + message + `</h6>
            `);
        }
    },
    failure: function (errMsg) {
        $('#allDay').html('');
        $('#allDay').append(`
            <h6 class="text-red">` + errMsg + `</h6>
        `);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Add Entering Time*/
$('#submit').on('click', function () {
    var roleId = $('#roleName option:selected').val();
    var startTime = $('#timeToStart').val().trim();
    var endTime = $('#timeToEnd').val().trim();
    $('input[name=options]:checked').map(function () {
        list.push(parseInt($(this).val()));
    });
    if (roleId == 0 && startTime == "" &&
        endTime == "" && list.length == 0) {
        $('.createTime-err').text('Hãy điền đầy đủ tất cả các trường bắt buộc.')
        return false;
    } else if (roleId == null || roleId == 0 || roleId == "") {
        $('.createTime-err').text('Hãy chọn chức vụ.')
        return false;
    } else if (startTime == "") {
        $('.createTime-err').text('Hãy điền thời gian bắt đầu.')
        return false;
    } else if (endTime == "") {
        $('.createTime-err').text('Hãy điền thời gian kết thúc.')
        return false;
    } else if (list.length == 0) {
        $('.createTime-err').text('Hãy chọn ngày áp dụng.')
        return false;
    } else {
        var newTime = {
            roleId: roleId,
            listDayId: list,
            startTime: startTime + ":00",
            endTime: endTime + ":00"
        }
        console.log(JSON.stringify(newTime))
        $.ajax({
            url: '/api/admin/addenteringtime',
            type: 'POST',
            data: JSON.stringify(newTime),
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
                    $('.createTime-err').text('');
                    $('#createSuccess .modal-body').html('');
                    $('#createSuccess .modal-body').append(`
                        <img class="mb-3 mt-3" src="https://img.icons8.com/material/100/007bff/ok--v1.png"/>
                        <h5>Thêm thời gian chấm thành công!</h5>
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