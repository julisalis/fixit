$(function () {
    $(".category-item").hover(function(){
        $(this).addClass("selected");
        var img = $(this).find(".img-selected");
        var name = img.attr("name");
        img.prop("src",'/images/'+name+"-selected.png");
    }, function () {
        $(this).removeClass("selected");
        var img = $(this).find(".img-selected");
        var name = img.attr("name");
        img.prop("src",'/images/'+name+".png");
    });

    $(".modal-signin button.btn-login").click(function(e) {
        var container = $(this).closest(".modal-signin");
        //Escondo el error
        $("div.error-msg",container).hide();
        var form = $(this).closest( "form" );
        $.ajax({
            type: 'POST',
            url: form.attr('action'),
            data: form.serialize(),
            async: false,
            success: function (data) {
                if(data.success) {
                    if (typeof(data.select_role) != 'undefined' && data.select_role == true) {
                        e.preventDefault();
                        //mostrar selector de role
                        BootstrapDialog.show({
                            title: 'Seleccionar rol',
                            message: 'Por favor, seleccione con que rol quiere iniciar sesión.',
                            closable: false,
                            buttons: [{
                                label: 'Cliente',
                                action: function(dialog) {
                                    $.ajax({
                                        type: 'POST',
                                        url: '/usuario/cambiarRol',
                                        data: {
                                            id: data.id,
                                            rol: "TOMADOR"
                                        },
                                        async: true,
                                        success: function(message){
                                            if(message.success){
                                                window.location.href = "/";
                                            }else{
                                                swal("Error", message.msg, "error")
                                            }
                                        }
                                    });
                                }
                            }, {
                                label: 'Profesional',
                                action: function(dialog) {
                                    $.ajax({
                                        type: 'POST',
                                        url: '/usuario/cambiarRol',
                                        data: {
                                            id: data.id,
                                            rol: "PRESTADOR"
                                        },
                                        async: true,
                                        success: function(message){
                                            if(message.success){
                                                window.location.href = "/";
                                            }else{
                                                swal("Error", message.msg, "error")
                                            }
                                        }
                                    });
                                }
                            }]
                        });
                    } else {
                        if (typeof(data.url) != 'undefined') {
                            window.location = data.url;
                        } else {
                            location.reload();
                        }
                    }
                } else {
                    //Muestro error
                    $("div.error-msg",container).show();
                }
            }
        });
    });

    $(".modal-publicacion").on("click",  function () {
        var publicacionId = $(this).data('id');

        $.get('/publicaciones/detalle', {publicacionId: publicacionId} )
            .done(function( data ) {
                if (typeof(data.publicacion) != 'undefined'){
                    $('#publicacion').modal('toggle');
                    $("#publicacion .modal-title").text( data.publicacion.titulo);
                    $("#publicacion .modal-descripcion").text( data.publicacion.descripcion);
                    $("#publicacion .modal-ubicacion").text( data.publicacion.localidad.nombre+', '+data.publicacion.provincia.nombre);
                    $("#publicacion .modal-precio").text('Hasta '+ data.publicacion.presupMax+' '+data.publicacion.currency);
                    $("#publicacion .modal-urgencia").text( data.publicacion.urgencia);
                    if(data.publicacion.fecha != 'undefined' && data.publicacion.fecha != null){
                        $("#publicacion .modal-fecha").text(data.publicacion.fecha.dayOfMonth+'/'+data.publicacion.fecha.monthValue+'/'+data.publicacion.fecha.year);
                    }
                    var image = data.publicacion.primaryImage;
                    var id = image.id;
                    var foto =  $("#publicacion .modal-body img") ;
                    $(foto).attr('src', '/multimedia/image/'+id);

                    $("#publicacion #ver-mas").attr('href','/publicacion/mas/'+id);
                }


            }).fail(function () {
            return false;
        });
    });

});
