/*Load role list*/
$.ajax({
    url: '/api/admin/rolelist',
    type: 'POST',
    success: function (data) {
        $.each(data.listRole, function (i, item) {
            $('#search-role').append(`<li value="` + item.roleId + `">` + item.roleName + `</li>`);
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
    roleId: "",
    sortBy: "0",
    orderBy: "0",
    pageNumber: "0"
}
var listUser = [];
/*Search button*/
$("#search").click(function () {
    var userName, roleId, sortBy, orderBy, pageNumber;
    userName = $('#searchByUsername input').val();
    pageNumber = 0;
    if ($('#role-name input').attr("value") == null) {
        roleId = 0;
    } else {
        roleId = $('#role-name input').attr("value");
    }
    if ($('#sortBy input').attr("value") == null) {
        sortBy = 0;
    } else {
        sortBy = $('#sortBy input').attr("value");
    }
    if ($('#orderBy input').attr("value") == null) {
        orderBy = 0;
    } else {
        orderBy = $('#orderBy input').attr("value");
    }
    inforSearch = {
        userName: userName,
        roleId: roleId,
        sortBy: sortBy,
        orderBy: orderBy,
        pageNumber: pageNumber
    }
    console.log(JSON.stringify(inforSearch));
    $('tbody').html("");
    search();
});
search();

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
            if (data.userList.content.length == 0) {
                $('tbody').append(
                    `<tr>
                        <td colspan="7" class="userlist-result">
                            Không có kết quả trả về!
                        </td>
                    </tr>`
                )
            }
            $.each(data.userList.content, function (i, item) {
                var mappingName, phone, email;
                if (item.classSchool == null) {
                    mappingName = "";
                } else {
                    mappingName = item.classSchool.mappingName;
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
                $('tbody').append(
                    `<tr>
                <td>
                    <span class="custom-checkbox">
                        <input id="` + item.username + `"type="checkbox" name="options" value="` + item.username + `">
                        <label for="` + item.username + `"></label>
                    </span>
                </td>
                <td><span id="userName">` + item.username + `</span></td>
                <td><span id="fullName">` + item.name + `</span></td>
                <td><span id="roleName">` + item.role.roleName + `</span></td>
                <td><span id="className">` + mappingName + `</span></td>
                <td><span id="phone">` + phone + `</span></td>
                <td><span id="email">` + email + `</span></td>
            </tr>`);
            });
            selectCheckbox();
        },
        failure: function (errMsg) {
            console.log(errMsg);
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
    console.log(JSON.stringify(listUser));
    $.ajax({
        url: '/api/admin/deleteaccount',
        type: 'DELETE',
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
            console.log(errMsg);
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
                console.log(errMsg);
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
            // console.log("My favourite sports are: " + userList.join(", "));
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            listUser = $.grep(listUser, function (value) {
                return value != removeItem;
            });
        }
        console.log(listUser);
    });

    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
            $('tbody tr').addClass('selected');
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
            $('tbody tr').removeClass('selected');
        }

    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
}