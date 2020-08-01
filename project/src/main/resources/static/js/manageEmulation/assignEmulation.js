/*Get giftedClass in combobox*/
$.ajax({
    url: '/api/assignRedStar/liststarclassdate',
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
        if (messageCode == 0 || messageCode == 1) {
            if (data.listClass.length != 0) {
                $("#classList").select2();
                $("#classList").html(`<option value="" selected="selected">Tất cả</option>`);
                $.each(data.listClass, function (i, item) {
                    $('#classList').append(
                        `<option value="` + item.classId + `">` + item.grade + ` ` + item.giftedClass.name + `</option>`
                    );
                });
            } else {
                $("#classList").html(`<option value="err" selected="selected">Danh sách lớp trống.</option>`);
            }
            if (data.listDate.length != 0) {
                $("#fromDate").select2();
                $("#fromDate").html("");
                $.each(data.listDate, function (i, item) {
                    if (i == 0) {
                        $('#fromDate').append(
                            `<option value="` + item + `" selected="selected">` + convertDate(item, '/') + `</option>
                        `);
                    } else {
                        $('#fromDate').append(
                            `<option value="` + item + `">` + convertDate(item, '/') + `</option>
                    `);
                    }
                });
            } else {
                $("#fromDate").html(`<option value="err" selected="selected">Danh sách ngày trống.</option>`);
            }
        } else {
            if (data.listClass.length != 0) {
                $("#classList").html(`<option value="err" selected="selected">` + message + `</option>`);
            } else {
                $("#fromDate").html(`<option value="err" selected="selected">` + message + `</option>`);
            }
        }
    },
    failure: function (errMsg) {
        if (data.listClass.length != 0) {
            $("#classList").html(`<option value="err" selected="selected">` + errMsg + `</option>`);
        } else {
            $("#fromDate").html(`<option value="err" selected="selected">` + errMsg + `</option>`);
        }
    },
    dataType: "json",
    contentType: "application/json"
});

setTimeout(search, 500);

/*Load data to list*/
function search() {
    var fromDate = $('#fromDate option:selected').val();
    var classId = $('#classList option:selected').val();
    var redStar = $('#redStarList').val().trim();
    var inforSearch = {
        fromDate: fromDate,
        classId: classId,
        redStar: redStar,
    };
    console.log(JSON.stringify(inforSearch));
    if (fromDate == 'err' || classId == 'err') {
        $('tbody').append(`<tr><td colspan="3" class="userlist-result">Danh sách trống.</td></tr>`);
        $('table').dataTable();
    } else {
        $('table').dataTable({
            destroy: true,
            searching: false,
            bInfo: false,
            paging: false,
            order: [],
            ajax: {
                url: "/api/assignRedStar/viewassigntask",
                type: "POST",
                data: function (d) {
                    return JSON.stringify(inforSearch);
                },
                dataType: "json",
                contentType: "application/json",
                failure: function (errMsg) {
                    $('tbody').append(`<tr><td colspan="3" class="userlist-result"> ` + errMsg + ` </td></tr>`)
                },
                dataSrc: function (data) {
                    var dataSrc = null;
                    var messageCode = data.message.messageCode;
                    var message = data.message.message;
                    if (messageCode == 0) {
                        if (data.listAssignTask.length != 0) {
                            dataSrc = data.listAssignTask;
                        } else {
                            return false;
                        }
                    } else {
                        $('tbody').append(`<tr><td colspan="3" class="userlist-result"> ` + message + ` </td></tr>`)
                        return false;
                    }
                    return dataSrc;
                }
            },
            columns: [
                {data: "className"},
                {data: "redStar1"},
                {data: "redStar2"},
            ],
            drawCallback: function (settings) {
                settings.oLanguage.sEmptyTable = "Danh sách trống."
            }
        })

    }
}

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

/*Download button*/
$("#download").click(function () {
    var fromDate = $('#fromDate option:selected').val();
    var download = {
        fromDate: fromDate,
        classId: "",
        redStar: ""
    }
    console.log(JSON.stringify(download))
    $.ajax({
        url: '/api/assignRedStar/download',
        type: 'POST',
        data: JSON.stringify(download),
        xhrFields: {
            responseType: 'blob'
        },
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            var name = "Phân-công-trực-tuần-" + fromDate + '.xls';
            a.href = url;
            a.download = name;
            document.body.append(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
        },
        failure: function (errMsg) {
            dialogModal('downloadModal', 'img/img-error.png', 'Không thể tải xuống!')
        },
        dataType: "binary",
        contentType: "application/json"
    });
});

/*Dialog Modal*/
function dialogModal(modalName, img, message) {
    $('#' + modalName + ' .modal-body').html(`
        <img class="mb-3 mt-3" src="` + img + `"/>
        <h5>` + message + `</h5>
    `)
    $('#' + modalName).modal('show');
}

if (localStorage.getItem('roleID') != 1) {
    $('.manageBtn').addClass('hide');
}