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
        readFile(this);

    });

});

var count=-1;
function firstAvailableDate() {
    var date = new Date();
    return date;
}





function readFile(input) {
    debugger;
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            count=count+1;
            var img_div = $('#image_template .image_thumbnail').clone();
            var images_container = $("#preview_images");
            images_container.show();
            images_container.append(img_div);
            var input = $('<input class="added_'+count+'" type="hidden">').attr('value', e.target.result).attr("name", "files["+count+"].cover");
            img_div.append(input);
        }



        reader.readAsDataURL(input.files[0]);

    } else {
        alert("Sorry - you're browser doesn't support the FileReader API");
    }
}

//Eliminado de foto
// $('a.remove').click(function(e) {
//     removeImage($(this),e);
// });

// function removeImage(current, event){
//     event.preventDefault();
//     var identifier_group = current.data('identifier-group');
//     if (typeof identifier_group !== typeof undefined && identifier_group !== false) {
//         //Guardo en una lista para eliminacion
//         var input = $('<input type="hidden">').attr('value', identifier_group).attr("name", "removed");
//         current.closest('form').append(input);
//     }else{
//         current.closest('.view').remove();
//     }
//     current.closest('.view').hide();
// }







