$(document).ready(function () {
    $.ajax({
        url: '/api/admin/violationandviolationtype',
        type: 'POST',
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            if (data.messageDTO.messageCode == 0) {
                if(data.listViolationType.length !=0){
                    $("#violationList").html("");
                    $.each(data.listViolationType, function (i, itemType) {
                        $('#violationList').append(`
                            <div class="panel-heading mt-3" data-toggle="collapse" data-target="#collapse`+itemType.typeId+`" onclick="toggleClick()">
                                <div>
                                    <div class="d-flex align-items-center"><h5 class="my-0 mr-2">Nội quy theo dõi: </h5>` + itemType.name + `</div>
                                    <div class="d-flex align-items-center"><h6 class="my-0 mr-2">Điểm:</h6>` + itemType.totalGrade + `</div>
                                </div>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="bt-table-field mx-5">
                                        <a href="editViolationType" class="bt-table-edit" title="Sửa" id="${itemType.typeId}">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                        </a>
                                    </span>
                                    <button><i class="fa fa-chevron-down rotate"></i></button>
                                </div>
                            </div>
                            <div class="panel-collapse collapse in" id="collapse`+itemType.typeId+`">
                                <table class="table table-hover">
                                    <thead>
                                    <th style="width: 5%"></th>
                                    <th>Vi phạm</th>
                                    <th>Điểm trừ</th>
                                    <th></th>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        `);
                    });

                    $.each(data.listViolationType, function (i, itemType) {
                        var typeId = "#collapse" + itemType.typeId + " tbody";
                        if (itemType.violation != null && itemType.violation.length != 0){
                            $.each(itemType.violation, function (j, itemVio) {
                                $(typeId).append(`
                                <tr>
                                    <th style="width: 5%"></th>
                                    <td>` + itemVio.description + `</td>
                                    <td>` + itemVio.substractGrade + `</td>
                                    <td><span>
                                            <a href="editViolation" title="Sửa" class="mx-2 bt-table-edit-vio" id="${itemVio.violationId}">
                                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                            </a>
                                            <a href="#" title="Xóa" class="mx-2">
                                                <i class="fa fa-trash" aria-hidden="true"></i>
                                            </a>
                                        </span></td>
                                </tr>
                            `);
                            });
                        }
                        else{
                            $(typeId).append(
                                `<tr>
                                    <td colspan="4" class="userlist-result">Danh sách lỗi vi phạm trống.</td>
                                </tr>`
                            )
                        }
                    });
                    getViolationTypeID();
                    getViolationID();
                }
            }
            else{
                $('#violationList').append(`
                    
                `);
            }
        },
        failure: function (errMsg) {
            $('#violationList').append(
                `<tr>
                    <td colspan="6" class="userlist-result">` + errMsg + ` </td>
                </tr>`
            )
        },
        dataType: "json",
        contentType: "application/json"
    });
});

function toggleClick() {
    $(".panel-heading").on('click', function () {
        $(this).find(".fa-chevron-down").toggleClass("up");
    })

}

/*Edit violation type by ID*/
function getViolationTypeID() {
    var typeId = $('.bt-table-edit');
    $(typeId).on('click', function (e) {
        typeId = $(this).prop('id');
        localStorage.setItem("violationTypeID", typeId);
    });
}

/*Edit violation by ID*/
function getViolationID() {
    var violationId = $('.bt-table-edit-vio');
    $(violationId).on('click', function (e) {
        violationId = $(this).prop('id');
        localStorage.setItem("violationId", violationId);
    });
}
