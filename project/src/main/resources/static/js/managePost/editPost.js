/* Value default */
var roleID = localStorage.getItem("roleID");
var username = localStorage.getItem("username");
var newsletterId = sessionStorage.getItem("newsletterIdEdit");
var request = {
  newsletterId: newsletterId,
};
var editor = ClassicEditor.create(
  document.querySelector("#post-editor-text-content"),
  {
    licenseKey: "",
  }
)
  .then((editor) => {
    window.editor = editor;
  })
  .catch((error) => {
    console.error("Oops, something went wrong!");
    console.error(
      "Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:"
    );
    console.warn("Build id: k22235w312jz-o54unq4w8qk1");
    console.error(error);
  });
var oldHeader, oldHeaderImage, oldContent, oldGim, createDate, status;

/* View detail post */
$.ajax({
  url: "/api/newsletter/viewletter",
  type: "POST",
  data: JSON.stringify(request),
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
      if (data.newsletter.length != 0) {
        oldHeader = data.newsletter.header;
        oldHeaderImage = data.newsletter.headerImage;
        oldContent = data.newsletter.content;
        oldGim = data.newsletter.gim;
        createDate = data.newsletter.createDate;
        status = data.newsletter.status;
        $("#titleName").val(oldHeader);
        $("#imagePreview").prop("src", oldHeaderImage);
        $("#post-editor-text-content").text(oldContent);
        if (oldGim == 1) {
          $("input[value=pin]").prop("checked", true);
        }
      } else {
        $("#titleName").prop("disabled", true);
        $("#imageCover").prop("disabled", true);
        $("#savePost").prop("disabled", true);
        $("#post-editor-text-content").text(`<h1>Bài viết không tồn tại.</h1>`);
      }
    } else {
      $("#titleName").prop("disabled", true);
      $("#imageCover").prop("disabled", true);
      $("#savePost").prop("disabled", true);
      $("#post-editor-text-content").text(`<h1>` + message + `</h1>`);
    }
  },
  failure: function (errMsg) {
    $("#titleName").prop("disabled", true);
    $("#imageCover").prop("disabled", true);
    $("#savePost").prop("disabled", true);
    $("#post-editor-text-content").text(`<h1>` + errMsg + `</h1>`);
  },
  dataType: "json",
  contentType: "application/json",
});

/* Edit post */
$("#savePost").on("click", function () {
  var newHeader = $("#titleName").val().trim();
  var newHeaderImage = $("#imagePreview").attr("src");
  if (!newHeaderImage) {
    $(".editPost-err").text("Ảnh bìa của bài viết không đúng định dạng.");
    return false;
  }
  var newContent = editor.getData();
  var newGim;
  if ($('input[type="checkbox"]').prop("checked") == true) {
    newGim = 1;
  } else {
    newGim = 0;
  }
  if (
    oldHeader == newHeader &&
    oldHeaderImage == newHeaderImage &&
    oldContent == newContent &&
    oldGim == newGim
  ) {
    $(".editPost-err").text("Hãy thay đổi thông tin.");
    return false;
  } else if (newHeader == "") {
    $(".editPost-err").text("Hãy nhập tiêu đề của bài viết.");
    return false;
  } else if (newHeaderImage.trim() == "") {
    $(".editPost-err").text("Hãy nhập ảnh bìa của bài viết.");
    return false;
  } else if (newContent == "") {
    $(".editPost-err").text("Hãy nhập nội dung của bài viết.");
    return false;
  } else {
    $(".editPost-err").text("");
    var request = {
      newsletter: {
        newsletterId: newsletterId,
        userName: username,
        createDate: createDate,
        header: newHeader,
        headerImage: newHeaderImage,
        content: newContent,
        gim: oldGim,
        status: status,
      },
      roleId: roleID,
    };
    if (newGim != oldGim) {
      if (newGim == 1) {
        $("#messageModal .modal-footer").html(`
                    <input type="button" class="btn btn-danger" id="newGim" value="XÁC NHẬN">
                    <input type="button" class="btn btn-primary" id="closeBtn" data-dismiss="modal" value="ĐÓNG">
                `);
        messageModal(
          "messageModal",
          "img/img-question.png",
          "Bạn có muốn <b>GHIM</b> bài viết này không?<h6>Bài viết được ghim trước đó sẽ bị bỏ ghim!</h6>"
        );
        $("#newGim").on("click", function () {
          request.newsletter.gim = newGim;
          editPost(request);
        });
      } else {
        $("#messageModal .modal-footer").html(`
                <input type="button" class="btn btn-danger" id="newGim" value="XÁC NHẬN">
                <input type="button" class="btn btn-primary" id="closeBtn" data-dismiss="modal" value="ĐÓNG">
                `);
        messageModal(
          "messageModal",
          "img/img-question.png",
          "Bạn có muốn <b>BỎ GHIM</b> bài viết này không?"
        );
        $("#newGim").on("click", function () {
          request.newsletter.gim = newGim;
          editPost(request);
        });
      }
    } else {
      editPost(request);
    }
  }
});

function editPost(request) {
  $.ajax({
    url: "/api/newsletter/editnewsletter",
    type: "POST",
    data: JSON.stringify(request),
    beforeSend: function () {
      $("body").addClass("loading");
    },
    complete: function () {
      $("body").removeClass("loading");
    },
    success: function (data) {
      var messageCode = data.messageCode;
      var message = data.message;
      if (messageCode == 0) {
        $("#messageModal .modal-footer").html(
          `
                    <a href="postDetail?id=` +
            newsletterId +
            `" class="btn btn-danger" id="viewPost">XEM BÀI VIẾT</a>
                    <a href="editPost" class="btn btn-primary">ĐÓNG</a>
                `
        );
        messageModal(
          "messageModal",
          "img/img-success.png",
          "Sửa bài viết thành công!"
        );
      } else {
        $("#messageModal .modal-footer").html(
          `<a href="editPost" class="btn btn-primary">ĐÓNG</a>`
        );
        messageModal("messageModal", "img/img-error.png", message);
      }
    },
    failure: function (errMsg) {
      $("#messageModal .modal-footer").html(
        `<a href="editPost" class="btn btn-primary">ĐÓNG</a>`
      );
      messageModal("messageModal", "img/img-error.png", errMsg);
    },
    dataType: "json",
    contentType: "application/json",
  });
}

if (roleID != 1) {
  $(".form-check").hide();
}

/* Upload image */
var loadFile = function (event) {
  const CLOUDINARY_URL =
    "https://api.cloudinary.com/v1_1/dl6mxf4ua/image/upload";
  const UPLOAD_PRESET = "tknrahcw";
  let file = event.target.files[0];
  let formData = new FormData();
  formData.append("file", file);
  formData.append("upload_preset", UPLOAD_PRESET);
  $("body").addClass("loading");

  fetch(CLOUDINARY_URL, {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      return response.text();
    })
    .then((data) => {
      $("body").removeClass("loading");
      let src = JSON.parse(data).url;
      let output = $("#imagePreview");
      output.prop("src", src);
      output.prop("alt", "Ảnh bìa bài viết");
    })
    .catch((err) => {
      $("body").addClass("loading");
      messageModal("overrideSuccess", "img/img-error.png", err);
    });
};
