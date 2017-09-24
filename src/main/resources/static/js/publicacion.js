$(function () {
    $.validate({
        lang : 'es',
        decimalSeparator : ',',
    });

    $(".category-item").click(function(){

            $(".category-item").each(function () {
                $(this).find(".category-checkbox").prop('checked',false);
                $(this).removeClass("checked");
                var img = $(this).find(".img-selected");
                var name = img.attr("name");
                img.prop("src",'/images/'+name+".png");
            });

            $(this).find(".category-checkbox").prop('checked',true);
            $(this).addClass("checked");
            var img = $(this).find(".img-selected");
            var name = img.attr("name");
            img.prop("src",'/images/'+name+"-selected.png");
    });

    $('.category-item').hover(function(){
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });

    $("#urgencia").change(function () {
        if($(this).val()=="FECHA"){
            $("#fecha").attr("disabled",false);
        } else {
            $("#fecha").attr("disabled",true);
        }
    });

    if( $("#urgencia").val()=="FECHA"){
        $("#fecha").attr("disabled",false);
    }

    $('.datepicker').datepicker({
        autoclose: true,
        dateFormat: "mm-dd-yy",
        maxDate: '+1Y',
        changeMonth: true,
        changeYear: true,
        showButtonPanel:true,
        minDate:firstAvailableDate(),
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

function firstAvailableDate() {
    var date = new Date();
    return date;
}

