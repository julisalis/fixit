$(function () {
    $.validate({
        lang : 'es',
        decimalSeparator : ',',
    });

    $(".category-item").click(function(){
        if($(this).hasClass("checked")){
            $(this).find(".category-checkbox").prop('checked',false);
            $(this).removeClass("checked");
            var img = $(this).find(".img-selected");
            var name = img.attr("name");
            img.prop("src",'/images/'+name+".png");
        }
        else{
            $(".category-item").find(".category-checkbox").prop('checked',false);
            $(this).find(".category-checkbox").prop('checked',true);
            $(this).addClass("checked");
            var img = $(this).find(".img-selected");
            var name = img.attr("name");
            img.prop("src",'/images/'+name+"-selected.png");
        }


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

});

function firstAvailableDate() {
    var date = new Date();
    return date;
}

