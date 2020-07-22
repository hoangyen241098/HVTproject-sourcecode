var listCreate = [];
var listEdit = [];

/*=============Set data================*/
/*Load week list and class list*/
$.ajax({
    url: '/api/rankweek/viewweekandclasslist',
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
            if (data.schoolWeekList != null) {
                $('#byWeek').html('');
                $.each(data.schoolWeekList, function (i, item) {
                    if (i == 0) {
                        $('#byWeek').append(`<option value="` + item.weekID + `" selected="selected">Tuần ` + item.week + `</option>`);
                    } else {
                        $('#byWeek').append(`<option value="` + item.weekID + `">Tuần ` + item.week + `</option>`);
                    }
                })
            } else {
                $('#byWeek').html(`<option>Danh sách tuần trống.</option>`);
            }
            if (data.classList != null) {
                $('#byClass').html(`<option value="" selected="selected">Tất cả</option>`);
                $("#byClass").select2();
                $.each(data.classList, function (i, item) {
                    $('#byClass').append(`<option value="` + item.classID + `">` + item.className + `</option>`);
                })
            } else {
                $('#byClass').html(`<option>Danh sách lớp trống.</option>`);
            }
        } else {
            if (data.schoolWeekList == null) {
                $('#byWeek').html(`<option>` + message + `</option>`);
            }
            if (data.classList == null) {
                $('#byClass').html(`<option>` + message + `</option>`);
            }
        }
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

setTimeout(search, 500);

/*Set data to table*/
function search() {
    var infoSearch = {
        weekId: $('#byWeek option:selected').val(),
        classId: $('#byClass option:selected').val()
    }
    console.log(JSON.stringify(infoSearch));
    $('table').dataTable({
        destroy: true,
        searching: false,
        bInfo: false,
        paging: false,
        responsive: true,
        ajax: {
            url: "/api/rankweek/searchrankweek",
            type: "POST",
            data: function (d) {
                return JSON.stringify(infoSearch);
            },
            dataType: "json",
            contentType: "application/json",
            failure: function (errMsg) {
                $('tbody').append(
                    `<tr>
                        <td colspan="7" class="userlist-result"> ` + errMsg + ` </td>
                    </tr>`
                )
            },
            dataSrc: function (data) {
                var dataSrc = null;
                var messageCode = data.message.messageCode;
                var message = data.message.message;
                if (messageCode == 0) {
                    if (data.rankWeekList != null) {
                        dataSrc = data.rankWeekList;
                    } else {
                        return false;
                    }
                } else {
                    $('tbody').append(
                        `<tr>
                            <td colspan="7" class="userlist-result"> ` + message + ` </td>
                        </tr>`
                    )
                    return false;
                }
                return dataSrc;
            }
        },
        columns: [
            {data: "className"},
            {data: "emulationGrade"},
            {data: "learningGrade"},
            {data: "movementGrade"},
            {data: "laborGrade"},
            {data: "totalGrade"},
            {data: "rank"},
        ],
        columnDefs: [
            {
                targets: 2,
                createdCell: function (td, cellData, rowData, row, col) {
                    $(td).attr('contenteditable', 'false');
                }
            },
            {
                targets: 3,
                createdCell: function (td, cellData, rowData, row, col) {
                    $(td).attr('contenteditable', 'false');
                }
            },
            {
                targets: 4,
                createdCell: function (td, cellData, rowData, row, col) {
                    $(td).attr('contenteditable', 'false');
                }
            }
        ],
        drawCallback: function (settings) {
            settings.oLanguage.sEmptyTable = "Danh sách xếp hạng tuần trống."
        }
    })
}

/*Search button*/
$('#search').click(function (e) {
    $('table tbody').html('');
    search();
})

$("#editGrades").on("click", function () {
    var row = $('tbody tr td[contenteditable]');
    var editOn = $('#editGrades').hasClass("editMode");

    if (editOn == false) {
        $(row).attr('contenteditable', 'true');
        $(row).css('background-color', '#fdf1f1');
        $('#editGrades').attr('value', 'Lưu thay đổi');
        $('#editGrades').addClass('editMode');
    } else if (editOn == true) {
        $(row).attr('contenteditable', 'false');
        $(row).css('background-color', 'transparent');
        $('#editGrades').attr('value', 'Sửa điểm');
        $('#editGrades').removeClass('editMode');
    }
});

/*==========Create rank===========*/
/*Load date list*/
$('#createRank').on('click', function () {
    $('#createNewRank').modal('show');
    listCreate = [];
    $.ajax({
        url: '/api/rankweek/loaddatelist',
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
                if (data.dateList != null) {
                    $('#dateList').html('');
                    $('#dateList').append(`
                    <h6>Các ngày áp dụng <span class="text-red">*</span></h6>
                `);
                    $.each(data.dateList, function (i, item) {
                        $('#dateList').append(`
                        <div class="form-check text-left my-1">
                            <span class="custom-checkbox">
                                <input type="checkbox" name="options" value="` + i + `">
                                <label for="` + i + `"></label>
                            </span>
                            <span class="ml-3">` + item.dayName + ` - ` + item.date + `</span>
                            <span class="hide dayName">` + item.dayName + `</span>
                            <span class="hide date">` + item.date + `</span>
                        </div>
                    `);
                    });
                } else {
                    $('#dateList').html(`<h6 class="text-red">Không có ngày áp dụng nào!</h6>`);
                }
            } else {
                $('#dateList').html(`<h6 class="text-red">` + message + `</h6>`);
            }
        },
        failure: function (errMsg) {
            $('#dateList').html(`<h6 class="text-red">` + errMsg + `</h6>`);
        },
        dataType: "json",
        contentType: "application/json"
    });
})

