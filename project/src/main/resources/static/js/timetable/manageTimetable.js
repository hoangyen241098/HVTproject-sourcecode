
$("#timetableform").submit(function (e) {
    //gửi date lên, nếu messagecode =0 hoạc confirm có thì gửi data lên
    e.preventDefault(); // avoid to execute the actual submit of the form.
    var date = $('#date-input')[0].value;
    var input = {
        date: date,
    }
    $.ajax({
        url: '/api/timetable/checkDate',
        type: 'POST',
        data:JSON.stringify(input),
        success: function (data) {
            console.log(data);
            if(data.messageCode == 1){
                //chỗ này hiện dialog lỗi
                console.log(data.message);
            }
            else if(data.messageCode == 2){
                //gọi dialog confirm có muốn ghi đè không nếu có thì gọi update();
                console.log(data.message);
                update();
            }
            else {
                update();
            }
        },
        failure: function (errMsg) {
            console.log(errMsg);
        },
        dataType: 'JSON',
        contentType: "application/json"
    });

});

function update() {
    var form = $('#timetableform');
    // var url = form.attr('action');
    var formData = new FormData(form[0]);

    $.ajax({
        type: "POST",
        //url: "manageTimetable",
        url: "/api/timetable/update",
        data: formData,//form.serialize(), // serializes the form's elements.
        async: false,
        success: function (data) {
            console.log(data)
        },
        failure: function (errMsg) {
            console.log(errMsg);
        },
        cache: false,
        contentType: false,
        processData: false,
    });
}