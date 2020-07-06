var infoSearch = {
    weekId,
    teacherId
}
var yearId, weekId, teacherId, weekIdCurrent;

$(document).ready(function () {
    $("#teacher").select2();
});
/*Load years and list*/
$.ajax({
    url: '/api/timetable/listyearandteacher',
    type: 'POST',
    beforeSend: function () {
        $('body').addClass("loading")
    },
    complete: function () {
        $('body').removeClass("loading")
    },
    success: function (data) {
        if (data.messageDTO.messageCode == 0) {
            weekIdCurrent = data.weekIdCurrent;
            if (data.listYear == null) {
                $('#year').html(`<option value="">Không có năm học nào</option>`);
            } else {
                $('#year').html("")
                $.each(data.listYear, function (i, item) {
                    if (item.yearID == data.yearIdCurrent) {
                        $('#year').append(`<option value="` + item.yearID + `" selected>` + item.year + `</option>`);
                    } else {
                        $('#year').append(`<option value="` + item.yearID + `">` + item.year + `</option>`);
                    }
                });
                yearId = $('#year option:selected').val();
                loadWeek();
            }
            if (data.teacherList == null) {
                $('#teacher').html(`<option value="">Không có giáo viên nào</option>`);
            } else {
                $('#teacher').html("")
                $.each(data.teacherList, function (i, item) {
                    $('#teacher').append(`<option value="` + item.teacherId + `">` + item.fullName + `</option>`);
                });
                teacherId = $('#teacher option:selected').val();
            }
            infoSearch = {
                weekId: weekIdCurrent,
                teacherId: teacherId
            }
            loadTimetable();
        } else {
            console.log(data.messageDTO.message);
        }
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

loadTimetable();

/*Load timetable*/
function loadTimetable() {
    $.ajax({
        url: '/api/timetable/teachertimetable',
        type: 'POST',
        data: JSON.stringify(infoSearch),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            $('.timetable').removeClass('hide');
            $('.table-err').addClass('hide');
            var messageCode = data.messageDTO.messageCode;
            var message = data.messageDTO.message;

            if (messageCode == 0) {
                $('.timetable').removeClass('hide');
                $('.table-err').addClass('hide');
                var morning = data.morningTimeTable;
                var afternoon = data.afternoonTimeTable;
                if (morning == null) {
                    $('.morning .data').html('');
                } else {
                    for (var i = 0; i < morning.length; i++) {
                        var slot = morning[i].slotId;
                        var dayId = morning[i].dayId;
                        var subject = morning[i].subject;
                        var className = morning[i].classIdentifier;
                        if (className == null) {
                            className = "";
                        }
                        if (slot == 1) {
                            $('tbody').find('tr').eq(slot - 1).find('td').eq(dayId + 1).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                            )
                        } else {
                            $('tbody').find('tr').eq(slot - 1).find('td').eq(dayId).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                            )
                        }

                    }
                }
                if (afternoon == null) {
                    $('.afternoon .data').html('');
                } else {
                    for (var i = 0; i < afternoon.length; i++) {
                        var slot = afternoon[i].slotId;
                        var dayId = afternoon[i].dayId;
                        var subject = afternoon[i].subject;
                        var className = afternoon[i].classIdentifier;
                        var isOddWeek = afternoon[i].isOddWeek;
                        if (className == null) {
                            className = "";
                        }
                        if (isOddWeek == 0 || isOddWeek == null) {
                            if (slot == 1) {
                                $('tbody').find('tr').eq(slot + 4).find('td').eq(dayId + 2).html(
                                    `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                                )
                            } else {
                                $('tbody').find('tr').eq(slot + 4).find('td').eq(dayId).html(
                                    `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                                )
                            }
                        } else {
                            if (slot == 1) {
                                $('tbody').find('tr').eq(slot + 6).find('td').eq(dayId + 1).html(
                                    `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                                )
                            } else {
                                $('tbody').find('tr').eq(slot + 6).find('td').eq(dayId).html(
                                    `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + className + `</div>`
                                )
                            }
                        }
                    }
                }
            } else {
                $('.timetable').addClass('hide');
                $('.table-err').removeClass('hide');
                $('.table-err').html(
                    ` <tr><td colspan="8" class="userlist-result">` + message + `</td> </tr> `
                )
            }
        },
        failure: function (errMsg) {
            $('.timetable').addClass('hide');
            $('.table-err').removeClass('hide');
            $('.table-err').html(
                ` <tr><td colspan="8" class="userlist-result">` + errMsg + `</td> </tr> `
            )
        },
        dataType: "json",
        contentType: "application/json"
    });
}

function changeSelected() {
    yearId = $('#year option:selected').val();
    loadWeek();
    weekId = $('#week option:selected').val();
}

/*Search timetable for Teacher*/
$('#search').click(function (e) {
    weekId = $('#week option:selected').val();
    teacherId = $('#teacher option:selected').val();
    infoSearch = {
        weekId: weekId,
        teacherId: teacherId,
    }
    console.log(JSON.stringify(infoSearch));
    $('tbody .data').html('');
    loadTimetable();
})