/*Create rank week*/
$('#createNewRankBtn').on('click', function () {
    $('input[name=options]:checked').each(function (i) {
        listCreate.push({
            date: $(this).parent().parent().find('.date').text(),
            dayName: $(this).parent().parent().find('.dayName').text(),
            isCheck: 1,
        });
    });
    var weekName = $('#weekName').val().trim();
    if (weekName == "" || weekName == null) {
        $('.createNewRank-err').text('Hãy nhập tên tuần.');
        return false;
    } else if (listCreate.length == 0) {
        $('.createNewRank-err').text('Hãy chọn ngày áp dụng.')
        return false;
    } else {
        $('.createNewRank-err').text('');
        var createRank = {
            userName: localStorage.getItem('username'),
            week: weekName,
            currentYearId: localStorage.getItem('currentYearId'),
            dateList: listCreate
        }
        console.log(JSON.stringify(createRank));
        $.ajax({
            url: '/api/rankweek/createrankweek',
            type: 'POST',
            data: JSON.stringify(createRank),
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
                    dialogModal('createSuccess', 'img/img-success.png', 'Tạo xếp hạng tuần thành công!')
                } else {
                    dialogModal('createSuccess', 'img/img-error.png', message)
                }
            },
            failure: function (errMsg) {
                dialogModal('createSuccess', 'img/img-error.png', errMsg)
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
})

/*=============Edit Rank=====================*/
/*Load edit rank week*/
$('#editRankBtn').on('click', function () {
    listEdit = [];
    $('#editRank').modal('show');
    var weekName = $('#byWeek option:selected').text().slice(5);
    $('#newWeekName').val(weekName);
    var weekId = $('#byWeek option:selected').val();
    var data = {
        weekId: weekId
    }
    $.ajax({
        url: '/api/rankweek/loadeditrankweek',
        type: 'POST',
        data: JSON.stringify(data),
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
                if (data.dateList != null) {
                    $('#dateListEdit').html('');
                    $('#dateListEdit').append(`
                    <h6>Các ngày áp dụng <span class="text-red">*</span></h6>
                    `);
                    $.each(data.dateList, function (i, item) {
                        $('#dateListEdit').append(`
                            <div class="form-check text-left my-1">
                                <span class="custom-checkbox">
                                    <input type="checkbox" name="editOptions" value="` + i + `">
                                    <label for="` + i + `"></label>
                                </span>
                                <span class="ml-3">` + item.dayName + ` - ` + item.date + `</span>
                                <span class="hide dayName">` + item.dayName + `</span>
                                <span class="hide date">` + item.date + `</span>
                                <span class="hide week">` + item.week + `</span>
                            </div>
                        `);
                        if (item.isCheck == 1) {
                            $('input[value=' + i + ']').prop('checked', true);
                        }
                    });
                } else {
                    $('#dateListEdit').html(`<h6 class="text-red">Không có ngày áp dụng nào!</h6>`);
                }
            } else {
                $('#dateListEdit').html(`<h6 class="text-red">` + message + `</h6>`);
            }
        },
        failure: function (errMsg) {
            $('#dateListEdit').html(`<h6 class="text-red">` + errMsg + `</h6>`);
        },
        dataType: "json",
        contentType: "application/json"
    });
})

