/*Value defaul*/
$('#datetime').val(moment().format('YYYY-MM-DD'));
var roleId = localStorage.getItem('roleID');
var username = localStorage.getItem('username');
var infoSearch = {
    username: username,
    classId: 1,
    date: $('#datetime').val(),
    roleId: roleId
}
var editViolation = "";

/*Set data classList to combobox*/
$.ajax({
    url: '/api/admin/classlist',
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
            if (data.classList != null) {
                $("#classList").select2();
                $("#classList").html('');
                $.each(data.classList, function (i, item) {
                    if (item.classID == 1) {
                        $('#classList').append(`<option value="` + item.classID + `" selected="selected">` + item.className + `</option>`);
                    } else {
                        $('#classList').append(`<option value="` + item.classID + `">` + item.className + `</option>`);
                    }
                });
            } else {
                $("#classList").html(`<option selected>` + message + `</option>`);
            }
        } else {
            $("#classList").html(`<option selected>` + message + `</option>`);
        }
    },
    failure: function (errMsg) {
        $("#classList").html(`<option selected>` + errMsg + `</option>`);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Search button*/
$("#search").click(function () {
    var classId, date;
    date = $('#datetime').val();
    if ($('#classList option:selected').val() == null || $('#classList option:selected').val() == "") {
        classId = "";
    } else {
        classId = $('#classList option:selected').val();
    }
    infoSearch = {
        username: username,
        classId: classId,
        date: date,
        roleId: roleId
    }
    console.log(JSON.stringify(infoSearch))
    $(".violation-by-date").html("");
    search();

});

search();

/*Set data to container*/
function search() {
    $.ajax({
        url: '/api/emulation/viewviolationofclass',
        type: 'POST',
        data: JSON.stringify(infoSearch),
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
                if (data.viewViolationClassList != null) {
                    $(".violation-by-date").html("");
                    $.each(data.viewViolationClassList, function (i, item) {
                        var violationClassId, substractGrade, createBy, status, reason, quantityNew, totals;
                        var classId = item.classId;
                        var className = item.className;
                        var createDate = item.createDate;
                        var violationDate = item.dayName + " - " + convertDate(createDate);
                        var description = item.description;
                        var note = item.note;
                        var quantity = item.quantity;
                        var checkEdit = item.checkEdit;
                        if (item.violationClassRequest == null) {
                            violationClassId = item.violationClassId;
                            createBy = item.createBy;
                            status = item.status;
                            reason = null;
                            quantityNew = null;
                            substractGrade = item.substractGrade;
                            totals = parseFloat(parseFloat(substractGrade) * parseInt(quantity)).toFixed(1);
                        } else {
                            violationClassId = item.violationClassRequest.violationClassId;
                            createBy = item.violationClassRequest.createBy;
                            status = item.violationClassRequest.status;
                            reason = item.violationClassRequest.reason;
                            quantityNew = item.violationClassRequest.quantityNew;
                            substractGrade = item.violationClassRequest.substractGrade;
                            totals = parseFloat(parseFloat(substractGrade) * parseInt(quantity)).toFixed(1);
                        }
                        $('.violation-by-date').append(`
                            <div class="violation-description mt-3">
                                <div class="violation-date">
                                    <span>` + violationDate + ` - Lớp ` + className + `</span>
                                </div>
                                <div class="violation-details">
                                    <div class="violation-name">
                                        <span class="font-500">Mô tả lỗi: </span>
                                        <span>` + description + `</span>
                                    </div>
                                    <p class="violation-note my-0">
                                        <span class="font-500">Ghi chú: </span>
                                        <span>` + note + `</span></p>
                                </div>
                                <div class="violation-substract-grade">
                                    <span class="font-500">Điểm trừ: </span>
                                    <span>` + substractGrade + `</span>
                                </div>
                                <div class="violation-quantity">
                                    <span class="font-500">Số lần: </span>
                                    <span>` + quantity + `</span>
                                </div>
                                <div class="violation-total">
                                    <span class="font-500">Tổng điểm trừ: </span>
                                    <span>` + totals + `</span>
                                </div>
                                <div class="violation-action">
                                    <div class="hide violationClassId">` + violationClassId + `</div>
                                    <div class="hide classId">` + classId + `</div>
                                    <div class="hide className">` + className + `</div>
                                    <div class="hide createDate">` + createDate + `</div>
                                    <div class="hide violationDate">` + violationDate + `</div>
                                    <div class="hide createBy">` + createBy + `</div>
                                    <div class="hide status">` + status + `</div>
                                    <div class="hide description">` + description + `</div>
                                    <div class="hide note">` + note + `</div>
                                    <div class="hide substractGrade">` + substractGrade + `</div>
                                    <div class="hide quantity">` + quantity + `</div>
                                    <div class="hide quantityNew">` + quantityNew + `</div>
                                    <div class="hide reason">` + reason + `</div>
                                    <input type="button" class="btn btn-danger edit-btn" data-toggle="modal" value="CHỈNH SỬA"/>
                                </div>
                            </div>
                        `);
                        if (checkEdit == 0) {
                            $('.violation-action input').removeClass('hide');
                            $('.violation-action input').val('CHỈNH SỬA');
                        } else if (checkEdit == 2) {
                            $('.violation-action input').removeClass('hide');
                            $('.violation-action input').val('XEM YÊU CẦU CHỈNH SỬA');

                        } else if (checkEdit == 1) {
                            $('.violation-action input').addClass('hide');
                        }
                        editBtn();
                    });
                } else {
                    $(".violation-by-date").html(`<h3 class="text-center mt-3">` + message + `</h3>`);
                }
            } else {
                $(".violation-by-date").html(`<h3 class="text-center mt-3">` + message + `</h3>`);
            }
        },
        failure: function (errMsg) {
            $(".violation-by-date").html(`<h3 class="text-center mt-3">` + errMsg + `</h3>`);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

/*Edit button*/
function editBtn() {
    $('.edit-btn').on('click', function () {
        editViolation = "";
        var violationClassId = $(this).parent().find('.violationClassId').text();
        var classId = $(this).parent().find('.classId').text();
        var className = $(this).parent().find('.className').text();
        var createDate = $(this).parent().find('.createDate').text();
        var violationDate = $(this).parent().find('.violationDate').text();
        var createBy = $(this).parent().find('.createBy').text();
        var status = $(this).parent().find('.status').text();
        var description = $(this).parent().find('.description').text();
        var note = $(this).parent().find('.note').text();
        var substract = $(this).parent().find('.substractGrade').text();
        var quantity = $(this).parent().find('.quantity').text();
        var quantityNew = $(this).parent().find('.quantityNew').text();
        var reason = $(this).parent().find('.reason').text();
        var total = parseFloat(parseFloat(substract) * parseInt(quantity)).toFixed(1);

        if ($('.violation-action input').val() == 'CHỈNH SỬA') {
            editModal(violationDate, className, description, note, substract, quantity, total)
            var $total = $('.total');
            increaseBtn(substract, total, $total);
            decreaseBtn(substract, total, $total);

            $('#editModalBtn').on('click', function () {
                var newQuantity = $('.quantity-input').val();
                var reason = $('#reason').val().trim();
                $('.editInfo-err').text('');
                if (quantity == newQuantity) {
                    $('.editInfo-err').text('Hãy thay đổi thông tin.');
                    return false;
                } else if (reason == null || reason == "") {
                    $('.editInfo-err').text('Hãy điền lý do thay đổi.');
                    return false;
                } else {
                    $('.editInfo-err').text('');
                    editViolation = {
                        violationClassId: violationClassId,
                        username: username,
                        classId: classId,
                        editDate: moment().format('YYYY-MM-DD'),
                        createDate: createDate,
                        roleId: roleId,
                        newQuantity: newQuantity,
                        reason: reason,
                    }
                    console.log(JSON.stringify(editViolation));
                    editModalBtn(description, substract, note, reason, quantity, newQuantity);
                }
            });
        }
        if ($('.violation-action input').val() == 'XEM YÊU CẦU CHỈNH SỬA') {
            $('#confirmEdit .modal-title').text('Yêu cầu chỉnh sửa');
            $('#confirmEdit .modal-footer .btn-danger').val('HỦY YÊU CẦU CHỈNH SỬA');
            $('#confirmEdit .modal-footer .btn-danger').prop('id', 'confirmDeleteBtn');
            $('#confirmEdit .modal-footer .btn-primary').val('ĐÓNG');
            editModalBtn(description, substract, note, reason, quantity, quantityNew);
        }

    })
}

/*Edit modal template*/
function editModal(violationDate, className, description, note, substract, quantity, total) {
    $('#editModal').modal('show');
    $('#editModal .modal-body').html('')
    $('#editModal .modal-body').append(`
        <p class="font-500">` + violationDate + ` - Lớp ` + className + `</p>
        <div class="ml-3">
            <p class="mb-1">
                <span class="font-500">Mô tả lỗi: </span>
                <span>` + description + `</span>
            </p>
            <p class="mb-1">
                <span class="font-500">Ghi chú: </span>
                <span class="text-red">` + note + `</span>
            </p>
        </div>
        <div class="ml-3">
            <p class="mb-1">
                <span class="font-500">Điểm trừ: </span>
                <span class="substract">` + substract + `</span>
            </p>
        </div>
        <div class="ml-3">
            <p class="mb-1 d-flex align-items-center">
                <span class="font-500">Số lần: </span>
                <span class="quantity d-flex ml-3">
                    <a role="button" class="btn-decrease"><i class="fa fa-minus" aria-hidden="true"></i></a>
                    <input class="quantity-input" type="number" value="` + quantity + `" min="0">
                    <a role="button" class="btn-increase"><i class="fa fa-plus" aria-hidden="true"></i></a>
                </span>
            </p>
        </div>
        <div class="ml-3">
            <p class="mb-1">
                <span class="font-500">Tổng điểm trừ: </span>
                <span class="total">` + total + `</span>
            </p>
        </div>
        <div class="form-group mx-3">
            <label class="font-500" for="reason">Lý do: </label>
            <textarea type="text" class="form-control" id="reason" placeholder="Lý do..."></textarea>
            <span class="text-red editInfo-err"></span>
        </div>
    `)
}

/*Edit Modal Button*/
function editModalBtn(description, substract, note, reason, quantity, newQuantity) {
    var total = parseFloat(parseFloat(substract) * parseInt(quantity)).toFixed(1);
    var newTotal = parseFloat(parseFloat(substract) * parseInt(newQuantity)).toFixed(1);
    $('#confirmEdit').modal('show');
    $('#confirmEdit .modal-body').html('');
    $('#confirmEdit .modal-body').append(`
    <div class="panel-title text-left">
        <h6 class="violationName">` + description + `</h6>
        <h6 class="substract-grade">Điểm trừ: ` + substract + `</h6>
    </div>
    <div class="panel-body">
        <div class="note">
            <span class="title">Ghi chú: </span> 
            <span class="info ml-4">` + note + `</span>
        </div>
        <div class="reason-change">
            <span class="title">Lý do thay đổi: </span> 
            <span class="info ml-4">` + reason + `</span>
        </div>
        <div class="quantity">
            <span class="title">Số lần: </span> 
            <span class="info ml-4">` + quantity + ` -> <span class="text-red font-500">` + newQuantity + `</span></span>
        </div>
        <div class="totals">
            <span class="title">Tổng điểm trừ: </span> 
            <span class="info ml-4">` + total + ` -> <span class="text-red font-500">` + newTotal + `</span></span>
        </div>
    </div>
    `);
    $('#confirmEditBtn').on('click', function () {
        confirmEditBtn();
    })
}

/*Confirm Edit Button*/
function confirmEditBtn() {
    $.ajax({
        url: '/api/emulation/requesteditviolation',
        type: 'POST',
        data: JSON.stringify(editViolation),
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
                dialogModal("img/img-success.png", "Tạo yêu cầu thay đổi thành công!");
            } else {
                dialogModal("img/img-error.png", message);
            }
        },
        failure: function (errMsg) {
            dialogModal("img/img-error.png", errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

/*Set dialog template*/
function dialogModal(img, message) {
    $('#saveSuccess').modal('show');
    $('#saveSuccess .modal-body').html('');
    $('#saveSuccess .modal-body').append(`
        <img class="mb-3 mt-3" src=` + img + `/>
        <h5>` + message + `</h5>
    `);
}

/*Button Increase*/
function increaseBtn(substract, total, $total) {
    $('.btn-increase').on('click', function () {
        var $qty = $(this).closest('div').find('.quantity-input');
        var currentVal = parseInt($qty.val());
        if (!isNaN(currentVal)) {
            $qty.val(currentVal + 1);
        }
        total = parseFloat(parseFloat(substract) * parseInt($qty.val())).toFixed(1);
        $total.text(total);
    });
}

/*Button Decrease*/
function decreaseBtn(substract, total, $total) {
    $('.btn-decrease').on('click', function () {
        var $qty = $(this).closest('div').find('.quantity-input');
        var currentVal = parseInt($qty.val());
        if (!isNaN(currentVal) && currentVal > 0) {
            $qty.val(currentVal - 1);
        }
        total = parseFloat(parseFloat(substract) * parseInt($qty.val())).toFixed(1);
        $total.text(total);
    });
}