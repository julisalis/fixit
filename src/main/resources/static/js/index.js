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
                        e.preventDefault();
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

        $.get('/publicacion/detalle', {publicacionId: publicacionId} )
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

                    var listaImg = data.publicacion.publicacionFotoForms;
                    var listaItems = $("#publicacion .item");
                    var listaIndicadores = $(".carousel-indicators li");
                    for(var i=0;i<listaImg.length;i++){
                        var img = listaImg[i].id;
                        var item = listaItems[i];
                        var indicador = listaIndicadores[i];
                        var foto = item.lastElementChild.lastElementChild.lastElementChild;
                        $(foto).attr('src', '/multimedia/image/'+img);
                        $(item).removeClass('oculto');
                        $(indicador).removeClass('oculto');
                    }
                    $(".modal-content .oculto").remove();

                    // $.each(listaImg, function (index, value) {
                    //     var listaItems = $("#publicacion .item");
                    //     var listaIndicadores = $(".carousel-indicators li");
                    //
                    //     var newItem = item.clone().prependTo('#imagenes-carrousel');
                    //     newItem.removeClass('original');
                    //     newItem.addClass('cloned');
                    //     var foto = newItem.find(".modal-imagenes");
                    //     $(foto).attr('src', '/multimedia/image/'+value.id);
                    //     $(newItem).show();
                    // })
                }


            }).fail(function () {
            return false;
        });
    });

    $('#publicacion').on('hidden.bs.modal', function () {
        var content = $('.modal-body #section-Clientes');
        content.html('');
        $('#section-copia #section-Clientes .section').clone().appendTo(content);
    });

});
