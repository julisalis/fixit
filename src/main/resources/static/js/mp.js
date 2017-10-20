//TODO: Establecer temporizador una vez creado el token de MP

function initializeMP(){
	Mercadopago.setPublishableKey(MPPublicKey);
	Mercadopago.clearSession();
	initializeFields();
	attachEvents();
}

var localCallbackSuccess = null;
var localCallbackError = null;

function createTokenMP(callbackSuccess,callbackError){
	localCallbackSuccess = callbackSuccess;
	localCallbackError = callbackError;
	Mercadopago.createToken($("#cartForm"), mpResponseHandler);
}


function mpResponseHandler(status, response) {
    if (status != 200 && status != 201) {
    	localCallbackError();
    }else{
    	$("#tokenMP").val(response.id);
    	localCallbackSuccess();
    }
};

function initializeFields(){
	//DNI,etc
	Mercadopago.getIdentificationTypes(function(response,data){
		if(response = 200){
			$.each(data, function( index, item ) {
				$('#docType').append($('<option>', { 
			        value: item.id,
			        text : item.name 
			    }));
			});
		}
	});
	
}

function attachEvents(){
	$("#cardNumber").on("keyup",function(){
		guessingPaymentMethod();
	});
}

function getBin() {
    var ccNumber = $("#cardNumber").val();
    return ccNumber.replace(/[ .-]/g, '').slice(0, 6);
};

function guessingPaymentMethod(event) {
    var bin = getBin();

    if (bin.length >= 6) {
    	Mercadopago.getPaymentMethod({
            "bin": bin
        }, setPaymentMethodInfo);
    }

};

function setPaymentMethodInfo(status, response) {
    if (status == 200) {
        $("#paymentMethodId").val(response[0].id);            
    }
};