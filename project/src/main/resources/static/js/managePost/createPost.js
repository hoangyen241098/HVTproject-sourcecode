/*Valude default*/
$('#inputDate').val(moment().format('YYYY-MM-DD'));
var editor = CKEDITOR.replace('post-editor-text-content', {
    cloudServices_uploadUrl: 'https://73438.cke-cs.com/easyimage/upload/',
    cloudServices_tokenUrl: 'https://73438.cke-cs.com/token/dev/de62f27633e0ccc284486ba070dbacf5b61e59390a805c23d58fc080b306',
    width: '100%',
    height: 500,
    extraPlugins: 'easyimage',
    removePlugins: 'image',
    removeDialogTabs: 'link:advanced',
    toolbar: [
        {
            name: 'document',
            items: ['Undo', 'Redo']
        },
        {
            name: 'styles',
            items: ['Format']
        },
        {
            name: 'basicstyles',
            items: ['Bold', 'Italic', 'Strike', '-', 'RemoveFormat']
        },
        {
            name: 'paragraph',
            items: ['NumberedList', 'BulletedList']
        },
        {
            name: 'links',
            items: ['Link', 'Unlink']
        },
        {
            name: 'insert',
            items: ['EasyImageUpload']
        }
    ],
});

/*Save button*/
$('#savePost').on('click', function () {
    var form = $('#form-post');
    var formData = new FormData(form[0]);
    formData.append( 'ckCsrfToken', 'CKJl3IP2xVAP5q9s6O86yt3C6fCzO4ChvpHIaj53' );
    console.log(JSON.stringify(formData))
    $.ajax({
        type: "POST",
        url: "https://73438.cke-cs.com/easyimage/upload/",
        data: formData,
        async: false,
        headers: {
            authorization: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoIjp7ImNvbGxhYm9yYXRpb24iOnsiKiI6eyJyb2xlIjoid3JpdGVyIn19fSwidXNlciI6eyJlbWFpbCI6ImthbUBleGFtcGxlLmNvbSIsIm5hbWUiOiJBZGVsYWlkZSBMZW9uYXJkIn0sInN1YiI6ImRldi11c2VyLUJwQW9jY0FFNWhWU1RFVGFidkYxIiwiaXNEZXZUb2tlbiI6dHJ1ZSwidGltZXN0YW1wIjoxNTk1ODgwNDk5ODA4LCJzaWduYXR1cmUiOiIwMWUyN2E5ZDE4NWU4YjA5OTA2NDc2NDI1Y2EzYmRjY2U1MzZjZWY1OGNiN2QwNDcyMzk1NjYwYzc5YmI0NDRmIiwiYXVkIjoiQnBBb2NjQUU1aFZTVEVUYWJ2RjEiLCJqdGkiOiJpNkZFSXpUcTNxNkxjY1VsMTBRVDZ2LXNZOHpEYm1BXyIsImlhdCI6MTU5NTg4MDQ5OX0.8Lx-n16iyCkRW2ay7Afihgl94sylo6HZJSEByiKKcdU',
        },
        success: function (data) {
            console.log(data)
        },
        failure: function (errMsg) {
            dialogErr('#overrideSuccess', errMsg);
        },
        cache: false,
        contentType: false,
        processData: false,
    });
    var data = editor.getData();
    console.log(data);
    messageModal('saveModal', 'img/img-success.png', data)
})

/*Dialog message*/
function messageModal(modalName, img, message) {
    $('#' + modalName + ' .modal-body').html(`
        <img class="my-3" src="` + img + `"/>
        <h5>` + message + `</h5>
    `)
    $('#' + modalName).modal('show');
}

/*Upload image*/
var loadFile = function (event) {
    var output = document.getElementById('imagePreview');
    output.src = URL.createObjectURL(event.target.files[0]);
    output.onload = function () {
        URL.revokeObjectURL(output.src)
    }
};