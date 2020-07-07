$("#submit").click(function (e) {
    var name = $('#description').val().trim();
    var totalGrade = $('#totalGrade').val().trim();

    if(name == ""){
        $('.addViolationType-err').text("Hãy nhập mô tả nội quy!");
        return false;
    }
    if (totalGrade == "" || Number.isInteger(totalGrade)) {
        $('.addViolationType-err').text("Hãy nhập điểm cho nội quy!");
        return false;
    }
    var model ={
        name : name,
        totalGrade : totalGrade
    };
    e.preventDefault();
    $.ajax({
        url: '/api/admin/addviolationtype',
        type: 'POST',
        data: JSON.stringify(model),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            var messageCode = data.messageCode;
            var message = data.message;
            if (messageCode == 0) {
                $('#createSuccess').css('display', 'block');
                $('.addViolationType-err').text("");
            } else {
                $('.addViolationType-err').text(message);
            }
        },
        failure: function (errMsg) {
            $('.addViolationType-err').text(errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
});