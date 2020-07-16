var fromDate;
var inforSearch = {
    fromDate: "2020-10-11",
    orderBy: "1",
    sortBy: "0",
    classId: "",
    redStar: "",
    pageNumber: 0
};

$("#search").click(function () {
    var classId, redStar;
    fromDate = $('#fromDate option:selected').val();
    if ($('#classList option:selected').val() == null || $('#classList option:selected').val() == "0") {
        classId = "";
    } else {
        classId = $('#classList option:selected').val();
    }
    if ($('#redStarList option:selected').val() == null || $('#redStarList option:selected').val() == "0") {
        redStar = "";
    } else {
        redStar = $('#redStarList option:selected').val();
    }
    inforSearch = {
        fromDate: fromDate,
        orderBy: "1",
        sortBy: "0",
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
                $("#fromDate").html("");
                $.each(data.listDate, function (i, item) {
                    if (i == 0) {
                        $('#fromDate').append(
                            `<option value="` + item + `" selected="selected">` + item + `</option>
                        `);
                    } else {
                        $('#fromDate').append(
                            `<option value="` + item + `">` + item + `</option>
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
            if (messageCode == 0) {
                if (data.listAssignTask.length != 0) {
                    $('#myTable tbody').html("");
                        $('#myTable').DataTable({
                            bFilter: false,
                            bInfo: false,
                            paging: false,
                            data: data.listAssignTask,
                            columns: [
                                {data: "classIdentifier"},
                                {data: "redStar"},
                            ]
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