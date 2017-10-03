// variables that the file uploader references
var fileInput;
var primaryImage = null;

$(function () {

   if($('#publicacionId').val() == ""){
        $.validate({
            lang : 'es',
            decimalSeparator: '.',
            onSuccess : function($form) {
                saveFunction($form);
                return false; // Will stop the submission of the form
            }
        });
    }else{
        $.validate({
            lang: 'es',
            decimalSeparator: '.',
        });

    }

    initializeImages();
    initializeFileInput();

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
    

});

function saveFunction(form){
    $.ajax({
        url: form.attr('action'),
        data: form.serialize(),
        method: "POST",
        success: function( data, textStatus, jqXHR) {
            if (data.publicacion.id != null && typeof(data.publicacion.id) != 'undefined') {
                $("#publicacionId").val(data.publicacion.id);
                uploadFiles();
            } else {
                errorMessage();
            }
        },
        error: function ( jqXHR, textStatus, errorThrown){
            errorMessage();
        }
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
        maxFileCount: 4,
        minFileCount: 1,
        maxFileSize: 1000,
        validateInitialCount: true,
        overwriteInitial: false,
        showClose :false,
        allowedFileExtensions: ["jpg", "png","jpeg"],
        minImageWidth: 100,
        minImageHeight: 140,
        uploadExtraData: function() {
            return {
                publicacionId: $("#publicacionId").val()
            };
        }
    });

    fileInput.on('filebatchuploadsuccess', function(event, data, previewId, index) {
        successMessage();
    });
    fileInput.on('filebatchuploaderror', function(event, data, previewId, index) {
        errorMessage();
    });

}
function initializeImages(){
    $(".card-image-container").on("click",".btn-profile",function(){
        $(".card-image-container").find(".card-image.primary").removeClass("primary");
        $(this).closest(".card-image").addClass("primary");
        primaryImage = $(this).data("publicacion-image-id");
    })
}

function uploadFiles(){
    fileInput.fileinput("upload");
}

function successMessage(){
    swal({
            title: "Guardado",
            text: "La publicación se ha guardado",
            type: "success",
            closeOnConfirm: true,
            showLoaderOnConfirm: false
        },
        function(){
            listPublicaciones();
        });
}

function listPublicaciones(){
    window.location.href = "/publicacion/list";
}

function errorMessage(){
    swal({
        title: "Error",
        text: "La publicación no se ha podido guardar",
        type: "error",
        closeOnConfirm: true,
        showLoaderOnConfirm: false
    });
}



