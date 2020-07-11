$('#date-input').change(function () {
    var dateInput = $(this).val();
    console.log(dateInput);
    $('.input-err').text(dateInput);
})