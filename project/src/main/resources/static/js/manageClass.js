localStorage.removeItem("classId");
$.ajax({
    url: '/api/admin/classtable',
    type: 'POST',
    beforeSend: function () {
        $('body').addClass("loading")
    },
    complete: function () {
        $('body').removeClass("loading")
    },
    success: function (data) {
        if (data.message.messageCode == 0) {
            var id = 0;
            $.each(data.classList, function (i, item) {
                id +=1;
                var grade, classIdentifier, status, giftedClassName;
                if (item.grade == null) {
                    grade = "";
                } else {
                    grade = item.grade;
                }
                if (item.classIdentifier == null) {
                    classIdentifier = "";
                } else {
                    classIdentifier = item.classIdentifier;
                }
                if (item.status == null) {
                    status = "";
                } else {
                    status = item.status;
                }
                if (item.giftedClass == null) {
                    giftedClassName = "";
                } else {
                    giftedClassName = item.giftedClass.name;
                }

                $('tbody').append(
                    `<tr>
                        <td><span>` + id + `</span></td>
                        <td><span id="grade">` + grade + `</span></td>
                        <td><span id="giftedClassName">` + giftedClassName + `</span></td>
                        <td><span id="classIdentifier">` + classIdentifier + `</span></td>
                        <td><span id="status">` + status + `</span></td>
                        <td><span class="bt-table-field"><a href="editClass" id="${item.classId}" class="bt-table-edit">
                         <i class="fa fa-pencil-square-o" aria-hidden="true"></i></input></span></td>
                    </tr>`
                );
            });
            getClassID();
        } else {
            $('tbody').append(
                `<tr>
                    <td colspan="7" class="userlist-result">
                        ` + data.message.message + `
                    </td>
                </tr>`
            )
        }

    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Edit class information by ID*/
function getClassID() {
    var classId = $('.bt-table-edit');
    $(classId).on('click', function (e) {
        classId = $(this).prop('id');
        localStorage.setItem("classId", classId);
        console.log(localStorage.getItem("classId"));
    });
}