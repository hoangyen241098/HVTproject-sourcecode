var inforSearch = {
    fromDate: "2020/07/06",
    orderBy: "1",
    sortBy: "0",
    classId: "1",
    redStar: "",
    pageNumber: 0
}

$("#search").click(function () {
    fromDate = $('#fromDate').val();
    if ($('#gifftedClass option:selected').val() == null || $('#gifftedClass option:selected').val() == "0") {
        giftedId = "";
    } else {
        giftedId = $('#gifftedClass option:selected').val();
    }
    if ($('#fromYear').val() == null) {
        fromYear = "";
    } else {
        fromYear = $('#fromYear').val();
    }
    toYear = $('#toYear').val();
    inforSearch = {
        fromDate: fromDate,
        toDate: toDate,
        giftedId: giftedId,
        fromYear: fromYear,
        toYear: toYear
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
        // if (messageCode == 0) {
        if (data.listRedStar != null) {
            $("#redStarList").select2();
            $("#redStarList").html(`<option value="0" selected="selected">Tất cả</option>`);
            $.each(data.listRedStar, function (i, item) {
                $('#redStarList').append(
                    `<option value="` + item.username + `">` + item.username + `</option>
                    `);
            });
        } else {
            $("#redStarList").html(`<option>Danh sách sao đỏ trống.</option>`);
        }
        if (data.listClass != null) {
            $("#classList").select2();
            $("#classList").html(`<option value="0" selected="selected">Tất cả</option>`);
            $.each(data.listClass, function (i, item) {
                $('#classList').append(
                    `<option value="` + item.classId + `">` + item.classIdentifier + `</option>
                `);
            });
        } else {
            $("#classList").html(`<option>Danh sách lớp trống.</option>`);
        }
        if (data.listDate != null) {
            $("#fromDate").select2();
            $("#fromDate").html(`<option value="0" selected="selected">Tất cả</option>`);
            $.each(data.listDate, function (i, item) {
                $('#fromDate').append(
                    `<option value="` + item + `">` + item + `</option>
                    `);
            });
        } else {
            $("#fromDate").html(`<option>Danh sách ngày trống.</option>`);
        }
        // } else {
        //     $("#gifftedClass").html(`<option>` + message + `</option>`);
        // }
    },
    failure: function (errMsg) {
        $("#gifftedClass").html(`<option>` + errMsg + `</option>`);
    },
    dataType: "json",
    contentType: "application/json"
});
search();

/*Load data to list*/
function search() {
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
            if (messageCode == 0) {
                if (data.listAssignTask.length != 0) {
                    $.each(data.violationClassList, function (i, item) {
                        $('#myTable tbody').append(
                            `<tr>
                                <td>` + item.classIdentifier + `</td>
                                <td>` + item.redStar + `</td>
                            </tr>`
                        );
                    });
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