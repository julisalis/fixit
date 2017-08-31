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


});
