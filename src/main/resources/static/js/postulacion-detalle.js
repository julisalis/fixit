/**
 * Created by iaruedel on 09/11/17.
 */
$(function () {

    $.validate({
        lang : 'es',
        decimalSeparator : '.',
    });

    $("#message-form").submit(function (e) {
        var form = $(this);
        enviarMensaje(form,e)

    })
});


function enviarMensaje(form,e){
    swal({
            title: "Enviar mensaje",
            text: "Se enviará el mensaje al destinatario",
            type: "info",
            showCancelButton: true,
            confirmButtonText: "Enviar",
            closeOnConfirm: false,
            showLoaderOnConfirm: true
        },
        function(){
            $.post(form.attr('action'),form.serialize())
                .done(function( data ) {
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
                    swal("Error", "No ha sido posible enviar el mensaje", "error");
                });

        });
    e.preventDefault();
}