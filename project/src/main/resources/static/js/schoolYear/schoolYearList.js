// $(document).ready(function() {
//     $('#tableSchoolYear').DataTable();
// } );
sessionStorage.removeItem('schoolId');
$.ajax({
    url: '/api/admin/schoolyearlist',
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
            if (data.schoolYearList != null) {
                var id = 0;
                $('tbody').html("");
                $.each(data.schoolYearList, function (i, item) {
                    var fromDate, toDate, yearName;
                    var yearId = item.yearId;
                    id += 1;
                    if (item.fromDate == null) {
                        fromDate = "-";
                    } else {
                        fromDate = item.fromDate;
                    }
                    if (item.toDate == null) {
                        toDate = "-";
                    } else {
                        toDate = item.toDate;
                    }
                    if (item.yearName == null) {
                        yearName = "-";
                    } else {
                        yearName = item.yearName;
                    }

                    $('tbody').append(
                        `<tr>
                            <td><span>` + id + `</span></td>
                            <td><span id="yearName">` + yearName + `</span></td>
                            <td><span id="fromDate">` + fromDate + `</span></td>
                            <td><span id="toDate">` + toDate + `</span></td>
                            <td><span id="action">
                                <a href="editSchoolYear" title="Sửa" class="mx-2" name="` + id + `" id="btnEdit">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                </a>
                                <a href="#deleteModal" title="Xóa" class="mx-2" name="` + id + `" 
                                data-toggle="modal" onclick="deleteYear()">
                                    <i class="fa fa-trash" aria-hidden="true"></i>
                                </a>
                            </span></td>
                        </tr>
                    `);
                });
            }
        } else {
            $('tbody').html("");
            $('tbody').append(
                `<tr>
                    <td colspan="5" class="userlist-result">` + message + `</td>
                </tr>`
            )
        }
        getschoolId();
        deleteYear();
    },
    failure: function (errMsg) {
        $('tbody').html("");
        $('tbody').append(
            `<tr>
                <td colspan="5" class="userlist-result">` + errMsg + ` </td>
            </tr>`
        )
    },
    dataType: "json",
    contentType: "application/json"
});

/*Get schoolYear ID*/
function getschoolId() {
    var schoolId = $('#action a');
    $(schoolId).on('click', function (e) {
        schoolId = $(this).prop('name');
        sessionStorage.setItem('schoolId', schoolId);
    });
}

/*Delete School Year*/
function deleteYear() {
    var schoolId = $('#action a');
    $(schoolId).on('click', function (e) {
        schoolId = $(this).prop('name');
    });
    $('#deleteModal .modal-body').html('');
    $('#deleteModal .modal-body').append(`
        <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
        <h5>Bạn có muốn <b>XÓA</b> năm học này không?</h5>
    `);

    $('#btnDelete').on('click', function () {
        var schoolYear = {
            schoolId: schoolId
        }
        console.log(JSON.stringify(schoolYear))
        $.ajax({
            url: '/api/admin/delschoolyear',
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
                    $("#deleteSuccess .modal-body").html("");
                    $('#deleteSuccess .modal-body').append(`
                    <img class="mb-3 mt-3" src="https://img.icons8.com/material/100/007bff/ok--v1.png"/>
                    <h5 id="message-delete">Xóa năm học thành công.</h5>
                `);
                } else {
                    $("#deleteSuccess .modal-body").html("");
                    $('#deleteSuccess .modal-body').append(`
                    <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                    <h5>` + message + `</h5>
                `);
                }
            },
            failure: function (errMsg) {
                $("#deleteSuccess .modal-body").html("");
                $('#deleteSuccess .modal-body').append(`
                    <img class="mb-3 mt-3" src="https://img.icons8.com/flat_round/100/000000/error--v1.png"/>
                    <h5>` + errMsg + `</h5>
            `);
            },
            dataType: "json",
            contentType: "application/json"
        });
    })
}




