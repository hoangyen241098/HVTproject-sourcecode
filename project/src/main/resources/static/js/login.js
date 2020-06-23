<!--Include HTML-->
$(document).ready(function () {
    $("div[data-includeHTML]").each(function () {
        $(this).load($(this).attr("data-includeHTML"));
    });
});

<!--Get data from JSON-->
// (function () {
//     var settings = {
//         "url": "http://localhost:8080/api/v1/user",
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

<!--Send data to JSON-->
// $('form').submit(function (e) {
//     e.preventDefault();
//     var serialized = $(this).serializeArray();
//     var s = '';
//     var data = {};
//     for(s in serialized){
//         data[serialized[s]['name']] = serialized[s]['value']
//     }
//     data = JSON.stringify(data);
//     $(".result").append(data);
//     return data;
// });

<!--Send data to AJAX-->
// var data={
//     username: $('#username').val(),
//     email: $('#email').val(),
//     password: $('#password').val(),
//     //add other properties similarly
// }
// $.ajax({
//     type: "POST",
//     url: "http://localhost:8080/api/v1/get",
//     data: $("#registerSubmit").serialize(),
//
//     success: function(html)
//     {
//         $('#userError').html(html);
//         $("#userError").html(userChar);
//         $("#userError").html(userTaken);
//         $(".result").append(html);
//         $(".result").append(data);
//     }
// });

