var fromDate;
var inforSearch = {
    fromDate: $('#fromDate option:selected').val(),
    orderBy: "0",
    sortBy: "0",
    classId: "",
    redStar: "",
    pageNumber: 0
};

$("#search").click(function () {
    var classId, redStar, sortBy, orderBy;
    fromDate = $('#fromDate option:selected').val();
    redStar = $('#redStarList').val().trim();
    if ($('#classList option:selected').val() == null || $('#classList option:selected').val() == "0") {
        classId = "";
    } else {
        classId = $('#classList option:selected').val();
    }
    if (redStar == null) {
        redStar = "";
    } else {
        redStar = $('#redStarList').val().trim();
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
    inforSearch = {
        fromDate: fromDate,
        orderBy: orderBy,
        sortBy: sortBy,
        classId: classId,
        redStar: redStar,
        pageNumber: 0
    }
    $('#violationList').html("");
    search();

});

/*Get giftedClass in combobox*/
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
            if (data.listClass != null) {
                $("#classList").select2();
                $("#classList").html(`<option value="0" selected="selected">Tất cả</option>`);
                $.each(data.listClass, function (i, item) {
                    $('#classList').append(
                        `<option value="` + item.classId + `">` + item.grade + ` ` + item.giftedClass.name + `</option>`
                    );
                });
            } else {
                $("#classList").html(`<option>Danh sách lớp trống.</option>`);
            }
            if (data.listDate != null) {
                $("#fromDate").select2();
                $("#fromDate").html("");
                $.each(data.listDate, function (i, item) {
                    if (i == 0) {
                        $('#fromDate').append(
                            `<option value="` + item + `" selected="selected">` + convertDate(item) + `</option>
                        `);
                    } else {
                        $('#fromDate').append(
                            `<option value="` + item + `">` + convertDate(item) + `</option>
                    `);
                    }
                });
            } else {
                $("#fromDate").html(`<option>Danh sách ngày trống.</option>`);
            }

        } else {
            $("#fromDate").html(`<option>` + message + `</option>`);
        }
    },
    failure: function (errMsg) {
        $("#fromDate").html(`<option>` + errMsg + `</option>`);
    },
    dataType: "json",
    contentType: "application/json"
});

search();

/*Load data to list*/
function search() {
    console.log(JSON.stringify(inforSearch));
    $.ajax({
        url: '/api/emulation/viewassigntask',
        type: 'POST',
        data: JSON.stringify(inforSearch),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            var messageCode = data.message.messageCode;
            var message = data.message.message;
            var totalPage = data.totalPage;
            if (totalPage == 0 || totalPage == null) {
                $('.table-paging').addClass('hide');
            } else {
                $('.table-paging').removeClass('hide');
                $('.table-paging').html('');
                paging(inforSearch, totalPage);
            }
            if (messageCode == 0) {
                if (data.listAssignTask.length != 0) {
                    $('#myTable tbody').html("");
                    $.each(data.listAssignTask, function (i, item) {
                        var classIdentifier, redStar;
                        if (item.className == "" || item.className == null) {
                            classIdentifier = "-";
                        } else {
                            classIdentifier = item.className;
                        }
                        if (item.redStar == "" || item.redStar == null) {
                            redStar = "-";
                        } else {
                            redStar = item.redStar;
                        }

                        $('#myTable tbody').append(`
                        <tr>
                            <td>` + classIdentifier + `</td>
                            <td>` + redStar + `</td>
                        </tr>
                        `);
                    });
                    pagingClick();
                } else {
                    $('#myTable tbody').html(`<tr><td colspan="2" class="userlist-result">Danh sách trống.</td></tr>`);
                }
            } else {
                $('#myTable tbody').html(`<tr><td colspan="2" class="userlist-result">` + message + `</td></tr>`);
            }
        },
        failure: function (errMsg) {
            $('#myTable tbody').html(`<tr><td colspan="2" class="userlist-result">` + errMsg + `</td></tr>`);
        },
        dataType: "json",
        contentType: "application/json"
    });
}