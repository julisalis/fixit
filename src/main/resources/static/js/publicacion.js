// variables that the file uploader references
var fileInput;
var imagesToDelete = new Array();
var primaryImage = null;
var maxFiles = 5 - $("#imageSize").val();
var minFiles = $("#imageSize").val()>0 ? 0 : 1;

$(function () {
    $.validate({
        lang : 'es',
        decimalSeparator: ',',
        onSuccess : function($form) {
            saveFunction();
                //uploadFiles();
            return false; // Will stop the submission of the form
        }
    });

    if(maxFiles == 0){
        $("#file-input-container").hide();
    }

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
    
    initializeImages();
    initializeFileInput();

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
    // $("#submit").on("click",  function () {
    //
    // });
});
function saveFunction() {
    var form = $("#publicacion-form");
    $.get(form.attr('action'), form.serialize())
        .done(function (data) {
            if(data.success) {
                debugger;
                if (data.publicacion.id != null && typeof(data.publicacion.id) != 'undefined') {
                    $("#publicacionId").val(data.publicacion.id)
                    uploadFiles();
                    //mostrar modal de success y redireccionar a list
                } else {
                    //Muestro error
                }
            }else {
                //Muestro error
            }
        }).fail(function () {
        debugger;
    });
}

function firstAvailableDate() {
    var date = new Date();
    return date;
}

function initializeFileInput(){
    fileInput = $("#input-images");
    fileInput.fileinput({
        language: "es",
        uploadUrl: "/publicacion/uploadImage", // server upload action
        uploadAsync: false,
        showUpload: false,
        showRemove: false,
        maxFileCount: maxFiles,
        minFileCount: minFiles,
        maxFileSize: 1000,
        validateInitialCount: true,
        overwriteInitial: false,
        allowedFileExtensions: ["jpg", "png","jpeg"],
        minImageWidth: 100,
        minImageHeight: 140,
        uploadExtraData: function() {
            return {
                publicacionId: $("#publicacionId").val()
            };
        }
    });

}
function initializeImages(){
    $(".card-image-container").on("click",".btn-profile",function(){
        $(".card-image-container").find(".card-image.primary").removeClass("primary");
        $(this).closest(".card-image").addClass("primary");
        primaryImage = $(this).data("publicacion-image-id");
    })

    $(".card-image-container").on("click",".btn-trash",function(){
        imagesToDelete.push($(this).data("publicacion-image-id"));
        $(this).closest(".card-image").fadeOut();
        maxFiles = maxFiles + 1;
        if(maxFiles > 0){
            $("#file-input-container").show();
        }
        updateMaxQuantityFile(maxFiles)
    })
}

function updateMaxQuantityFile(maxQuantity){
    fileInput.fileinput('refresh', {maxFileCount:maxQuantity})
}

function uploadFiles(){
    fileInput.fileinput("upload");
}


