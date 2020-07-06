function toggleClick() {
    $(".panel-heading").on('click', function () {
        $(this).find(".fa-chevron-down").toggleClass("up");
    })

}