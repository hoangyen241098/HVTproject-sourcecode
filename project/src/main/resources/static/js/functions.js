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

$(document).on({
    ajaxStart: function () {
        $('body').addClass("loading");
    },
    ajaxComplete: function () {
        $('body').removeClass("loading");
    }
})
