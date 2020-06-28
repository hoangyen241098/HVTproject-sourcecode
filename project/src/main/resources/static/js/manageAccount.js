$.ajax({
    url: '/api/admin/rolelist',
    type: 'POST',
    success: function (data) {
        $.each(data, function (i, item) {
            $.each(item, function (i, list) {
                $('#search-role').append(`<li value="` + list.roleId + `">` + list.roleName + `</li>`);
            });
        });
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});
var inforSearch = {
    userName: "",
    roleId: "",
    sortBy: "0",
    orderBy: "0",
    pageNumber: "0"
}
$.ajax({
    url: '/api/admin/userlist',
    type: 'POST',
    data: JSON.stringify(inforSearch),
    success: function (data) {
        $.each(data.userList.content, function (i, item) {
            var mappingName, phone, email;
            if (item.classSchool == null) {
                mappingName = "";
            } else {
                mappingName = item.classSchool.mappingName;
            }
            if (item.phone == null) {
                phone = "";
            } else {
                phone = item.phone;
            }
            if (item.email == null) {
                email = "";
            } else {
                email = item.email;
            }
            $('tbody').append(
                `<tr>
                <td>
                    <span class="custom-checkbox">
                        <input id="` + item.username + `"type="checkbox" name="options[]" value="1">
                        <label for="` + item.username + `"></label>
                    </span>
                </td>
                <td><span id="userName">` + item.username + `</span></td>
                <td><span id="fullName">` + item.name + `</span></td>
                <td><span id="roleName">` + item.role.roleName + `</span></td>
                <td><span id="className">` + mappingName + `</span></td>
                <td><span id="phone">` + phone + `</span></td>
                <td><span id="email">` + email + `</span></td>
                <td>
                    <a th:href="@{editAccount/id}" class="edit" data-toggle="modal">
                        <i class="material-icons" data-toggle="tooltip" title="Sửa">&#xE254;</i>
                    </a>
                </td>
            </tr>`);
        });
    },
    failure: function (errMsg) {
        console.log(errMsg);
    },
    dataType: "json",
    contentType: "application/json"
});

$("#search").click(function (e) {
    console.log("username = " + $('#searchByUsername input').val());
    console.log("sortById = " + $('#sortBy input').attr("value"));
    console.log("roleId = " + $('#role-name input').attr("value"));
    console.log("orderById = " + $('#orderBy input').attr("value"));
});