var inforSearch = {
    userName: "",
    roleId: null,
    sortBy: "1",
    orderBy: "0",
    pageNumber: 0
}
var listUser = [];

/*Load role list*/
$.ajax({
    url: '/api/admin/rolelist',
    type: 'POST',
    success: function (data) {
        $.each(data.listRole, function (i, item) {
            $('#role-name').append(`<option value="` + item.roleId + `">` + item.roleName + `</option>`);
        });
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});
search();

/*Search button*/
$("#search").click(function () {
    var userName, roleId, sortBy, orderBy, pageNumber;
    userName = $('#searchByUsername input').val().trim();
    if ($('#role-name option:selected').val() == null) {
        roleId = null;
    } else {
        roleId = $('#role-name option:selected').val();
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
        userName: userName,
        roleId: roleId,
        sortBy: sortBy,
        orderBy: orderBy,
        pageNumber: 0
    }
    $('tbody').html("");
    $('.table-paging').html("");
    search();
});

/*Load user list*/
function search() {
    $.ajax({
        url: '/api/admin/userlist',
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
                var totalPages = data.userList.totalPages;

                // for (var i = 0; i < totalPages; i++) {
                //     if (i == inforSearch.pageNumber) {
                //         $('.table-paging').append(
                //             `<input type="button" value="` + (i + 1) + `" class="table-paging__page table-paging__page_cur"/>`
                //         );
                //     } else {
                //         $('.table-paging').append(
                //             `<input type="button" value="` + (i + 1) + `" class="table-paging__page"/>`
                //         );
                //     }
                // }

                $('.table-paging').append(
                    `<button type="button" class="btn btn-page btn-prev" id="prevPage" title="Trang trước"><i class="fa fa-chevron-left"></i></button>`
                );
                $('.table-paging').append(
                    `<button type="button" class="btn btn-page btn-next" id="nextPage" title="Trang sau"><i class="fa fa-chevron-right"></i></button>`
                );
                $('.table-paging').append(
                    `<div class="pageNumber"><b>` + (inforSearch.pageNumber + 1) + ` </b>/ ` + totalPages + `</div>`
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
                $.each(data.userList.content, function (i, item) {
                    var mappingName, phone, email, name, roleName;
                    if (item.name == null) {
                        name = "";
                    } else {
                        name = item.name;
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
                    if (item.classSchool == null) {
                        mappingName = "";
                    } else {
                        mappingName = item.classSchool.grade + " " + item.classSchool.giftedClass.name;
                    }
                    if (item.role == null) {
                        roleName = "";
                    } else {
                        roleName = item.role.roleName;
                    }
                    var selected = "";
                    var checked = ""
                    if (isCheck(item.username)) {
                        selected = "selected"
                        checked = "checked"
                    }
                    $('tbody').append(
                        `<tr class="` + selected + `">
                <td>
                    <span class="custom-checkbox ">
                        <input id="` + item.username + `"type="checkbox" name="options" value="` + item.username + `" ` + checked + `>
                        <label for="` + item.username + `"></label>
                    </span>
                </td>
                <td><span id="userName">` + item.username + `</span></td>
                <td><span id="fullName">` + name + `</span></td>
                <td><span id="roleName">` + roleName + `</span></td>
                <td><span id="className">` + mappingName + `</span></td>
                <td><span id="phone">` + phone + `</span></td>
                <td><span id="email">` + email + `</span></td>
                </tr>`);
                });
                selectCheckbox();
                prevPage();
                nextPage();
                // pagingClick();
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
                    <td colspan="7" class="userlist-result">` + errMsg + ` </td>
                </tr>`
            )
        },
        dataType: "json",
        contentType: "application/json"
    });
}

/*Delete account*/
$("#deleteAccount").click(function (e) {
    listUser = {
        listUser: listUser,
    }
    e.preventDefault();
    $.ajax({
        url: '/api/admin/deleteaccount',
        type: 'POST',
        data: JSON.stringify(listUser),
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

/*Reset Account*/
$("#resetPassword").click(function (e) {
    $('.resetPass-err').text("");
    var newpassword = $('#new-password').val().trim();
    var confirmpassword = $('#confirm-password').val().trim();

    if (newpassword == "" && confirmpassword == "") {
        $('.resetPass-err').text("Hãy điền đầy đủ tất cả các trường.");
        return false;
    } else if (newpassword == "") {
        $('.resetPass-err').text("Hãy điền mật khẩu mới.");
        return false;
    } else if (confirmpassword == "") {
        $('.resetPass-err').text("Hãy xác nhận lại mật khẩu.");
        return false;
    } else if (newpassword != confirmpassword) {
        $('.resetPass-err').text("Mật khẩu xác nhận không đúng.");
        return false;
    } else {
        var resetPassword = {
            userNameList: listUser,
            passWord: newpassword,
        }
        e.preventDefault();
        $.ajax({
            url: '/api/admin/resetpassword',
            type: 'POST',
            data: JSON.stringify(resetPassword),
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
                    $('#message-reset').text(message);
                } else {
                    $('.img-success').addClass('hide');
                    $('.img-fail').removeClass('hide');
                    $('#message-reset').text(message);
                }
            },
            failure: function (errMsg) {
                $('#message-reset').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
});

function isCheck(username) {
    if (listUser == null || listUser.length == 0) {
        return false;
    }
    for (var i = 0; i < listUser.length; i++) {
        if (listUser[i] == username) {
            return true;
        }
    }
    return false;
}

// function pagingClick() {
//     $('.table-paging input').on('click', function (event) {
//         $("#selectAll").prop("checked", false);
//         var value = ($(this).val() - 1);
//         inforSearch.pageNumber = value;
//         $('tbody').html("");
//         $('.table-paging').html("");
//         search();
//     })
// }
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

/*Check user before delete account*/
function checkUser() {
    $('#deleteAccountModal .modal-body').html("");
    var userErr = localStorage.getItem("username");
    if (jQuery.inArray(userErr, listUser) != -1) {
        $('#deleteAccountModal .modal-body').append(`<h5>Bạn không thể xoá tài khoản <b class="error">` + userErr + `</b></h5>`);
        $('#deleteAccountModal .modal-footer .btn-danger').addClass('hide');
        $('#deleteAccountModal .modal-footer .btn-primary').attr('value', 'ĐÓNG');
    } else if (listUser.length == 0) {
        $('#deleteAccountModal .modal-body').append(`<h5>Hãy chọn tài khoản mà bạn muốn xóa</h5>`);
        $('#deleteAccountModal .modal-footer .btn-danger').addClass('hide');
        $('#deleteAccountModal .modal-footer .btn-primary').attr('value', 'ĐÓNG');
    } else {
        $('#deleteAccountModal .modal-body').append(`<h5>Bạn có chắc muốn <b>XÓA</b> tài khoản này không?</h5>`);
        $('#deleteAccountModal .modal-footer .btn-danger').removeClass('hide');
        $('#deleteAccountModal .modal-footer .btn-primary').attr('value', 'KHÔNG');
    }
}

/*Check user before reset password*/
function checkResetPassword() {
    if (listUser.length == 0) {
        $('#resetPasswordModal .modal-body .form-group').addClass('hide');
        $('#resetPasswordModal .modal-body').append(`<h5>Hãy chọn tài khoản mà bạn muốn đặt lại mật khẩu</h5>`);
        $('#resetPasswordModal .modal-footer .btn-danger').addClass('hide');
        $('#resetPasswordModal .modal-footer .btn-primary').attr('value', 'ĐÓNG');
    } else {
        $('#resetPasswordModal .modal-body .form-group').removeClass('hide');
        $('#resetPasswordModal .modal-body h5').addClass('hide');
        $('#resetPasswordModal .modal-footer .btn-danger').removeClass('hide');
        $('#resetPasswordModal .modal-footer .btn-primary').attr('value', 'KHÔNG');
    }
}

/*Select checkbox*/
function selectCheckbox() {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
            if (jQuery.inArray($(this).val(), listUser) == -1) {
                listUser.push($(this).val());
            }
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            listUser = $.grep(listUser, function (value) {
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
                if (jQuery.inArray($(this).val(), listUser) == -1) {
                    listUser.push($(this).val());
                }
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
                $('tbody tr').removeClass('selected');
                var removeItem = $(this).val();
                listUser = $.grep(listUser, function (value) {
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
