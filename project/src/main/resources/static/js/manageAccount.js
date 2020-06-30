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
var inforSearch = {
    userName: "",
    roleId: null,
    sortBy: "0",
    orderBy: "0",
    pageNumber: "0"
}
var listUser = [];
/*Search button*/
$("#search").click(function () {
    var userName, roleId, sortBy, orderBy, pageNumber;
    userName = $('#searchByUsername input').val();
    if ($('#role-name option:selected').val() == null) {
        roleId = null;
    } else {
        roleId = $('#role-name option:selected').val();
    }
    if ($('#sortBy option:selected').val() == null) {
        sortBy = "0";
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
        pageNumber: '0'
    }
    console.log(JSON.stringify(inforSearch));
    $('tbody').html("");
    $('.table-paging').html("");
    search();
});
search();

function pagingClick() {
    $('.table-paging input').on('click', function (event) {
        var value = ($(this).val() - 1);
        inforSearch.pageNumber = value;
        $('tbody').html("");
        $('.table-paging').html("");
        search();
    })
}

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
                for (var i = 0; i < data.userList.totalPages; i++) {

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

                $.each(data.userList.content, function (i, item) {
                    var mappingName, phone, email, name;
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
                    var s = "";
                    var checked = ""
                    if (ischeck(item.username)) {
                        s = "selected"
                        checked = "checked"
                    }
                    $('tbody').append(
                        `<tr class="` + s + `">
                <td>
                    <span class="custom-checkbox ">
                        <input id="` + item.username + `"type="checkbox" name="options" value="` + item.username + `" ` + checked + `>
                        <label for="` + item.username + `"></label>
                    </span>
                </td>
                <td><span id="userName">` + item.username + `</span></td>
                <td><span id="fullName">` + name + `</span></td>
                <td><span id="roleName">` + item.role.roleName + `</span></td>
                <td><span id="className">` + mappingName + `</span></td>
                <td><span id="phone">` + phone + `</span></td>
                <td><span id="email">` + email + `</span></td>
            </trselected>`);
                });
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
            console.log(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

function ischeck(username) {
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

/*Delete account*/
$("#deleteAccount").click(function (e) {
    listUser = {
        listUser: listUser,
    }

    e.preventDefault();
    console.log(JSON.stringify(listUser));
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
    var newpassword = $('#new-password').val();
    var confirmpassword = $('#confirm-password').val();

    if (newpassword.trim() == "" && confirmpassword.trim() == "") {
        $('.resetPass-err').text("Hãy điền đầy đủ tất cả các trường.");
        return false;
    } else if (newpassword.trim() == "") {
        $('.resetPass-err').text("Hãy điền mật khẩu mới.");
        return false;
    } else if (confirmpassword.trim() == "") {
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

/*Select checkbox*/
function selectCheckbox() {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
            listUser.push($(this).val());
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            listUser = $.grep(listUser, function (value) {
                return value != removeItem;
            });
        }
    });

    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
            $('tbody tr').addClass('selected');
            listUser.push(checkbox.val());
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
            $('tbody tr').removeClass('selected');
            var removeItem = checkbox.val();
            listUser = $.grep(listUser, function (value) {
                return value != removeItem;
            });
        }

    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
    console.log(listUser)
}