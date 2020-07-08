/*Set navigation bar*/
$(document).ready(function () {
    var loginSuccess = localStorage.getItem("loginSuccess");
    var roleID = localStorage.getItem("roleID");
    if (loginSuccess == 0) {
        $("#loginSuccessMenu").addClass("show");
        $('#loginMenu').css('display', 'none');
        if (roleID == 1) {
            $("#adminMenu").addClass("show");
        }
        if (roleID == 2) {
            $("#scheduleManagerMenu").addClass("show");
        }
    } else {
        $('#loginMenu').css('display', 'block');
        $("#loginSuccessMenu").removeClass("show");
        $("#adminMenu").removeClass("show");
        $("#scheduleManagerMenu").removeClass("show");
    }
    $("#logout").click(function () {
        localStorage.clear();
    })
});

/*Loading page*/
$(document).on({
    ajaxStart: function () {
        $('body').addClass("loading");
    },
    ajaxComplete: function () {
        $('body').removeClass("loading");
    }
})

/*Select Checkbox*/
function selectCheckbox() {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
            if (jQuery.inArray($(this).val(), list) == -1) {
                list.push($(this).val());
            }
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            list = $.grep(list, function (value) {
                return value != removeItem;
            });
        }
    });

    $("#selectAll").click(function () {
        var checkbox = $('table tbody input[type="checkbox"]');
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
                $('tbody tr').addClass('selected');
                if (jQuery.inArray($(this).val(), list) == -1) {
                    list.push($(this).val());
                }
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
                $('tbody tr').removeClass('selected');
                var removeItem = $(this).val();
                list = $.grep(list, function (value) {
                    return value != removeItem;
                });
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
}

/*Check user is selected or not*/
function isCheck(user) {
    if (list == null || list.length == 0) {
        return false;
    }
    for (var i = 0; i < list.length; i++) {
        if (list[i] == user) {
            return true;
        }
    }
    return false;
}

/*Pagination*/
function pagingClick() {
    $('.table-paging input').on('click', function (event) {
        $("#selectAll").prop("checked", false);
        var value = ($(this).val() - 1);
        if ($(this).val() == "Sau") {
            value = $('.table-paging__page_cur').val();
        } else if ($(this).val() == "Trước") {
            value = $('.table-paging__page_cur').val() - 2;
        }
        inforSearch.pageNumber = value;
        $('tbody').html("");
        $('.table-paging').html("");
        search();
    })
}

function paging(inforSearch, totalPages) {
    var pageNumber = parseInt(inforSearch.pageNumber)
    $('.table-paging').append(
        `<input type="button" class="table-paging__page btn-prev" id="prevPage" value="Trước"/>`
    );
    if (pageNumber < 4) {
        var newTotalPages = (totalPages <= 4) ? (totalPages) : (4);
        for (var i = 0; i < newTotalPages; i++) {
            if (i == pageNumber) {
                $('.table-paging').append(
                    `<input type="button" value="` + (i + 1) + `" class="table-paging__page table-paging__page_cur"/>`
                );
            } else {
                $('.table-paging').append(
                    `<input type="button" value="` + (i + 1) + `" class="table-paging__page"/>`
                );
            }
        }
        if (newTotalPages < totalPages) {
            $('.table-paging').append(
                `<input type="button" value="..." class="table-paging__page" disabled/>`
            );
        }
    } else {
        $('.table-paging').append(
            `<input type="button" value="` + (1) + `" class="table-paging__page"/>`
        );
        $('.table-paging').append(
            `<input type="button" value="` + (2) + `" class="table-paging__page"/>`
        );
        $('.table-paging').append(
            `<input type="button" value="..." class="table-paging__page" disabled/>`
        );
        var pageEnd = (pageNumber + 2 < totalPages) ? (pageNumber + 2) : (totalPages);
        for (var i = pageNumber - 1; i < pageEnd; i++) {
            if (i == pageNumber) {
                $('.table-paging').append(
                    `<input type="button" value="` + (i + 1) + `" class="table-paging__page table-paging__page_cur"/>`
                );
            } else {
                $('.table-paging').append(
                    `<input type="button" value="` + (i + 1) + `" class="table-paging__page"/>`
                );
            }
        }
        if (pageEnd < totalPages) {
            $('.table-paging').append(
                `<input type="button" value="..." class="table-paging__page" disabled/>`
            );
        }
    }
    $('.table-paging').append(
        `<input type="button" class="table-paging__page btn-next" id="nextPage" value="Sau"/>`
    );
    if (inforSearch.pageNumber == 0) {
        $('#prevPage').prop('disabled', true);
    } else {
        $('#prevPage').prop('disabled', false);
    }
    if ((inforSearch.pageNumber + 1) == totalPages) {
        $('#nextPage').prop('disabled', true);
    } else {
        $('#nextPage').prop('disabled', false);
    }

};

function nextPage() {
    $('#nextPage').on('click', function (event) {
        $("#selectAll").prop("checked", false);
        inforSearch.pageNumber++;
        $('tbody').html("");
        $('.table-paging').html("");
        search();
    })
}

function prevPage() {
    $('#prevPage').on('click', function (event) {
        $("#selectAll").prop("checked", false);
        inforSearch.pageNumber--;
        $('tbody').html("");
        $('.table-paging').html("");
        search();
    })
}

/*Convert string to form date*/
function convertDate(str) {
    str = str.split("-");
    str = str[2].concat("-" + str[1]);
    return str;
}

/*Load combobox week in timetable*/
function loadWeek() {
    var listweek = {
        yearIdCurrent: yearId,
    }
    /*Call API for weeks List*/
    $.ajax({
        url: '/api/timetable/listweek',
        type: 'POST',
        data: JSON.stringify(listweek),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            var messageCode = data.messageDTO.messageCode;
            var message = data.messageDTO.message;
            if (messageCode == 0) {
                if (data.listWeek != null) {
                    $('#week').html("");
                    $.each(data.listWeek, function (i, list) {
                        if (list.timeTableWeekId == weekIdCurrent) {
                            $('#week').append(`<option value="` + list.timeTableWeekId + `" selected>` + convertDate(list.fromDate) + ` đến ` + convertDate(list.toDate) + `</option>`);
                        } else {
                            $('#week').append(`<option value="` + list.timeTableWeekId + `">` + convertDate(list.fromDate) + ` đến ` + convertDate(list.toDate) + `</option>`);
                        }
                    });
                }
                weekIdCurrent = $('#week option:selected').val();
            } else {
                $('#week').html(`<option value="0">` + message + `</option>`);
            }
        },
        failure: function (errMsg) {
            $('#week').html(`<option value="0">` + errMsg + `</option>`);
        },
        dataType: 'JSON',
        contentType: "application/json"
    });
}

function toggleClick() {
    $(".panel-heading").on('click', function () {
        $(this).find(".fa-chevron-down").addClass("up");
    })
    $(".panel-heading.collapsed").on('click', function () {
        $(this).find(".fa-chevron-down").removeClass("up");
    })
}
