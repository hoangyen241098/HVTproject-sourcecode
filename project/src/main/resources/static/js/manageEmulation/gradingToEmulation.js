$('.btn-increase').on('click', function () {
    var $qty = $(this).closest('div').find('.quantity-input');
    var currentVal = parseInt($qty.val());
    if (!isNaN(currentVal)) {
        $qty.val(currentVal + 1);
    }
    var $substract = $(this).closest('td').parent().find('.substract');
    var $total = $(this).closest('td').parent().find('.total');
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
});


$('.btn-decrease').on('click', function () {
    var $qty = $(this).closest('div').find('.quantity-input');
    var currentVal = parseInt($qty.val());
    if (!isNaN(currentVal) && currentVal > 0) {
        $qty.val(currentVal - 1);
    }
    var $substract = $(this).closest('td').parent().find('.substract');
    var $total = $(this).closest('td').parent().find('.total');
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
});
getTotalGrade();

function getTotalGrade() {
    var $this = $('tbody tr');
    var $substract = $this.find('.substract');
    var $total = $this.find('.total');
    var $qty = $this.find('.quantity-input')
    var total = parseFloat($substract.text()) * parseInt($qty.val());
    $total.text(total.toFixed(2));
}

