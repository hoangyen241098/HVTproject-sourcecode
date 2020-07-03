var infoSearch = {
    weekId: 2,
    classId: 1
}
/*Load years and list*/
$.ajax({
    url: '/api/timetable/listyearandclass',
    type: 'POST',
    success: function (data) {
        if (data.messageDTO.messageCode == 0) {
            if (data.listYear == null) {
                $('#year').append(`<option>Không có năm học nào</option>`);
            } else {
                $.each(data.listYear, function (i, item) {
                    $('#year').append(`<option value="` + item.yearID + `">` + item.year + `</option>`);
                });
            }

            if (data.classList == null) {
                $('#class').append(`<option>Không có lớp nào</option>`);
            } else {
                $.each(data.classList, function (i, item) {
                    $('#class').append(`<option value="` + item.classId + `">`
                        + item.giftedClass.giftedClassId + `" "` + item.giftedClass.name +
                        `</option>`);
                });
            }
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
        url: '/api/timetable/classtimetable',
        type: 'POST',
        data: JSON.stringify(infoSearch),
        success: function (data) {
            var messageCode = data.messageDTO.messageCode;
            var message = data.messageDTO.message;

            if (messageCode == 0) {
                var morning = data.morningTimeTable;
                var afternoon = data.afternoonTimeTable;
                if (morning == null) {
                    $('tbody').html(
                        ` <tr><td colspan="8" class="userlist-result">Không có thời khóa biểu buổi sáng</td> </tr> `
                    )
                } else {
                    for (var i = 0; i < morning.length; i++) {
                        var slot = morning[i].slot;
                        var dayId = morning[i].dayId;
                        var subject = morning[i].subject;
                        var teacherId = morning[i].teacherId;
                        if (teacherId == null) {
                            teacherId = "";
                        }
                        if (slot == 1) {
                            $('tbody').find('tr').eq(slot - 1).find('td').eq(dayId + 1).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + teacherId + `</div>`
                            )
                        } else {
                            $('tbody').find('tr').eq(slot - 1).find('td').eq(dayId).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + teacherId + `</div>`
                            )
                        }

                    }
                }
                if (afternoon == null) {
                    $('tbody').html(
                        ` <tr><td colspan="8" class="userlist-result">Không có thời khóa biểu buổi chiều</td> </tr> `
                    )
                } else {
                    for (var i = 0; i < afternoon.length; i++) {
                        var slot = afternoon[i].slot;
                        var dayId = afternoon[i].dayId;
                        var subject = afternoon[i].subject;
                        var teacherId = afternoon[i].teacherId;
                        if (teacherId == null) {
                            teacherId = "";
                        }
                        if (slot == 1) {
                            $('tbody').find('tr').eq(slot + 4).find('td').eq(dayId + 1).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + teacherId + `</div>`
                            )
                        } else {
                            $('tbody').find('tr').eq(slot + 4).find('td').eq(dayId).html(
                                `<div class="subject">` + subject + `</div>
                            <div class="teacher">` + teacherId + `</div>`
                            )
                        }
                    }
                }

            } else {

                $('.timetable').html(
                    ` <tr><td colspan="8" class="userlist-result">` + message + `</td> </tr> `
                )
            }
        },
        failure: function (errMsg) {
            $('.timetable').html(
                ` <tr><td colspan="8" class="userlist-result">` + errMsg + `</td> </tr> `
            )
        },
        dataType: "json",
        contentType: "application/json"
    });
}
