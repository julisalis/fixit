// variables that the file uploader references
var fileInput;
var imagesToDelete = new Array();
var primaryImage = null;
var maxFiles = 5 - $("#imageSize").val();
var minFiles = $("#imageSize").val()>0 ? 0 : 1;

$(function () {
    $.validate({
        lang : 'es',
        onSuccess : function($form) {
            debugger;
            uploadFiles();
        }
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
    initializeImages();
    initializeFileInput();

    $("#modal-publicacion").on("click",  function () {
        var publicacionId = $(this).data('id');

        $.get('/publicacion/detalle', {publicacionId: publicacionId} )
            .done(function( data ) {
                if (typeof(data.publicacion) != 'undefined'){
                    $('#publicacion').modal('toggle');
                    $("#publicacion .modal-title").text( data.publicacion.titulo);
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

function initializeFileInput(){
    fileInput = $("#input-images");
    fileInput.fileinput({
        language: "es",
        uploadUrl: "publicacion/uploadImage", // server upload action
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
                publicicacionId: $("#publicicacionId").val()
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






