/*Set navigation bar*/
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

/*Loading page*/
$(document).on({
    ajaxStart: function () {
        $('body').addClass("loading");
    },
    ajaxComplete: function () {
        $('body').removeClass("loading");
    }
})

/*Select Checkbox*/
function selectCheckbox(list) {
    var checkbox = $('table tbody input[type="checkbox"]');
    $(checkbox).on('change', function (e) {
        row = $(this).closest('tr');
        if ($(this).is(':checked')) {
            row.addClass('selected');
            if (jQuery.inArray($(this).val(), list) == -1) {
                list.push($(this).val());
            }
        } else {
            row.removeClass('selected');
            var removeItem = $(this).val();
            list = $.grep(list, function (value) {
                return value != removeItem;
            });
        }
    });

    $("#selectAll").click(function () {
        var checkbox = $('table tbody input[type="checkbox"]');
        if (this.checked) {
            checkbox.each(function () {
                this.checked = true;
                $('tbody tr').addClass('selected');
                if (jQuery.inArray($(this).val(), list) == -1) {
                    list.push($(this).val());
                }
            });
        } else {
            checkbox.each(function () {
                this.checked = false;
                $('tbody tr').removeClass('selected');
                var removeItem = $(this).val();
                list = $.grep(list, function (value) {
                    return value != removeItem;
                });
            });
        }
    });
    checkbox.click(function () {
        if (!this.checked) {
            $("#selectAll").prop("checked", false);
        }
    });
}