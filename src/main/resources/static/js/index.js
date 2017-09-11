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

    $(".modal-signin button.btn-login").click(function() {
        var container = $(this).closest(".modal-signin");
        //Escondo el error
        $("div.error-msg",container).hide();
        var form = $(this).closest( "form" );
        $.post(form.attr('action') , form.serialize() )
            .done(function( data) {
                if(data.success) {
                    if (typeof(data.select_role) != 'undefined' && data.select_role == true) {
                        //mostrar selector de role
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
            }).fail(function () {
                $("div.error-msg",container).show();
            });

    });


});
