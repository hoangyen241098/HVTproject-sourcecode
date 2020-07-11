$(document).ready(function () {
    $("#gifftedClass").select2();
    $('.violation-date').css('width', '26%');
});

$('#fromYear').change(function () {
    var fromYear = $(this).val();
    $('#toYear').val(parseInt(fromYear) + 3);
});