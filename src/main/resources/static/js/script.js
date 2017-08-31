$(function () {
    $(document).scroll(function () {
        var $nav = $(".navbar-fixed-top");
        $nav.toggleClass('scrolled', $(this).scrollTop() > $nav.height());
    });

    $("#localidad").load('/signup/ajax/localidad', $("#provincia").serialize());
    $('#provincia').on('change', function() {
        $("#localidad").load('/signup/ajax/localidad', $("#provincia").serialize());
    });

    $.validate({
        lang : 'es',
        decimalSeparator : ',',
    });

    //*******************Form de register***********************
    $("#signup form .btn-aceptar").click(function(){
        $(this).prop('disabled',true);
        var form = $(this).closest( "form" );

        $("#signup div.error-msg").hide();
        $("#signup div.error-msg p").remove();
        $('div.has-error p').remove();
        $('div.has-error').removeClass('has-error');

        $.get(form.attr('action') , form.serialize() )
            .done(function( data ) {
                if(data.success) {
                    if(typeof(data.url) != 'undefined'){
                        window.location=data.url;
                    }else if(typeof(data.msg) != 'undefined'){
                        var msg_modal = $('.modal .modal-dialog .modal-content');
                        $('h3',msg_modal).append(data.msg);
                        $('#modalSuccess .modal-dialog').append(msg_modal);
                        $('#modalSuccess').modal('toggle');
                    }
                } else {
                    //Manejo de errores en json
                    var errorsList = data.errors;
                    if(typeof(errorsList) != 'undefined'){
                        for (i = 0; i < errorsList.length; i++) {
                            var div = $('input[name="'+data.errors[i].field+'"]',form).closest('div');
                            div.addClass('has-error');
                            div.append($("<p>").text(data.errors[i].defaultMessage));
                        }
                    }else{
                        $("#signup div.error-msg ul").append("<li>Error en registro de usuario</li>");
                        $("#signup div.error-msg").show();
                    }
                    $("#signup form .btn-aceptar").prop('disabled',false);
                }
            })
            .fail(function( data ) {
                var result = $(data);

            });

    });
});

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

$(document).on('click','.searchbychar', function(event) {
    event.preventDefault();
    var target = "#" + this.getAttribute('data-target');
    $('html, body').animate({
        scrollTop: $(target).offset().top
    }, 500);
});
