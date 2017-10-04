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

    $(".modal-signin button.btn-login").click(function() {
        var container = $(this).closest(".modal-signin");
        //Escondo el error
        $("div.error-msg",container).hide();
        var form = $(this).closest( "form" );
        $.post(form.attr('action') , form.serialize() )
            .done(function( data) {
                if(data.success) {
                    if (typeof(data.select_role) != 'undefined' && data.select_role == true) {
                        //mostrar selector de role
                        swal("Elegiste Profesional");
                        /*swal("¿Con qué perfil desea iniciar sesión?", {
                            buttons: {
                                prestador: "Profesional",
                                tomador: "Cliente"
                            },
                        })
                            .then((value) => {
                            switch (value) {

                            case "tomador":
                                swal("Elegiste Cliente");
                                break;

                            case "prestador":
                                swal("Elegiste Profesional");
                                break;
                            }
                        });*/
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
            }).fail(function () {
                $("div.error-msg",container).show();
            });

    });

    $(".modal-publicacion").on("click",  function () {
        var publicacionId = $(this).data('id');

        $.get('/publicacion/detalle', {publicacionId: publicacionId} )
            .done(function( data ) {
                if (typeof(data.publicacion) != 'undefined'){
                    $('#publicacion').modal('toggle');
                    $("#publicacion .modal-title").text( data.publicacion.titulo);
                    $("#publicacion .modal-descripcion").text( data.publicacion.descripcion);
                    $("#publicacion .modal-ubicacion").text( data.publicacion.localidad.nombre+', '+data.publicacion.provincia.nombre);
                    $("#publicacion .modal-precio").text('Hasta '+ data.publicacion.presupMax+' '+data.publicacion.currency);
                    $("#publicacion .modal-urgencia").text( data.publicacion.urgencia);
                    $("#publicacion .modal-fecha").text(data.publicacion.fecha.dayOfMonth+'/'+data.publicacion.fecha.monthValue+'/'+data.publicacion.fecha.year);
                }


            }).fail(function () {
            return false;
        });
    });


});
