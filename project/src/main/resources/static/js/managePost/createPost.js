/*Valude default*/
$('#inputDate').val(moment().format('YYYY-MM-DD'));
var editor = CKEDITOR.replace('post-editor-text-content', {
    // cloudServices_uploadUrl: 'https://73438.cke-cs.com/easyimage/upload/',
    // cloudServices_tokenUrl: 'https://73438.cke-cs.com/token/dev/de62f27633e0ccc284486ba070dbacf5b61e59390a805c23d58fc080b306',
    // filebrowserBrowseUrl: 'https://driveuploader.com/upload/6OknrPxOZf/',
    // filebrowserUploadUrl: 'https://driveuploader.com/upload/6OknrPxOZf/',
    // filebrowserImageBrowseUrl: 'https://driveuploader.com/upload/6OknrPxOZf/',
    // cloudServices_uploadUrl: 'https://driveuploader.com/upload/6OknrPxOZf/',
    width: '100%',
    height: 500,
    removeDialogTabs: 'image:advanced;link:advanced'
});

/*Config Editor*/
CKEDITOR.editorConfig = function (config) {
    config.toolbarGroups = [
        {name: 'document', groups: ['mode', 'document', 'doctools']},
        {name: 'clipboard', groups: ['clipboard', 'undo']},
        {name: 'editing', groups: ['find', 'spellchecker', 'selection', 'editing']},
        {name: 'forms', groups: ['forms']},
        {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
        {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
        {name: 'links', groups: ['links']},
        {name: 'styles', groups: ['styles']},
        {name: 'insert', groups: ['insert']},
        {name: 'colors', groups: ['colors']},
        {name: 'tools', groups: ['tools']},
        {name: 'others', groups: ['others']},
        {name: 'about', groups: ['about']}
    ];

    config.removeButtons = 'About,Iframe,PageBreak,Smiley,Flash,Anchor,Language,BidiRtl,BidiLtr,CreateDiv,HiddenField,ImageButton,SelectAll,Replace,Find,PasteFromWord,PasteText,Form,Print,Save,Source,Scayt,TextField,Textarea,Select,Button,NewPage';
};

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