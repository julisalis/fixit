$(function () {
    initializePaymentMethod();
    initializeMP();

    $('#cartForm').validate({
        lang: 'es',
        decimalSeparator: '.'
    });


    $("#payButton").click(function (e) {
        swal({
                title: "Validación",
                text: "Se validará la tarjeta de crédito",
                type: "info",
                showCancelButton: true,
                closeOnConfirm: false,
                showLoaderOnConfirm: true
            },
            function(){
                setTimeout(function(){
                    createTokenMP(callbackSuccessCreditCard,callbackErrorCreditCard);
                }, 1000);

            });
    });

});

function initializePaymentMethod(){
    $('#cartForm').card({
        container: $("#cardContainer"),
        formSelectors: {
            expiryInput: 'input[name="expiry_month"], input[name="expiry_year"]'
        }
    });
}

function callbackSuccessCreditCard(){

    swal({
            title: "Validación correcta",
            text: "Tarjeta de crédito validada, procederemos a efectuar la contratación",
            type: "success",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true
        },
        function(){
            setTimeout(function(){
                contratar();
            }, 1000);

        });
}
function callbackErrorCreditCard(){
    swal({
        title: "Tarjeta inválida",
        text: "Revisa los datos ingresados",
        type: "error",
        closeOnConfirm: true,
        showLoaderOnConfirm: false
    });
}

function contratar() {
    var data = {};
    extractCardForm(data);
    extractPostulacionForm(data);
    $.ajax({
        type: 'POST',
        url: '/contratar',
        data: data,
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
function extractCardForm(data){
    data.creditCardPayed = true;
    data.tokenMP = $("#tokenMP").val();
    data.paymentMethodId = $("#paymentMethodId").val();
}
function extractPostulacionForm(data){
    data.postulacionId = $("#postulacionId").val();
}




