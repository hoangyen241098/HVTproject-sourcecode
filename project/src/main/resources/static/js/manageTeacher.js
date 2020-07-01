var inforSearch = {
    fullName: "",
    orderBy: "0",
    pageNumber: "0"
}
var listTeacher = [];
localStorage.removeItem("teacherId");
search();

/*Search button*/
$("#search").click(function () {
    var fullName, orderBy, pageNumber;
    fullName = $('#searchByFullName input').val().trim();
    if ($('#orderBy option:selected').val() == null) {
        orderBy = "0";
    } else {
        orderBy = $('#orderBy option:selected').val();
    }
    inforSearch = {
        fullName: fullName,
        orderBy: orderBy,
        pageNumber: '0'
    }
    $('tbody').html("");
    $('.table-paging').html("");
    search();
});

/*Load teacher list*/
function search() {
    $.ajax({
        url: '/api/admin/teacherlist',
        type: 'POST',
        data: JSON.stringify(inforSearch),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            if (data.message.messageCode == 0) {
                for (var i = 0; i < data.teacherList.totalPages; i++) {
                    if (i == inforSearch.pageNumber) {
                        $('.table-paging').append(
                            `<input type="button" value="` + (i + 1) + `" class="table-paging__page table-paging__page_cur"/>`
                        );
                    } else {
                        $('.table-paging').append(
                            `<input type="button" value="` + (i + 1) + `" class="table-paging__page"/>`
                        );
                    }
                }

                $.each(data.teacherList.content, function (i, item) {
                    var phone, email, status;
                    if (item.status == null || item.status == 0) {
                        status = `<span id="true" class="status-active"><i class="fa fa-circle" aria-hidden="true"></i></span>`;
                    } else {
                        status = `<span id="false" class="status-deactive"><i class="fa fa-circle" aria-hidden="true"></i></span>`;
                    }
                    if (item.phone == null) {
                        phone = "";
                    } else {
                        phone = item.phone;
                    }
                    if (item.email == null) {
                        email = "";
                    } else {
                        email = item.email;
                    }
                    var selected = "";
                    var checked = ""
                    if (isCheck(item.teacherId)) {
                        selected = "selected"
                        checked = "checked"
                    }
                    $('tbody').append(
                        `<tr class="` + selected + `">
                <td>
                    <span class="custom-checkbox ">
                        <input id="` + item.teacherId + `"type="checkbox" name="options" value="` + item.teacherId + `" ` + checked + `>
                        <label for="` + item.teacherId + `"></label>
                    </span>
                </td>
                <td><span id="fullName">` + item.fullName + `</span></td>
                <td><span id="teacherIdentifier">` + item.teacherIdentifier + `</span></td>
                <td><span id="phone">` + phone + `</span></td>
                <td><span id="email">` + email + `</span></td>
                <td><span id="status">` + status + `</span></td>
                <td><span class="bt-table-field"><a href="teacherInformation" id="` + item.teacherId + `" class="bt-table-edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></input></span>
                </td>
                </tr>`);
                });
                getTeacherID();
                selectCheckbox();
                pagingClick();
            } else {
                $('tbody').append(
                    `<tr>
                        <td colspan="7" class="userlist-result">
                            ` + data.message.message + `
                        </td>
                    </tr>`
                )
            }
        },
        failure: function (errMsg) {
            $('tbody').append(
                `<tr>
                        <td colspan="7" class="userlist-result">
                            ` + errMsg + `
                        </td>
                    </tr>`
            )
        },
        dataType: "json",
        contentType: "application/json"
    });
}

/*Edit teacher information by ID*/
function getTeacherID() {
    var teacherId = $('.bt-table-edit');
    $(teacherId).on('click', function (e) {
        teacherId = $(this).prop('id');
        localStorage.setItem("teacherId", teacherId);
    });
}

/*Delete teacher*/
$("#deleteTeacher").click(function (e) {
    listTeacher = {
        listTeacher: listTeacher,
    }
    e.preventDefault();
    $.ajax({
        url: '/api/admin/deleteteacher',
        type: 'POST',
        data: JSON.stringify(listTeacher),
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
                $('.img-success').removeClass('hide');
                $('.img-fail').addClass('hide');
                $('#message-delete').text(message);
            } else {
                $('.img-success').addClass('hide');
                $('.img-fail').removeClass('hide');
                $('#message-delete').text(message);
            }
        },
        failure: function (errMsg) {
            $('#message-delete').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
});

function isCheck(teacher) {
    if (listTeacher == null || listTeacher.length == 0) {
        return false;
    }
    for (var i = 0; i < listTeacher.length; i++) {
        if (listTeacher[i] == teacher) {
            return true;
        }
    }
    return false;
}

function pagingClick() {
    $('.table-paging input').on('click', function (event) {
        $("#selectAll").prop("checked", false);
        var value = ($(this).val() - 1);
        inforSearch.pageNumber = value;
        $('tbody').html("");
        $('.table-paging').html("");
        search();
    })
}

/*Check teacher before delete account*/
function checkTeacher() {
    $('#deleteTeacherModal .modal-body').html("");
    if (listTeacher.length == 0) {
        $('#deleteTeacherModal .modal-body').append(`<h5>Hãy chọn giáo viên mà bạn muốn xóa</h5>`);
        $('#deleteTeacherModal .modal-footer .btn-danger').addClass('hide');
        $('#deleteTeacherModal .modal-footer .btn-primary').attr('value', 'ĐÓNG');
    } else {
        $('#deleteTeacherModal .modal-body').append(`<h5>Bạn có chắc muốn <b>XÓA</b> giáo viên này không?</h5>`);
        $('#deleteTeacherModal .modal-footer .btn-danger').removeClass('hide');
        $('#deleteTeacherModal .modal-footer .btn-primary').attr('value', 'KHÔNG');
    }
}

/*Select checkbox*/
function selectCheckbox() {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
            if (jQuery.inArray($(this).val(), listTeacher) == -1) {
                listTeacher.push($(this).val());
            }
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            listTeacher = $.grep(listTeacher, function (value) {
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
                if (jQuery.inArray($(this).val(), listTeacher) == -1) {
                    listTeacher.push($(this).val());
                }
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
                $('tbody tr').removeClass('selected');
                var removeItem = $(this).val();
                listTeacher = $.grep(listTeacher, function (value) {
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