/*Set navigation bar*/
$(document).ready(function () {
    getAuthen();
    var loginSuccess = localStorage.getItem("loginSuccess");
    var roleID = localStorage.getItem("roleID");
    var username = localStorage.getItem("username");
    if (loginSuccess == 0) {
        $("#loginSuccessMenu").addClass("show");
        $("#loginSuccessMenu .nav-link").html(username + `<i class="fa fa-caret-down"></i>`);
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
        //localStorage.clear();
        logout();
    })

    $('#adminMenu').on('click', function () {
        $('.mega-menu.show').not($(this).find('.mega-menu')).removeClass('show');
        $('.dropdown-menu.show').removeClass('show');
        $('.nav-link .fa').not($(this).find('.fa')).removeClass('up');
        $(this).find('.mega-menu').toggleClass('show');
        $(this).find('.fa').toggleClass('up');
    })
    $('.dropdown').on('click', function () {
        $('.mega-menu.show').removeClass('show');
        $('.dropdown-menu.show').not($(this).find('.dropdown-menu')).removeClass('show');
        $('.nav-link .fa').not($(this).find('.fa')).removeClass('up');
        $(this).find('.dropdown-menu').toggleClass('show');
        $(this).find('.fa').toggleClass('up');
    });
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

/*Convert string to form date*/
function convertDate(str) {
    str = str.split("-");
    str = str[2].concat("/" + str[1] + "/" + str[0]);
    return str;
}

function toggleClick() {
    $(".panel-heading").on('click', function () {
        $(this).find(".fa-chevron-down").addClass("up");
    })
    $(".panel-heading.collapsed").on('click', function () {
        $(this).find(".fa-chevron-down").removeClass("up");
    })
}

/*Set limited date in year*/
function limitedDate() {
    var fromYear = $('#fromYear').val();
    $('#toYear').val(parseInt(fromYear) + 1);
    var toYear = $('#toYear').val();
    $('#fromDate').attr('min', fromYear + '-01-01');
    $('#fromDate').attr('max', fromYear + '-12-31');
    $('#toDate').attr('min', toYear + '-01-01');
    $('#toDate').attr('max', toYear + '-12-31');
}

// /*Clear session when leaving page*/
var pathname = $(location).attr('pathname');
if (pathname != '/teacherInformation') {
    sessionStorage.removeItem('teacherId');
}
if (pathname != '/editClass') {
    sessionStorage.removeItem('classId');
}
if (pathname != '/editViolationType') {
    sessionStorage.removeItem('violationTypeID');
}
if (pathname != '/editViolation') {
    sessionStorage.removeItem('violationId');
}
if (pathname != '/editSchoolYear') {
    sessionStorage.removeItem('schoolYearId');
}
if (pathname != '/violationListOfClass') {
    sessionStorage.removeItem('classIdGrading');
    sessionStorage.removeItem('dateGrading');
}

function logout() {
    $.ajax({
        type: 'POST',
        url: "/api/user/logout",
       // data: JSON.stringify(user),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            if(data != null && data.messageCode === 0){
                localStorage.clear();
                // localStorage.removeItem("userInfo")
                // this.$cookies.remove("access_token")
                // this.$cookies.remove("token_provider")
                // window.location.href = "/"
            }
        },
        failure: function (errMsg) {
            $('.login-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

function getAuthen() {
    $.ajax({
        type: 'POST',
        url: "/api/user/get-authentication",
        method : 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("accessToken")
        },

        // data: JSON.stringify(user),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            if(data != null && data.messageCode === 0){
                localStorage.clear();
                // localStorage.removeItem("userInfo")
                // this.$cookies.remove("access_token")
                // this.$cookies.remove("token_provider")
                // window.location.href = "/"
            }
        },
        failure: function (errMsg) {
            $('.login-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
        // Authorization: 'Bearer ' + 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTU5NjA5NzYxMSwiZXhwIjoxNTk2NzAyNDExfQ.bCFuTQOk1Kl9ARmrT83HTOQOMOFbFb6aLY_uEXiJTXBMz3tdlh3RMFPz70-sCKzNgTZ8bFJlzGeeHs5PgGAeNQ'
    });
}