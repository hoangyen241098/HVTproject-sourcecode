$.ajax({
    url: '/api/emulation/liststarclassdate',
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
                    schoolYearId = item.schoolYearId;
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
                                <a href="editSchoolYear" title="Sửa" class="mx-2 btnEdit" name="` + schoolYearId + `">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                </a>
                                <a href="#deleteModal" title="Xóa" class="mx-2 btnDelete" name="` + schoolYearId + `" data-toggle="modal">
                                    <i class="fa fa-trash" aria-hidden="true"></i>
                                </a>
                            </span></td>
                        </tr>
                    `);
                });
                $('#tableSchoolYear').DataTable({
                    lengthMenu: [30],
                    bLengthChange: false,
                    bFilter: false,
                    bInfo: false,
                    paging: false,
                });
                getSchoolYearId();
                deleteYear();
            }
        } else {
            $('tbody').html("");
            $('tbody').append(
                `<tr>
                    <td colspan="5" class="userlist-result">` + message + `</td>
                </tr>`
            )
        }
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