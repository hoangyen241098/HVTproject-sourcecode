/* Valude default */
var roleID = localStorage.getItem("roleID");
var username = localStorage.getItem("username");
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

if (roleID == 1) {
  $(".form-check").removeClass("hide");
}

/* Save button */
$("#savePost").on("click", function () {
  var titleName = $("#titleName").val().trim();
  var image = $("#imagePreview").attr("src");
  var data = editor.getData();
  var gim;
  if ($('input[type="checkbox"]').prop("checked") == true) {
    gim = 1;
  } else {
    gim = 0;
  }
  if (titleName == "") {
    $(".createPost-err").text("Hãy nhập tiêu đề của bài viết.");
    return false;
  } else if (!image) {
    $(".createPost-err").text("Hãy nhập ảnh bìa của bài viết.");
    return false;
  } else if (data == "") {
    $(".createPost-err").text("Hãy nhập nội dung của bài viết.");
    return false;
  } else {
    var request = {
      username: username,
      header: titleName,
      headerImage: image,
      content: data,
      gim: gim,
      roleId: roleID,
    };
    if (gim == 1) {
      request.gim = 0;
      $("#saveModal .modal-footer").html(`
                    <input type="button" class="btn btn-danger" id="newGim" value="XÁC NHẬN">
                    <input type="button" class="btn btn-primary" id="closeBtn" data-dismiss="modal" value="ĐÓNG">
                `);
      messageModal(
        "saveModal",
        "img/img-question.png",
        "Bạn có muốn <b>GHIM</b> bài viết này không?<h6>Bài viết được ghim trước đó sẽ bị bỏ ghim!</h6>"
      );
      $("#newGim").on("click", function () {
        request.gim = 1;
        addNewPost(request);
      });
    } else {
      addNewPost(request);
    }
  }
});

function addNewPost(request) {
  $.ajax({
    url: "/api/newsletter/addnewsletter",
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
        $("#saveModal .modal-footer").html(
          `
                    <a href="postDetail?id=` +
            data.newsletterId +
            `" class="btn btn-danger" id="viewPost">XEM BÀI VIẾT</a>
                    <a href="createPost" class="btn btn-primary">ĐÓNG</a>
                `
        );
        messageModal(
          "saveModal",
          "img/img-success.png",
          "Tạo bài viết thành công!"
        );
      } else {
        messageModal("saveModal", "img/img-error.png", message);
      }
    },
    failure: function (errMsg) {
      messageModal("saveModal", "img/img-error.png", errMsg);
    },
    dataType: "json",
    contentType: "application/json",
  });
}

/* Upload image */
var loadFile = function (event) {
  const CLOUDINARY_URL =
    "https://api.cloudinary.com/v1_1/dyvyvyris/image/upload";
  const UPLOAD_PRESET = "dnewusot";
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
