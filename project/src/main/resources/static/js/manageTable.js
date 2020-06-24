<!-- Show information of each user -->
$(function () {
    // attach table filter plugin to inputs
    $('.table-striped').on('click', '.panel-heading span.filter', function (e) {
        var $this = $(this),
            $panel = $this.parents('.panel');
        $panel.find('.panel-body').slideToggle();

        if ($panel.find('.clickable').text() == "Đóng") {
            $panel.find('.clickable').text('Mở');
        } else {
            $panel.find('.clickable').text('Đóng');
        }
    });
    $('[data-toggle="tooltip"]').tooltip();
})


// Checkbox: Select all
$(document).ready(function () {
    var checkbox = $('.table-tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('.table-tbody');
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
        } else {
            checkbox.each(function () {
                this.checked = false;
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
});
