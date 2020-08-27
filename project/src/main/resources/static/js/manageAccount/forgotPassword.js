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
                        $('.table').append(`<tr><td class="font-500">` + item.name + `</td><td class="font-500">` + validatePhone(item.phone) + `</td></tr>`)
                    } else {
                        $('.table').append(`<tr><td class="font-500">` + item.name + `</td></tr>`)
                    }
                })
            } else {
                $('.table').html(`<tr><td class="font-500">Chưa có thông tin liên hệ!</td></tr>`);
            }
        } else {
            $('.table').html(`<tr><td class="font-500">` + message + `</td></tr>`);
        }
    },
    failure: function (errMsg) {
        $('.table').html(`<tr><td class="font-500">` + errMsg + `</td></tr>`);
    },
    dataType: "json",
    contentType: "application/json"
});
