// $(".panel-collapse").hide();

$(".panel-heading").click(function () {
    $(this).parent().find(".fa-chevron-down").toggleClass("up");
});
