$(function () {
    $.validate({
        lang : 'es',
        decimalSeparator : ',',
        // onSuccess : function($form) {
        //     if(!validateSelects()){
        //         return false; // Will stop the submission of the form
        //     }
        //
        // }
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
