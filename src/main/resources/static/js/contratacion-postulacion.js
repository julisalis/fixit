$(function () {
    initializePaymentMethod();
    initializeMP();

    $('#cartForm').validate({
        lang: 'es',
        decimalSeparator: '.',
        onSuccess : function() {
            validateCardAndSubmit();
        }
    });
});

function initializePaymentMethod(){
    $('#cartForm').card({
        container: $("#cardContainer"),
        formSelectors: {
            expiryInput: 'input[name="expiry_month"], input[name="expiry_year"]'
        }
    });
    $("#cartForm input").val("");
}

function validateCardAndSubmit(){
    var data = {};
    extractCardForm(data);
    extractPostulacionForm(data);
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
}
function extractCardForm(data){
    data.creditCardPayed = true;
    data.tokenMP = $("#tokenMP").val();
    data.paymentMethodId = $("#paymentMethodId").val();
}
function extractPostulacionForm(data){
    data.postulacionId = $("#postulacionId").val();
}

function callbackSuccessCreditCard(){

    swal({
            title: "Validación correcta",
            text: "Tarjeta de crédito validada",
            type: "success",
            closeOnConfirm: true,
            showLoaderOnConfirm: false
        },
        function(){
            swal({
                    title: "Pago",
                    text: "Se procederá a procesar el pago",
                    type: "info",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                },
                function(){
                    setTimeout(function(){
                        contratar(data);
                    }, 1000);

                });
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

function contratar(data) {
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




