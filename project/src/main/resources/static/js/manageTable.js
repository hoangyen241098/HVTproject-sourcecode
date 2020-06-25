/*Checkbox: Select all*/
$(document).ready(function () {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
        } else {
            row.removeClass('selected');
        }
    });

    // Select/Deselect checkboxes

    $("#selectAll").click(function () {
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
            });
            $('tbody tr').addClass('selected');
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
            $('tbody tr').removeClass('selected');
        }

    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
});
