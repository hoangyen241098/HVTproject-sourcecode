var inforSearch = {
    status: "",
    createDate: "",
    userName: "",
    pageNumber: 0
}
search();

$('#search').on('click', function () {
    $('.panel-default').html('');
    search();
});

/*Load Homepage*/
function search() {
    inforSearch.status = $('#status option:selected').val();
    inforSearch.createDate = $('#inputDate').val();
    inforSearch.userName = $('#createBy').val();
    console.log(JSON.stringify(inforSearch));
    $.ajax({
        url: "/api/newsletter/searchconfirmnews",
        type: "POST",
        data: JSON.stringify(inforSearch),
        beforeSend: function () {
            $('body').addClass("loading")
        },
        complete: function () {
            $('body').removeClass("loading")
        },
        success: function (data) {
            var messageCode = data.message.messageCode;
            var message = data.message.message;
            $('.table-paging').html('');
            $('.panel-default').html('');
            if (messageCode == 0) {
                if (data.listLetter != null) {
                    var totalPages = data.listLetter.totalPages;
                    paging(inforSearch, totalPages);
                    $.each(data.listLetter.content, function (i, item) {
                        var status = item.status;
                        if (status == 1) {
                            status = "Đã xóa";
                        } else if (status == 2) {
                            status = "Chưa duyệt";
                        } else if (status == 0) {
                            status = "Đã duyệt";
                        }
                        $('.panel-default').append(`
                        <div class="panel-post mb-3">
                            <div class="panel-post-content">
                                <div class="post-img">
                                    <img src="` + item.headerImage + `">
                                </div>
                                <div class="post-description">
                                    <div class="post-title">` + item.header + `</div>
                                    <div class="post-date">` + item.createDate + `</div>
                                    <div class="post-shortDes">` + limitedText(item.content) + `</div>
                                    <div class="post-status font-500">` + status + `</div>
                                </div>
                            </div>
                            <div class="panel-btn-view">
                                <a href="postDetail" class="btn btn-customer" id="` + item.newsletterId + `">XEM BÀI VIẾT</a>
                            </div>
                        </div>
                        `);
                    });
                    pagingClick();
                    getNewsletterId();
                } else {
                    $('.panel-default').html('<h3 class="text-center">Danh sách bài viết trống.</h3>');
                }
            } else {
                $('.panel-default').html(`<h3 class="text-center">` + message + `</h3>`);
            }
        },
        failure: function (errMsg) {
            $('.panel-default').html(`<h3 class="text-center">` + errMsg + `</h3>`);
        },
        dataType: "json",
        contentType: "application/json"
    });
}


/*Get newsletterId */
function getNewsletterId() {
    var newsletterId = $('.panel-btn-view a');
    $(newsletterId).on('click', function (e) {
        newsletterId = $(this).prop('id');
        sessionStorage.setItem("newsletterId", newsletterId);
    });
}
