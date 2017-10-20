/**
 * Created by julian on 20/10/17.
 */
$(function () {
    initializePaymentMethod();
    // initializeMP();
});

function contratar(postulacionId) {
    $.ajax({
        type: 'POST',
        url: '/contratar',
        data: {
            postulacionId: postulacionId
        },
        async: true,
        success: function(message){
            if(message.success){
                swal({
                    title: "Postulación contratada!",
                    text: message.msg,
                    type: "success",
                },function (e) {
                    window.location.href="/publicacion/list";
                });
            }else{
                swal("Error", message.msg, "error")
            }
        }
    });
}

function initializePaymentMethod(){
    //initialize card animation
    $('#cartForm').card({
        // a selector or DOM element for the container
        // where you want the card to appear
        container: $("#cardContainer"),
        formSelectors: {
            expiryInput: 'input[name="expiry_month"], input[name="expiry_year"]'
        }
    });
    $("#cardRow").fadeIn("slow");
    $("#cartForm input").val("");
}