$(function () {
    $(document).scroll(function () {
        var $nav = $(".navbar-fixed-top");
        $nav.toggleClass('scrolled', $(this).scrollTop() > $nav.height());
    });

    var navMain = $(".navbar-collapse");
    navMain.on("click", "a", null, function () {
        navMain.collapse('hide');
    });

    $("#localidad").load('/signup/ajax/localidad', $("#provincia").serialize());
    $('#provincia').on('change', function() {
        $("#localidad").load('/signup/ajax/localidad', $("#provincia").serialize());
    });

    $.validate({
        lang : 'es',
        decimalSeparator : '.',
    });

    //*******************Form de register***********************
    $("#signup form .btn-aceptar").click(function(){
        $.LoadingOverlay("show");
        $(this).prop('disabled',true);
        var form = $(this).closest( "form" );

        $("#signup div.error-msg").hide();
        $("#signup div.error-msg p").remove();
        $('div.has-error p').remove();
        $('div.has-error').removeClass('has-error');

        $.get(form.attr('action') , form.serialize() )
            .done(function( data ) {
                $.LoadingOverlay("hide");
                if(data.success) {
                    if(typeof(data.url) != 'undefined'){
                        window.location=data.url;
                    }else if(typeof(data.msg) != 'undefined'){
                        if(typeof(data.nombre_afip) != 'undefined'){
                            swal({
                                title: "Registro exitoso",
                                text: 'You can use <b>bold text</b>, <a href="//github.com">links</a> and other HTML tags',
                                html: true,
                                type: "success",
                            },function (e) {
                                window.location="/";
                            });
                        }else{
                            swal({
                                title: "Registro exitoso",
                                type: "success",
                                text: data.msg,
                            },function (e) {
                                window.location="/";
                            });
                        }
                    }
                } else {
                    //Manejo de errores en json 
                    var errorsList = data.errors;
                    if(typeof(errorsList) != 'undefined'){
                        for (i = 0; i < errorsList.length; i++) {
                            var div = $('input[name="'+data.errors[i].field+'"]',form).closest('div');
                            div.addClass('has-error');
                            div.append($("<p>").text(data.errors[i].defaultMessage));
                        }
                    }else{
                        BootstrapDialog.show({
                            title: 'Error',
                            message: data.msg,
                            type: BootstrapDialog.TYPE_DANGER,
                            buttons: [{
                                label: 'Cerrar',
                                action: function(dialogItself){
                                    dialogItself.close();
                                }
                            }]
                        });
                    }
                    $("#signup form .btn-aceptar").prop('disabled',false);
                }
            })
            .fail(function( data ) {
                $.LoadingOverlay("hide");
                var result = $(data);

            });

    });

    $("#postular").click(function (e) {
        $.ajax({
            url: "/usuario/validateMercadoPago",
            async: false,
            method: "GET",
            success: function( data) {
                if(!data.success) {
                    swal({
                        title: "Error",
                        text: "Debe iniciar sesión en Mercado Pago",
                        type: "error",
                    },function () {
                        window.location.href = "/usuario/perfil";
                    });
                    e.preventDefault();
                    return false;
                }
            },
            error: function ( jqXHR, textStatus, errorThrown){
                e.preventDefault();
                return false;
            }
        });
    });
});




function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

$(document).on('click','.searchbychar', function(event) {
    event.preventDefault();
    var target = "#" + this.getAttribute('data-target');
    $('html, body').animate({
        scrollTop: $(target).offset().top - 65
    }, 500);
});
