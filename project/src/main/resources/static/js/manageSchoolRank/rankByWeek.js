$("#editGrades").on("click", function () {
    var row = $('tbody tr td[contenteditable]');
    var editOn = $('#editGrades').hasClass("editMode");

    if (editOn == false) {
        $(row).attr('contenteditable', 'true');
        $(row).css('background-color', '#fdf1f1');
        $('#editGrades').attr('value', 'Lưu thay đổi');
        $('#editGrades').addClass('editMode');
    } else if (editOn == true) {
        $(row).attr('contenteditable', 'false');
        $(row).css('background-color', 'transparent');
        $('#editGrades').attr('value', 'Sửa điểm');
        $('#editGrades').removeClass('editMode');
    }
});
$(document).ready(function () {
    $("#byClass").select2();
    $('table').DataTable(
        {
            "lengthMenu": [ 30 ],
            "bLengthChange": false,
            "bFilter": false,
            "bInfo": false,
            "paging": false
        }
    );
});
