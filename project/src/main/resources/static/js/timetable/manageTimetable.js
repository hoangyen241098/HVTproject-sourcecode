var date;
$("#timetableform").submit(function (e) {
    //gửi date lên, nếu messagecode =0 hoạc confirm có thì gửi data lên
    e.preventDefault(); // avoid to execute the actual submit of the form.
    date = $('#date-input').val();
    var input = {
        date: date,
    }
    $.ajax({
        url: '/api/timetable/checkDate',
        type: 'POST',
        data: JSON.stringify(input),
        success: function (data) {
            console.log(data);
            if (data.messageCode == 1) {
                //chỗ này hiện dialog lỗi
                $('#overrideTimetableModal').modal('show');
                $('#overrideTimetableModal .modal-body').html('');
                $('#overrideTimetableModal .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>` + data.message + `</h5>
                `);
                $('#overrideTimetableModal .modal-footer').html('');
                $('#overrideTimetableModal .modal-footer').append(`
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="ĐÓNG">
                `);
                console.log(data.message);
            } else if (data.messageCode == 2) {
                //gọi dialog confirm có muốn ghi đè không nếu có thì gọi update();
                $('#overrideTimetableModal').modal('show');
                $('#overrideTimetableModal .modal-body').html('');
                $('#overrideTimetableModal .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>Ngày áp dụng của Thời khóa biểu đã tồn tại.</h5>
                <h5>Thời khóa biểu này sẽ bị ghi đè lên thời khóa biểu trước đó.</h5>
                <h5>Bạn có muốn ghi đè không?</h5>
                `);
                $('#overrideTimetableModal .modal-footer').html('');
                $('#overrideTimetableModal .modal-footer').append(`
                    <input type="submit" class="btn btn-danger" data-toggle="modal"
                           id="overrideTimetable" data-dismiss="modal" value="CÓ">
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="KHÔNG">
                `);
                console.log(data.message);
                $('#overrideTimetable').on('click', function () {
                    console.log('update')
                    update();
                })
            } else {
                update();
            }
        },
        failure: function (errMsg) {
            $('#overrideTimetableModal').modal('show');
            $('#overrideTimetableModal .modal-body').html('');
            $('#overrideTimetableModal .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>` + errMsg + `</h5>
            `);
            $('#overrideTimetableModal .modal-footer').html('');
            $('#overrideTimetableModal .modal-footer').append(`
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="ĐÓNG">
            `);
        },
        dataType: 'JSON',
        contentType: "application/json"
    });

});

function update() {
    var form = $('#timetableform');
    // var url = form.attr('action');
    var formData = new FormData(form[0]);
    console.log(formData)
    $.ajax({
        type: "POST",
        //url: "manageTimetable",
        url: "/api/timetable/update",
        data: formData,//form.serialize(), // serializes the form's elements.
        async: false,
        success: function (data) {
            console.log(data)
            if (data.messageCode == 0) {
                $('#overrideSuccess').modal('show');
                $('#overrideSuccess .modal-body').html('');
                $('#overrideSuccess .modal-body').append(`
                    <img class="mb-3 mt-3" src="https://img.icons8.com/material/100/007bff/ok--v1.png"/>
                    <h5>Thay đổi Thời khóa biểu thành công!</h5>
                `);
                $('#overrideSuccess .modal-footer').html('');
                $('#overrideSuccess .modal-footer').append(`
                    <a type="button" class="btn btn-primary" href="manageTimetable">ĐÓNG</a>
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="ĐÓNG">
                `);
            } else {
                $('#overrideSuccess').modal('show');
                $('#overrideSuccess .modal-body').html('');
                $('#overrideSuccess .modal-body').append(`
                    <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                    <h5>` + data.message + `</h5>
                `);
                $('#overrideSuccess .modal-footer').html('');
                $('#overrideSuccess .modal-footer').append(`
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="ĐÓNG">
                `);
            }
        },
        failure: function (errMsg) {
            $('#overrideSuccess').modal('show');
            $('#overrideSuccess .modal-body').html('');
            $('#overrideSuccess .modal-body').append(`
                <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                <h5>` + errMsg + `</h5>
            `);
            $('#overrideSuccess .modal-footer').html('');
            $('#overrideSuccess .modal-footer').append(`
                <input type="button" class="btn btn-primary" data-dismiss="modal" value="ĐÓNG">
            `);
        },
        cache: false,
        contentType: false,
        processData: false,
    });
}