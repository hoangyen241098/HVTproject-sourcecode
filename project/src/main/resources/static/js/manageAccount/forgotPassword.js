$.ajax({
    type: 'POST',
    url: "/api/user/getAdminInfor",
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
            if (data.userList.content.length != 0) {
                $.each(data.userList.content, function (i, item) {
                    if (item.phone != null) {
                        if (item.name != "") {
                            $('.admin-info').append("<h5 class='text-left'>" + item.name + " - " + validatePhone(item.phone) + "</h5>")
                        } else {
                            $('.admin-info').append("<h5 class='text-left'>" + validatePhone(item.phone) + "</h5>")
                        }
                    } else {
                        $('.table .form-title').html('Chưa cập nhật Hotline!');
                        $('.admin-info').addClass('hide');
                    }
                })
            } else {
                $('.table .form-title').html('Chưa cập nhật Hotline!');
                $('.admin-info').addClass('hide');
            }
        } else {
            $('.table .form-title').html(message);
            $('.admin-info').addClass('hide');
        }
    },
    failure: function (errMsg) {
        $('.table .form-title').html(errMsg);
        $('.admin-info').addClass('hide');
    },
    dataType: "json",
    contentType: "application/json"
});
