<!--Send data to JSON-->
$(document).ready(function () {

    $("#signin").click(function (e) {
        var user = {
            username: $('#username').val(),
            password: $('#password').val(),
        };

        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/api/user/login",
            data: JSON.stringify(user),
            success: function (data) {
                var message = data.message.message;
                // alert(message);
                if (message == "Thành công") {
                    $('#loginSuccess').css('display','block');
                }
                else{
                    $('.errorTxt').text(message);
                }
            },
            failure: function (errMsg) {
                $('.errorTxt').text(errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });

    });
});


// <!--Get data from JSON-->
// (function () {
//     var settings = {
//         "url": "http://localhost:8080/api/user/login",
//         "method": "POST",
//         "timeout": 0,
//         "format": "json",
//     };
//
//     $.getJSON(settings).done(function (data) {
//         $.each(data, function (i, item) {
//             $("#json-item").append(item.name + ": Username: " + item.username + "   |   Password: " + item.password);
//         });
//     });
// })();