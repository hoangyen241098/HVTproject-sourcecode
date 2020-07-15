$('#datetime').val(moment().format('YYYY-MM-DD'));
/*Get data in page*/
$.ajax({
    url: '/api/emulation/viewgradingemulation',
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
                $("#classList").html(`<option value="0" selected>Tất cả</option>`);
                $.each(data.classList, function (i, item) {
                    $('#classList').append(
                        `<option value="` + item.classID + `">` + item.className + `</option>
                    `);
                });
            } else {
                $("#classList").html(`<option>Không có lớp nào.</option>`);
            }
            if (data.vioTypeAndVioList != null) {
                $(".panel-default").html("");
                $.each(data.vioTypeAndVioList, function (i, item) {
                    var dataTarget = "collapse" + item.typeId;
                    $('.panel-default').append(
                        `<div class="panel-heading mt-3" data-toggle="collapse" data-target="#` + dataTarget + `" onclick="toggleClick()">
                             <div class="violationTypeName">
                                <span>
                                    <h5 class="my-0 mr-2 d-inline">Nội quy theo dõi: </h5>
                                    <span>` + item.name + `</span>
                                </span>
                                <div class="d-flex align-items-center">
                                    <h6 class="my-0 mr-2">Điểm trừ:</h6>
                                    <span>` + item.totalGrade + `</span>
                                </div>
                            </div>
                            <button><i class="fa fa-chevron-down rotate"></i></button>
                        </div>
                        <div class="panel-collapse collapse in" id="` + dataTarget + `">
                            <table class="table table-responsive-customer">
                                <thead>
                                    <th></th>
                                    <th>Vi phạm</th>
                                    <th>Điểm trừ</th>
                                    <th>Số lần</th>
                                    <th>Tổng điểm trừ</th>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    `);
                    if (item.violation != null) {
                        var dataTable = "#" + dataTarget + " tbody";
                        $(dataTable).html("");
                        $.each(item.violation, function (i, list) {
                            $(dataTable).append(`
                            <tr>
                                <td>
                                    <span class="custom-checkbox ">
                                        <input id="` + list.violationId + `" type="checkbox" name="options" value="` + list.violationId + `">
                                        <label for="` + list.violationId + `"></label>
                                    </span>
                                </td>
                                <td>
                                    <div class="form-group text-left">
                                        <label>` + list.description + `</label>
                                        <input type="text" class="form-control text-red" placeholder="Ghi chú...">
                                    </div>
                                </td>
                                <td class="substract">` + list.substractGrade + `</td>
                                <td>
                                    <div class="quantity d-flex">
                                        <button class="btn-decrease"><i class="fa fa-minus" aria-hidden="true"></i></button>
                                        <input class="quantity-input" type="number" value="0" min="0">
                                        <button class="btn-increase"><i class="fa fa-plus" aria-hidden="true"></i></button>
                                    </div>
                                </td>
                                <td class="total"></td>
                            </tr>`);
                        });
                    } else {

                    }
                });
            } else {
                $("#classList").html(`<option>Không có lớp nào.</option>`);
            }
        } else {
            $("#gifftedClass").html(`<option>` + message + `</option>`);
        }
    },
    failure: function (errMsg) {
        $("#gifftedClass").html(`<option>` + errMsg + `</option>`);
    },
    dataType: "json",
    contentType: "application/json"
});

$('.btn-increase').on('click', function () {
    var $qty = $(this).closest('div').find('.quantity-input');
    var currentVal = parseInt($qty.val());
    if (!isNaN(currentVal)) {
        $qty.val(currentVal + 1);
    }
    var $substract = $(this).closest('td').parent().find('.substract');
    var $total = $(this).closest('td').parent().find('.total');
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
});


$('.btn-decrease').on('click', function () {
    var $qty = $(this).closest('div').find('.quantity-input');
    var currentVal = parseInt($qty.val());
    if (!isNaN(currentVal) && currentVal > 0) {
        $qty.val(currentVal - 1);
    }
    var $substract = $(this).closest('td').parent().find('.substract');
    var $total = $(this).closest('td').parent().find('.total');
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
});
getTotalGrade();

function getTotalGrade() {
    var $this = $('tbody tr');
    var $substract = $this.find('.substract');
    var $total = $this.find('.total');
    var $qty = $this.find('.quantity-input')
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
}

