var listCreate = [];
var listEdit = [];
var listEditOld = [];
var rankOld = [];

/*=============Set data================*/
/*Load year, week list and class list*/
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
        var currentYearId = data.currentYearId;
        if (messageCode == 0) {
            if (data.schoolYearList != null) {
                $('#byYear').html('');
                $.each(data.schoolYearList, function (i, item) {
                    if (currentYearId == item.schoolYearId) {
                        $('#byYear').append(`<option value="` + item.schoolYearId + `" selected="selected">` + item.yearName + `</option>`);
                    } else {
                        $('#byYear').append(`<option value="` + item.schoolYearId + `">` + item.yearName + `</option>`);
                    }
                })
                var yearId = $('#byYear option:selected').val();
                loadComboboxYear(yearId);
            } else {
                $('#byYear').html(`<option value="err">Danh sách năm học trống.</option>`);
                $('#byWeek').html(`<option value="err">Danh sách tuần trống.</option>`);
                $('#byWeek').prop('disabled', true);
            }
            if (data.classList != null) {
                $('#byClass').html(`<option value="" selected="selected">Tất cả</option>`);
                $("#byClass").select2();
                $.each(data.classList, function (i, item) {
                    $('#byClass').append(`<option value="` + item.classID + `">` + item.className + `</option>`);
                })
            } else {
                $('#byClass').html(`<option value="err">Danh sách lớp trống.</option>`);
            }
        } else {
            if (data.schoolYearList == null) {
                $('#byYear').html(`<option value="err">` + message + `</option>`);
            }
            if (data.schoolWeekList == null) {
                $('#byWeek').html(`<option value="err">` + message + `</option>`);
            }
            if (data.classList == null) {
                $('#byClass').html(`<option value="err">` + message + `</option>`);
            }
        }
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Get week list combo when change year combo box*/
function loadComboboxYear(yearId) {
    $.ajax({
        url: '/api/rankweek/getweeklist',
        type: 'POST',
        data: JSON.stringify({yearId: yearId}),
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
                            $('#byWeek').append(`<option value="` + item.weekID + `" name="` + item.yearId + `" selected="selected">Tuần ` + item.week + `</option>`);
                        } else {
                            $('#byWeek').append(`<option value="` + item.weekID + `" name="` + item.yearId + `">Tuần ` + item.week + `</option>`);
                        }
                    });
                    if (sessionStorage.getItem('weekName') != null) {
                        var weekName = 'Tuần ' + sessionStorage.getItem('weekName');
                        $("#byWeek option").filter(function () {
                            return $(this).text() == weekName;
                        }).prop("selected", true);
                    }
                    if (sessionStorage.getItem('weekId') != null) {
                        $("#byWeek").val(sessionStorage.getItem('weekId')).change();
                    }
                } else {
                    $('#byWeek').html(`<option value="err">Danh sách tuần đang trống.</option>`);
                }
            } else {
                if (data.schoolWeekList == null) {
                    $('#byWeek').html(`<option value="err">` + message + `</option>`);
                }
            }
        },
        failure: function (errMsg) {
            console.log(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

$('#byYear').change(function () {
    var yearId = $('#byYear option:selected').val();
    loadComboboxYear(yearId);
})

setTimeout(search, 500);

/*Set data to table*/
function search() {
    var infoSearch = {
        weekId: $('#byWeek option:selected').val(),
        classId: $('#byClass option:selected').val()
    }
    console.log(JSON.stringify(infoSearch));
    if ($('#byWeek option:selected').val() == 'err') {
        $('#editRankBtn').addClass('hide');
        $('#editGrades').addClass('hide');
        $('tbody').append(`<tr><td colspan="7" class="userlist-result">Không có tuần trong dữ liệu.</td></tr>`)
    } else {
        if (localStorage.getItem('roleID') == 1) {
            $('#editRankBtn').removeClass('hide');
            $('#editGrades').removeClass('hide');
        }
        $('table').dataTable({
            destroy: true,
            searching: false,
            bInfo: false,
            paging: false,
            // responsive: true,
            order: [],
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
                    var checkEdit = data.checkEdit;
                    if (messageCode == 0) {
                        if(checkEdit != null && checkEdit == 1 ){
                            $('#editGrades').addClass('hide');
                            $('#editRankBtn').addClass('hide');
                        }
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
                    targets: 0,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).attr('data-column', rowData.classId);
                        $(td).css('min-width', '150px');
                    }
                },
                {
                    targets: 1,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).addClass('text-right');
                    }
                },
                {
                    targets: 2,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).attr('contenteditable', 'false');
                        $(td).addClass('learningGrade text-right');
                    }
                },
                {
                    targets: 3,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).attr('contenteditable', 'false');
                        $(td).addClass('movementGrade text-right');
                    }
                },
                {
                    targets: 4,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).attr('contenteditable', 'false');
                        $(td).addClass('laborGrade text-right');
                    }
                },
                {
                    targets: 5,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).addClass('text-right');
                    }
                },
                {
                    targets: 6,
                    createdCell: function (td, cellData, rowData, row, col) {
                        $(td).addClass('font-500 text-right');
                    }
                }
            ],
            drawCallback: function (settings) {
                settings.oLanguage.sEmptyTable = "Danh sách xếp hạng tuần trống."
            }
        })
    }
}

