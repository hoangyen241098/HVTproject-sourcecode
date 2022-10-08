/*Value defaul*/
$("#fromDate").val(moment().format("YYYY-MM-DD"));
$("#toDate").val(moment().format("YYYY-MM-DD"));
$("#toDate").attr("min", $("#fromDate").val());
$("#fromDate").change(function () {
  var fromDate = $(this).val();
  $("#toDate").attr("min", fromDate);
});

var classId, fromDate, toDate;
if (sessionStorage.getItem("classIdShowViolation") == null) {
  classId = sessionStorage.getItem("classIdShowViolation");
}
if (sessionStorage.getItem("fromDateShowViolation") == null) {
  date = moment().format("YYYY-MM-DD");
  $("#fromDate").val(date);
} else {
  date = sessionStorage.getItem("fromDateShowViolation");
  $("#fromDate").val(date);
}
if (sessionStorage.getItem("toDateShowViolation") == null) {
  date = moment().format("YYYY-MM-DD");
  $("#toDate").val(date);
} else {
  date = sessionStorage.getItem("toDateShowViolation");
  $("#toDate").val(date);
}

$(document).ready(function () {
  search();
  getClassList();
  download();
});

/* Load data to list */
function search() {
  $("tbody").html("");
  var fromDate = $("#fromDate").val();
  var toDate = $("#toDate").val();
  var classId = $("#classList").val();
  var infoSearch = {
    fromDate: fromDate,
    classId: classId,
    toDate: toDate,
  };
  if (fromDate == "err" || classId == "err") {
    $("tbody").html(
      `<tr><td colspan="7" class="text-center">Danh sách trống.</td></tr>`
    );
  } else {
    var t = $("table").dataTable({
      destroy: true,
      searching: false,
      bInfo: false,
      paging: false,
      order: [],
      ajax: {
        url: "/api/emulation/viewviolationOfClasses",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: function (d) {
          return JSON.stringify(infoSearch);
        },
        beforeSend: function () {
          $("body").addClass("loading");
        },
        complete: function () {
          $("body").removeClass("loading");
        },
        dataSrc: function (data) {
          var dataSrc = null;
          var messageCode = data.message.messageCode;
          var message = data.message.message;
          if (messageCode == 0) {
            if (data.viewViolationClassList != null) {
              dataSrc = data.viewViolationClassList;
              $.each(data.viewViolationClassList, function (i, item) {
                item.score = (item.substractGrade * item.quantity).toFixed(2);
              });
              $("#download").removeClass("hide");
            } else {
            $("#download").addClass("hide");
              return false;
            }
          } else {
            $("#download").addClass("hide");
            $("tbody").append(
              `<tr><td colspan="7" class="text-center"> ` +
                message +
                ` </td></tr>`
            );
            return false;
          }
          return dataSrc;
        },
        failure: function (errMsg) {
          $("#download").addClass("hide");
          $("tbody").append(
            `<tr><td colspan="7" class="userlist-result">` +
              errMsg +
              `</td></tr>`
          );
        },
      },
      columns: [
        { data: "createDate" },
        { data: "dayName" },
        { data: "description" },
        { data: "className" },
        { data: "note" },
        { data: "createBy" },
        { data: "score" },
      ],
      drawCallback: function (settings) {
        settings.oLanguage.sEmptyTable = "Danh sách lỗi của lớp đang trống.";
      },
    });
  }
}

/* Set data classList to combobox */
function getClassList() {
  $.ajax({
    url: "/api/admin/classlist",
    type: "POST",
    beforeSend: function () {
      $("body").addClass("loading");
    },
    complete: function () {
      $("body").removeClass("loading");
    },
    success: function (data) {
      var messageCode = data.message.messageCode;
      var message = data.message.message;
      if (messageCode == 0) {
        if (data.classList != null) {
          $("#classList").select2();
          $("#classList").html(`<option value="" selected>Tất cả</option>`);
          $.each(data.classList, function (i, item) {
            $("#classList").append(
              `<option value="` +
                item.classID +
                `">` +
                item.className +
                `</option>
                        `
            );
          });
        } else {
          $("#classList").html(`<option selected>` + message + `</option>`);
        }
      } else {
        $("#classList").html(`<option selected>` + message + `</option>`);
      }
      search();
    },
    failure: function (errMsg) {
      $("#classList").html(`<option selected>` + errMsg + `</option>`);
    },
    dataType: "json",
    contentType: "application/json",
  });
}

/*===============Download===================*/

/*Download button*/
function download() {
  $("#download").click(function () {
    var fromDate = $("#fromDate").val();
    var toDate = $("#toDate").val();
    var classId = $("#classList").val();
    var download = {
      fromDate: fromDate,
      classId: classId,
      toDate: toDate,
    };
    $.ajax({
      url: "/api/emulation/downloadViolationOfClasses",
      type: "POST",
      data: JSON.stringify(download),
      xhrFields: {
        responseType: "blob",
      },
      beforeSend: function () {
        $("body").addClass("loading");
      },
      complete: function () {
        $("body").removeClass("loading");
      },
      success: function (data) {
        var a = document.createElement("a");
        var url = window.URL.createObjectURL(data);
        var name = "TỔNG-HỢP-VI-PHẠM.xls";
        a.href = url;
        a.download = name;
        document.body.append(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url);
      },
      statusCode: {
        400: function (errMsg) {
          messageModal(
            "messageModal",
            "img/img-error.png",
            "Không thể tải được tập tin!"
          );
        },
      },
      dataType: "binary",
      contentType: "application/json",
    });
  });
}

/* Search button */
$("#search").click(function () {
  search();
});

if (roleID != 1) {
  $(".manageBtn").addClass("hide");
}
