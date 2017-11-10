/**
 * Created by iaruedel on 09/11/17.
 */
$(function () {

    $.validate({
        lang : 'es',
        decimalSeparator : '.',
    });

    $("#message-form").submit(function (e) {
        e.preventDefault();
        $.LoadingOverlay("show");
        var form = $(this);
        enviarMensaje(form)

    })
});


function enviarMensaje(form){
    swal({
            title: "Enviar mensaje",
            text: "Se enviará el mensaje al destinatario",
            type: "info",
            showCancelButton: true,
            confirmButtonText: "Enviar",
            closeOnConfirm: false,
        },
        function(){
            $.post(form.attr('action'),form.serialize())
                .done(function( data ) {
                    $.LoadingOverlay("hide");
                    if(data.success){
                        swal({
                            title: "Enviado",
                            text: "Su mensaje ha sido enviado con exito.",
                            type: "success",
                        },function (e) {
                            location.reload();
                        });
                    } else{
                        swal("Error", "No ha sido posible enviar el mensaje", "error");
                    }
                })
                .fail(function () {
                    $.LoadingOverlay("hide");
                    swal("Error", "No ha sido posible enviar el mensaje", "error");
                });

        });
}