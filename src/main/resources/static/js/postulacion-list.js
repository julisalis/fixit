
$(function () {
    $(".deletePostulacion").click(function (e) {
        var href = $(this).attr("href");
        confirmDelete(href,e);
    });
});

function confirmDelete(h,e){
    swal({
            title: "¿Está seguro?",
            text: "No podrá recuperar la postulación una vez eliminada.",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Sí, eliminar!",
            closeOnConfirm: false,
        },
        function(){
            $.post(h)
                .done(function( data ) {
                    if(data.success){
                        swal({
                            title: "Eliminada",
                            text: "Su postulación ha sido eliminada.",
                            type: "success",
                        },function (e) {
                            location.reload();
                        });
                    } else{
                        swal("Error", "No ha sido posible eliminar la postulación", "error");
                    }
                })
                .fail(function () {
                    swal("Cancelled", "No ha sido posible eliminar la postulación", "error");
                });

        });
    e.preventDefault();
}





