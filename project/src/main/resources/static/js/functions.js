$(document).ready(function () {
    var loginSuccess = localStorage.getItem("loginSuccess");
    var roleID = localStorage.getItem("roleID");
    if (loginSuccess == 0) {
        $("#loginSuccess-menu").addClass("show");
        $('#login').css('display', 'none');
        if (roleID == 1) {
            $("#admin").addClass("show");
        }
        if (roleID == 2) {
            $("#schedule-manager").addClass("show");
        }
    } else {
        $('#login').css('display', 'block');
        $("#loginSuccess-menu").removeClass("show");
        $("#admin").removeClass("show");
        $("#schedule-manager").removeClass("show");
    }
    $("#logout").click(function () {
        localStorage.clear();
    })
});