/*Edit rank week*/
$('#editRankBtnModal').on('click', function () {
    $('input[name=editOptions]:checked').each(function (i) {
        listEdit.push({
            date: $(this).parent().parent().find('.date').text(),
            dayName: $(this).parent().parent().find('.dayName').text(),
            isCheck: 1,
            week: $('#byWeek option:selected').val()
        });
    });
    $('input[name=editOptions]:not(:checked)').each(function (i) {
        listEdit.push({
            date: $(this).parent().parent().find('.date').text(),
            dayName: $(this).parent().parent().find('.dayName').text(),
            isCheck: 0,
            week: null
        });
    });
    var weekName = $('#newWeekName').val().trim();
    if (weekName == "" || weekName == null) {
        $('.editRank-err').text('Hãy nhập tên tuần.');
        return false;
    } else if (listEdit.length == 0) {
        $('.editRank-err').text('Hãy chọn ngày áp dụng.')
        return false;
    } else {
        $('.editRank-err').text('');
        var editRank = {
            weekId: $('#byWeek option:selected').val(),
            week: weekName,
            dateList: listEdit
        }
        console.log(JSON.stringify(editRank));
        $.ajax({
            url: '/api/rankweek/editrankweek',
            type: 'POST',
            data: JSON.stringify(editRank),
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
                    dialogModal('editSuccess', 'img/img-success.png', 'Sửa xếp hạng tuần thành công!')
                } else {
                    dialogModal('editSuccess', 'img/img-error.png', message)
                }
            },
            failure: function (errMsg) {
                dialogModal('editSuccess', 'img/img-error.png', errMsg)
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
})

/*===============Download===================*/
/*Download button*/
$("#download").click(function () {
    var classId = $('#byClass option:selected').val();
    var weekId = $('#byWeek option:selected').val();
    if (classId == null || classId == "") {
        classId = ""
    } else {
        classId = $('#byClass option:selected').val();
    }
    var download = {
        weekId: weekId,
        classId: classId,
    }
    console.log(JSON.stringify(download))
    $.ajax({
        url: '/api/rankweek/download',
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
            var name = 'XẾP-HẠNG-THI-ĐUA-THEO-TUẦN.xls';
            a.href = url;
            a.download = name;
            document.body.append(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
        },
        failure: function (errMsg) {
            dialogModal('downloadModal', 'img/img-error.png', errMsg)
        },
        dataType: "binary",
        contentType: "application/json"
    });
});

/*=====================================*/
/*Set role*/
if (localStorage.getItem('roleID') != 1) {
    $('.manageBtn').addClass('hide');
}

/*Dialog Modal*/
function dialogModal(modalName, img, message) {
    $('#' + modalName + ' .modal-body').html(`
        <img class="mb-3 mt-3" src="` + img + `"/>
        <h5>` + message + `</h5>
    `)
    $('#' + modalName).modal('show');
}

/*Remove checkbox when close modal*/
$(document).on('hidden.bs.modal', '#createNewRank', '#editRank', function () {
    $('input[name=options]').prop('checked', false);
});