/*Search button*/
$('#search').click(function (e) {
    $('table tbody').html('');
    search();
})

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
                    dialogModal('createSuccess', 'img/img-success.png', 'Tạo xếp hạng tuần thành công!');
                    sessionStorage.removeItem('weekId');
                    sessionStorage.removeItem('weekName');
                    sessionStorage.setItem('weekName', weekName);
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
    listEditOld = [];
    $('#editRank').modal('show');
    var weekName = $('#byWeek option:selected').text().slice(5);
    $('#newWeekName').val(weekName);
    var weekId = $('#byWeek option:selected').val();
    var data = {
        weekId: weekId
    }
    console.log(JSON.stringify(data));
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
                    $('input[name=editOptions]:checked').each(function (i) {
                        listEditOld.push({
                            date: $(this).parent().parent().find('.date').text(),
                            dayName: $(this).parent().parent().find('.dayName').text(),
                            isCheck: 1,
                            week: $('#byWeek option:selected').val()
                        });
                    });
                    $('input[name=editOptions]:not(:checked)').each(function (i) {
                        listEditOld.push({
                            date: $(this).parent().parent().find('.date').text(),
                            dayName: $(this).parent().parent().find('.dayName').text(),
                            isCheck: 0,
                            week: null
                        });
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
    listEdit = [];
    var count = 0;
    $('input[name=editOptions]:checked').each(function (i) {
        count++;
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
    listEditOld.sort(function (a, b) {
        return new Date(b.date) - new Date(a.date);
    });
    listEdit.sort(function (a, b) {
        return new Date(b.date) - new Date(a.date);
    });
    var weekNameOld = $('#byWeek option:selected').text().slice(5);
    var weekName = $('#newWeekName').val().trim();

    if ((weekNameOld == weekName) && (JSON.stringify(listEditOld) == JSON.stringify(listEdit))) {
        $('.editRank-err').text('Hãy thay đổi thông tin.');
        return false;
    } else if (weekName == "" || weekName == null) {
        $('.editRank-err').text('Hãy nhập tên tuần.');
        return false;
    } else if (count == 0) {
        $('.editRank-err').text('Hãy chọn ngày áp dụng.')
        return false;
    } else {
        $('.editRank-err').text('');
        var editRank = {
            weekId: $('#byWeek option:selected').val(),
            week: weekName,
            userName: localStorage.getItem('username'),
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
                    sessionStorage.removeItem('weekId');
                    sessionStorage.removeItem('weekName');
                    sessionStorage.setItem('weekName', weekName);
                    dialogModal('editSuccess', 'img/img-success.png', 'Sửa xếp hạng tuần thành công!');
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

/*=============Edit Grade=====================*/
/*Button Edit table*/
$("#editGrades").on("click", function () {
    var row = $('tbody tr td[contenteditable]');
    var editOn = $('#editGrades').hasClass("editMode");
    var weekId = $('#byWeek option:selected').val();


    if (editOn == false) {
        $(row).attr('contenteditable', 'true');
        $(row).css('background-color', '#fdf1f1');
        $('#editGrades').attr('value', 'Lưu thay đổi');
        validateInput('learningGrade', 20);
        validateInput('movementGrade', 0.2);
        validateInput('laborGrade', 0.2);
        $('#closeEditGrades').removeClass('hide');
        $('#editGrades').addClass('editMode');
        rankOld = [];
        $('table tbody tr').each(function () {
            rankOld.push({
                weekId: weekId,
                classId: $(this).find('td').eq(0).data('column'),
                className: $(this).find('td').eq(0).text(),
                emulationGrade: $(this).find('td').eq(1).text(),
                learningGrade: $(this).find('td').eq(2).text(),
                movementGrade: $(this).find('td').eq(3).text(),
                laborGrade: $(this).find('td').eq(4).text(),
                totalGrade: $(this).find('td').eq(5).text(),
                rank: $(this).find('td').eq(6).text(),
                history: null
            })
        });
        rankOld.sort(function (a, b) {
            return a.classId - b.classId;
        });
        console.log(rankOld);
    } else if (editOn == true) {
        var rankWeekList = [];
        $('table tbody tr').each(function () {
            rankWeekList.push({
                weekId: weekId,
                classId: $(this).find('td').eq(0).data('column'),
                className: $(this).find('td').eq(0).text(),
                emulationGrade: $(this).find('td').eq(1).text(),
                learningGrade: $(this).find('td').eq(2).text(),
                movementGrade: $(this).find('td').eq(3).text(),
                laborGrade: $(this).find('td').eq(4).text(),
                totalGrade: $(this).find('td').eq(5).text(),
                rank: $(this).find('td').eq(6).text(),
                history: null
            })
        });
        var newData = {
            rankWeekList: rankWeekList
        }

        rankWeekList.sort(function (a, b) {
            return a.classId - b.classId;
        });
        console.log(rankWeekList);
        if (JSON.stringify(rankOld) == JSON.stringify(rankWeekList)) {
            $('#editSuccess .modal-footer').html(`<input type="button" class="btn btn-primary" value="ĐÓNG" data-dismiss="modal"/>`)
            dialogModal('editSuccess', 'img/img-error.png', 'Chưa thay đổi dữ liệu.');
        } else {
            $(row).attr('contenteditable', 'false');
            $(row).css('background-color', 'transparent');
            $('#editGrades').attr('value', 'Sửa điểm');
            $('#editGrades').removeClass('editMode');
            $('#closeEditGrades').addClass('hide');
            $.ajax({
                url: '/api/rankweek/updatescorerankweek',
                type: 'POST',
                data: JSON.stringify(newData),
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
                        sessionStorage.removeItem('weekName');
                        sessionStorage.removeItem('weekId');
                        sessionStorage.setItem('weekId', weekId);
                        dialogModal('editSuccess', 'img/img-success.png', 'Sửa điểm thành công!');
                    } else {
                        sessionStorage.removeItem('weekName');
                        sessionStorage.removeItem('weekId');
                        sessionStorage.setItem('weekId', weekId);
                        dialogModal('editSuccess', 'img/img-error.png', message)
                    }
                },
                failure: function (errMsg) {
                    sessionStorage.removeItem('weekName');
                    sessionStorage.removeItem('weekId');
                    sessionStorage.setItem('weekId', weekId);
                    dialogModal('editSuccess', 'img/img-error.png', errMsg)
                },
                dataType: "json",
                contentType: "application/json"
            });
        }
    }
    $('#closeEditGrades').click(function () {
        $(row).attr('contenteditable', 'false');
        $(row).css('background-color', 'transparent');
        $('#editGrades').attr('value', 'Sửa điểm');
        $('#editGrades').removeClass('editMode');
        $('#closeEditGrades').addClass('hide');
        search();
    })
});

/*Validate input*/
function validateInput(className, max) {
    var inputClass = $('table .' + className);
    var input;
    $(inputClass).focus(function () {
        input = parseFloat($(this).text());
    });
    $(inputClass).blur(function () {
        var value = parseFloat($(this).text());
        console.log(value)
        if (value > max || value < 0 || isNaN(value)) {
            var $input = $(this);
            $(this).css('color', 'red');
            $(this).css('font-weight', '500');
            $('#editSuccess .modal-footer').html(`<input type="button" class="btn btn-primary closeModalErr" value="ĐÓNG"/>`)
            if (isNaN(value)) {
                dialogModal('editSuccess', 'img/img-error.png', 'Không được để trống trường này!');
            } else {
                dialogModal('editSuccess', 'img/img-error.png', 'Điểm nhập vào phải nhỏ hơn ' + max + '!');
            }
            $('.closeModalErr').on('click', function () {
                $input.focus();
                $('#editSuccess').modal('hide');
            })
        } else if (value != input) {
            $(this).css('color', 'black');
            $(this).css('font-weight', '700');
        } else {
            $(this).css('color', 'black');
            $(this).css('font-weight', '400');
        }
    });
    $('[contenteditable="true"]').keypress(function (e) {
        var x = event.charCode || event.keyCode;
        if (isNaN(String.fromCharCode(e.which)) && x != 46 || x === 32 || x === 13 || (x === 46 && event.currentTarget.innerText.includes('.'))) e.preventDefault();
    });
}

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

function closeModal(weekId) {
    $('.isClosed').on('click', function () {
        $('#editSuccess').modal('hide');
        $('#createSuccess').modal('hide');
        $('#byWeek option:selected').val(weekId);
        $('table tbody').html('');
        search();
    })
}

/*Remove checkbox when close modal*/
$(document).on('hidden.bs.modal', '#createNewRank', '#editRank', function () {
    $('input[name=options]').prop('checked', false);
    $('input[name=editOptions]').prop('checked', false);
    $('#weekName').val('');
    $('#newWeekName').val('');
});

/*===============View History===================*/
/*View history button*/
$("#viewHistory").click(function () {
    var weekId = $('#byWeek option:selected').val();
    var viewHistory = {
        weekId: weekId
    };
    $.ajax({
        url: '/api/rankweek/viewhistory',
        type: 'POST',
        data: JSON.stringify(viewHistory),
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
                $('#historyModal .modal-body').html(data.history);
                $('#historyModal').modal('show');
            }
            else{
                dialogModal('downloadModal', 'img/img-error.png', message)
            }
        },
        failure: function (errMsg) {
            dialogModal('downloadModal', 'img/img-error.png', errMsg)
        },
        dataType: "json",
        contentType: "application/json"
    });
});