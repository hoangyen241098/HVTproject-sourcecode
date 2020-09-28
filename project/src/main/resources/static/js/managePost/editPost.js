/*Value default*/
var newsletterId = sessionStorage.getItem('newsletterIdEdit');
var oldHeader, oldHeaderImage, oldContent, oldGim, createDate, status;
var request = {
    newsletterId: newsletterId
}
var editor = CKEDITOR.replace('post-editor-text-content', {
    height: 500,
    width: '100%',
});

/*View detail post*/
$.ajax({
    url: "/api/newsletter/viewletter",
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
        if (messageCode == 0) {
            if (data.newsletter.length != 0) {
                oldHeader = data.newsletter.header;
                oldHeaderImage = data.newsletter.headerImage;
                oldContent = data.newsletter.content;
                oldGim = data.newsletter.gim;
                createDate = data.newsletter.createDate;
                status = data.newsletter.status;
                $('#titleName').val(oldHeader);
                $('#imagePreview').prop('src', oldHeaderImage);
                $('#post-editor-text-content').text(oldContent);
                if (oldGim == 1) {
                    $('input[value=pin]').prop('checked', true);
                }
            } else {
                $('#titleName').prop('disabled', true);
                $('#imageCover').prop('disabled', true);
                $('#savePost').prop('disabled', true);
                $('#post-editor-text-content').text(`<h1>Bài viết không tồn tại.</h1>`);
            }
        } else {
            $('#titleName').prop('disabled', true);
            $('#imageCover').prop('disabled', true);
            $('#savePost').prop('disabled', true);
            $('#post-editor-text-content').text(`<h1>` + message + `</h1>`);
        }
    },
    failure: function (errMsg) {
        $('#titleName').prop('disabled', true);
        $('#imageCover').prop('disabled', true);
        $('#savePost').prop('disabled', true);
        $('#post-editor-text-content').text(`<h1>` + errMsg + `</h1>`);
    },
    dataType: "json",
    contentType: "application/json"
});

/*Edit post*/
$('#savePost').on('click', function () {
    var newHeader = $('#titleName').val().trim();
    var newHeaderImage = $('#imagePreview').attr('src');
    var newContent = editor.getData();
    var newGim;
    if ($('input[type="checkbox"]').prop("checked") == true) {
        newGim = 1;
    } else {
        newGim = 0;
    }
    if (oldHeader == newHeader && oldHeaderImage == newHeaderImage &&
        oldContent == newContent && oldGim == newGim) {
        $('.editPost-err').text('Hãy thay đổi thông tin.');
        return false;
    } else if (newHeader == "") {
        $('.editPost-err').text('Hãy nhập tiêu đề của bài viết.');
        return false;
    } else if (newHeaderImage.trim() == '') {
        $('.editPost-err').text('Hãy nhập ảnh bìa của bài viết.');
        return false;
    } else if (newContent == "") {
        $('.editPost-err').text('Hãy nhập nội dung của bài viết.');
        return false;
    } else {
        $('.editPost-err').text('');
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
            roleId: roleID
        }
        if (newGim != oldGim) {
            if (newGim == 1) {
                $('#messageModal .modal-footer').html(`
                    <input type="button" class="btn btn-danger" id="newGim" value="XÁC NHẬN">
                    <input type="button" class="btn btn-primary" id="closeBtn" data-dismiss="modal" value="ĐÓNG">
                `);
                messageModal('messageModal', 'img/img-question.png', 'Bạn có muốn <b>GHIM</b> bài viết này không?<h6>Bài viết được ghim trước đó sẽ bị bỏ ghim!</h6>');
                $('#newGim').on('click', function () {
                    request.newsletter.gim = newGim;
                    editPost(request);
                })
            } else {
                $('#messageModal .modal-footer').html(`
                <input type="button" class="btn btn-danger" id="newGim" value="XÁC NHẬN">
                <input type="button" class="btn btn-primary" id="closeBtn" data-dismiss="modal" value="ĐÓNG">
                `);
                messageModal('messageModal', 'img/img-question.png', 'Bạn có muốn <b>BỎ GHIM</b> bài viết này không?');
                $('#newGim').on('click', function () {
                    request.newsletter.gim = newGim;
                    editPost(request);
                })
            }
        } else {
            editPost(request);
        }
    }
})

function editPost(request) {
    $.ajax({
        url: "/api/newsletter/editnewsletter",
        type: "POST",
        data: JSON.stringify(request),
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
                $('#messageModal .modal-footer').html(`
                    <a href="postDetail?id=` + newsletterId + `" class="btn btn-danger" id="viewPost">XEM BÀI VIẾT</a>
                    <a href="editPost" class="btn btn-primary">ĐÓNG</a>
                `)
                messageModal('messageModal', 'img/img-success.png', 'Sửa bài viết thành công!');
            } else {
                $('#messageModal .modal-footer').html(`<a href="editPost" class="btn btn-primary">ĐÓNG</a>`);
                messageModal('messageModal', 'img/img-error.png', message);
            }
        },
        failure: function (errMsg) {
            $('#messageModal .modal-footer').html(`<a href="editPost" class="btn btn-primary">ĐÓNG</a>`);
            messageModal('messageModal', 'img/img-error.png', errMsg);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

/*Upload image*/
var loadFile = function (event) {
    var apiUrl = 'https://api.imgur.com/3/image';
    var apiKey = 'dcc612a7faddf22';

    var file = event.target.files[0];
    var formData = new FormData();
    formData.append('image', file);

    $.ajax({
        async: false,
        crossDomain: true,
        processData: false,
        contentType: false,
        type: 'POST',
        url: apiUrl,
        data: formData,
        headers: {
            Authorization: 'Client-ID ' + apiKey,
            Accept: 'application/json',
        },
        mimeType: 'multipart/form-data',
        beforeSend: function () {
            $('body').addClass("loading");
        },
        complete: function () {
            $('body').removeClass("loading");
        },
        success: function (data) {
            var src = data.split('"link":"')[1].split('"')[0];
            var output = $('#imagePreview');
            output.attr('src', src);
            output.prop('alt', 'Ảnh bìa bài viết');
            output.onload = function () {
                URL.revokeObjectURL(output.src);
            }
        },
        failure: function (errMsg) {
            messageModal('overrideSuccess', 'img/img-error.png', errMsg);
        },

    });
};

if(roleID != 1) {
    $('.form-check').hide();
}
