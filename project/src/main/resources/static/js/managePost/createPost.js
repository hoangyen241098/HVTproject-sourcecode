/*Valude default*/
var roleID = localStorage.getItem("roleID");
var username = localStorage.getItem("username");
var editor = CKEDITOR.replace('post-editor-text-content', {
    cloudServices_uploadUrl: 'https://73438.cke-cs.com/easyimage/upload/',
    cloudServices_tokenUrl: 'https://73438.cke-cs.com/token/dev/de62f27633e0ccc284486ba070dbacf5b61e59390a805c23d58fc080b306',
    width: '100%',
    height: 500,
    extraPlugins: 'easyimage',
});
var imageCover = CKEDITOR.replace('imageCover', {
    cloudServices_uploadUrl: 'https://73438.cke-cs.com/easyimage/upload/',
    cloudServices_tokenUrl: 'https://73438.cke-cs.com/token/dev/de62f27633e0ccc284486ba070dbacf5b61e59390a805c23d58fc080b306',
    width: '100%',
    height: 100,
    extraPlugins: 'easyimage',
    removePlugins: 'image',
    removeDialogTabs: 'link:advanced',
    toolbar: [
        {
            name: 'insert',
            items: ['EasyImageUpload']
        }
    ],
});

/*Save button*/
$('#savePost').on('click', function () {
    var titleName = $('#titleName').val().trim();
    // var image = $('#imagePreview').attr('src');
    var image = imageCover.getData();
    image = image.split('src=')[1].split('"')[1];
    var data = editor.getData();
    console.log(image);
    if (titleName == "") {
        $('.createPost-err').text('Hãy nhập tiêu đề của bài viết.');
        return false;
    } else if (image == undefined) {
        $('.createPost-err').text('Hãy nhập ảnh bìa của bài viết.');
        return false;
    } else if (data == "") {
        $('.createPost-err').text('Hãy nhập nội dung của bài viết.');
        return false;
    } else {
        var request = {
            username: username,
            header: titleName,
            headerImage: image,
            content: data,
            roleId: roleID,
        }
        console.log(JSON.stringify(request))
        $.ajax({
            url: "/api/newsletter/addnewsletter",
            type: "POST",
            data: JSON.stringify(request),
            beforeSend: function () {
                $('body').addClass("loading")
            },
            complete: function () {
                $('body').removeClass("loading")
            },
            success: function (data) {
                var messageCode = data.message.messageCode;
                var message = data.message.message;
                sessionStorage.setItem('newsletterId', data.newsletterId);
                if (messageCode == 0) {
                    $('#saveModal .modal-footer').html(`
                        <a href="postDetail" class="btn btn-danger" id="viewPost">XEM BÀI VIẾT</a>
                        <a href="createPost" class="btn btn-primary">ĐÓNG</a>
                    `)
                    messageModal('saveModal', 'img/img-success.png', "Tạo bài viết thành công!");
                } else {
                    messageModal('saveModal', 'img/img-error.png', message);
                }
            },
            failure: function (errMsg) {
                messageModal('saveModal', 'img/img-error.png', errMsg);
            },
            dataType: "json",
            contentType: "application/json"
        });
    }
})

/*Upload image*/
var loadFile = function (event) {
    var form = $('#form-post');
    var formData = new FormData(form[0]);
    formData.append('ckCsrfToken', 'CKJl3IP2xVAP5q9s6O86yt3C6fCzO4ChvpHIaj53');

    $.ajax({
        type: "POST",
        url: "https://73438.cke-cs.com/easyimage/upload/",
        data: formData,
        async: false,
        headers: {
            authorization: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoIjp7ImNvbGxhYm9yYXRpb24iOnsiKiI6eyJyb2xlIjoid3JpdGVyIn19fSwidXNlciI6eyJlbWFpbCI6InZlYXplbWVAZXhhbXBsZS5jb20iLCJuYW1lIjoiTHVyYSBXYWxrZXIifSwic3ViIjoiZGV2LXVzZXItQnBBb2NjQUU1aFZTVEVUYWJ2RjEiLCJpc0RldlRva2VuIjp0cnVlLCJ0aW1lc3RhbXAiOjE1OTYwMTAyMTc5NjcsInNpZ25hdHVyZSI6IjA5NDk3OWU4ODVkOWY5NzlhOWJmMDk0NDZmZjg2ZjBhYzIzYmZmMzhlNzdhNzRiYjQ4ZDM3OGYyNGU4YTY1YTUiLCJhdWQiOiJCcEFvY2NBRTVoVlNURVRhYnZGMSIsImp0aSI6ImluYkYtUnJySWU3eDkxc212dDdWSXBjZWh3X2xmNWdYIiwiaWF0IjoxNTk2MDEwMjE3fQ.teiHdZp-YsLlsGU0lTu5k3-sVFBFrv_Od8640h4g9Ic',
        },
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function (resp) {
            $('body').removeClass("loading");
            console.log(resp);
            // temp1[0].object["a"].authorization
        },
        success: function (data) {
            $('#imagePreview').prop('src', data.default);
            $('#imagePreview').prop('alt', 'Ảnh bìa bài viết');
            console.log(data.default);
        },
        failure: function (errMsg) {
            dialogErr('#overrideSuccess', errMsg);
        },
        cache: false,
        contentType: false,
        processData: false,
    });
};

/*Dialog message*/
function messageModal(modalName, img, message) {
    $('#' + modalName + ' .modal-body').html(`
        <img class="my-3" src="` + img + `"/>
        <h5>` + message + `</h5>
    `)
    $('#' + modalName).modal('show');
}
