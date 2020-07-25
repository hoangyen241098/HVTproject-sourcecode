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

/*Config Editor*/

/*Save button*/
$('#savePost').on('click', function () {
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