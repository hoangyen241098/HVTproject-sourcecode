localStorage.removeItem("classId");
var inforSearch = {
    classIdentifier: "",
    grade: "",
    sortBy: "1",
    orderBy: "0",
    status: "",
    pageNumber: 0
}

search();
/*Search button*/
$("#search").click(function () {
    var grade, classIdentifier, sortBy, orderBy, status;
    classIdentifier = $('#searchByIdentifier input').val().trim();
    if ($('#searchByGrade option:selected').val() == null || $('#searchByGrade option:selected').val() == "0") {
        grade = "";
    } else {
        grade = $('#searchByGrade option:selected').val();
    }
    if ($('#sortBy option:selected').val() == null) {
        sortBy = "1";
    } else {
        sortBy = $('#sortBy option:selected').val();
    }
    if ($('#orderBy option:selected').val() == null) {
        orderBy = "0";
    } else {
        orderBy = $('#orderBy option:selected').val();
    }
    if ($('#status option:selected').val() == null) {
        status = "";
    } else {
        status = $('#status option:selected').val();
    }
    inforSearch = {
        classIdentifier: classIdentifier,
        grade: grade,
        sortBy: sortBy,
        orderBy: orderBy,
        status: status,
        pageNumber: 0
    }
    $('tbody').html("");
    $('.table-paging').html("");
    search();

});

/*Load class list*/
function search() {
    $("#myTable").DataTable({
        destroy: true,
        searching: false,
        bInfo: false,
        paging: false,
        ajax: {
            url: "/api/admin/classtable",
            type: "POST",
            data: function (d) {
                return JSON.stringify(inforSearch);
            },
            dataType: "json",
            contentType: "application/json",
            failure: function (errMsg) {
                $('tbody').append(
                    `<tr>
                        <td colspan="6" class="userlist-result"> ` + errMsg + ` </td>
                    </tr>`
                )
            },
            dataSrc: function (data) {
                var dataSrc = null;
                var messageCode = data.message.messageCode;
                var message = data.message.message;
                if (messageCode == 0) {
                    var totalPages = data.classList.totalPages;
                    paging(inforSearch, totalPages);
                    if (data.classList != null) {
                        dataSrc = data.classList.content;
                    } else {

                        return false;
                    }
                    pagingClick();
                } else {
                    $('tbody').append(
                        `<tr>
                            <td colspan="6" class="userlist-result"> ` + message + ` </td>
                        </tr>`
                    )
                    return false;
                }
                return dataSrc;
            }
        },
        columns: [
            {data: "grade"},
            {data: "giftedClass.name"},
            {data: "classIdentifier"},
            {
                data: "status",
                sortable: false,
                render: function (data, type, full, meta) {
                    var status;
                    if (data == null || data == 0) {
                        status = `<span class="status-active"><i class="fa fa-circle" aria-hidden="true"></i></span>`;
                    } else {
                        status = `<span class="status-deactive"><i class="fa fa-circle" aria-hidden="true"></i></span>`;
                    }
                    return status;
                }
            },
            {
                data: "classId",
                sortable: false,
                render: function (data, type, full, meta) {
                    var btn = `<span class="bt-table-field">
                                    <a href="editClass" id="${data}" class="bt-table-edit">
                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                    </a>
                                </span>`
                    getClassID();
                    return btn;
                }
            }
        ],
        drawCallback: function (settings) {
            settings.oLanguage.sEmptyTable = "Không có dữ liệu."
        }
    });

}

/*Edit class information by ID*/
function getClassID() {
    var classId = $('.bt-table-edit');
    $(classId).on('click', function (e) {
        classId = $(this).prop('id');
        localStorage.setItem("classId", classId);
    });
}