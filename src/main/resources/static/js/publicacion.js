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

    $("a#add-photo-link input").on('change', function () {
        if($('.image_thumbnail').length <= 8){
            readFile(this);
        }else {
            //show error
            $("a#add-photo-link input").prepend('<input class="error"> Ha alcanzado el límite de fotos </input>')
        }


    });

    $("#preview_images .image_thumbnail.view").hover(function () {
        $(this).find($("buttons")).show();
    }, function () {
        $(this).find($("buttons")).hide();
    });
});

var count=-1;
function firstAvailableDate() {
    var date = new Date();
    return date;
}

function readFile(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(".photo-section  #preview_images").show();
            count=count+1;
            var img_div = $('#image_template .image_thumbnail').clone();
            var images_container = $("#preview_images");
            images_container.show();
            images_container.append(img_div);
            var img = $("img", img_div).attr('src', e.target.result);
            var input = $('<input class="added_'+count+'" type="hidden">').attr('value', e.target.result).attr("name", "files["+count+"]");
            img_div.append(input);

        }
        reader.readAsDataURL(input.files[0]);

    } else {
        alert("Sorry - you're browser doesn't support the FileReader API");
    }
}

//Eliminado de foto
$('a.remove').click(function(e) {
    removeImage($(this),e);
});

function removeImage(current, event){
    event.preventDefault();
    var input = $('<input type="hidden">').attr('value', current.data('id-image')).attr("name", "removed");
    current.closest('form').append(input);
    current.closest('.view').hide();
}







