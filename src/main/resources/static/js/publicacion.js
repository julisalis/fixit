$(function () {
    $.validate({
        lang : 'es',
        decimalSeparator : ',',
    });

    $(".category-item").click(function(){
        $(this).parent(".category-icon").parent(".category-item").find(".input-radio").attr('checked',true);
    });

    $('.datepicker').datepicker({
        format: 'mm/dd/yyyy',
        startDate: '-3d'
    });


});

/**
 * validates selects that are hidden because the chosen
 */
function validateSelects(){
    var valid = true
    $.each($("form .chosen"),function(index,value){
        if($(this).val() == null){
            $(this).parent(".form-group").find(".form-error").remove();
            $(this).parent(".form-group").append("<span class='help-block form-error'>Este campo es obligatorio</span>");
            valid = false;
        }
    });
    return valid;
}
