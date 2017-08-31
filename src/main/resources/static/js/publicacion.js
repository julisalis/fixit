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


    $('.datepicker').datepicker({
        format: 'mm/dd/yyyy',
        startDate: '-3d'
    });



});

