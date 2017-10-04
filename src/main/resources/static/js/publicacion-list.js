/**
 * Created by iaruedel on 03/10/17.
 */
// variables that the file uploader references
$(function () {
    $(".deletePublicacion").click(function (e) {
        var href = $(this).attr("href");
        confirmDelete(href,e);
    });
});

function confirmDelete(h,e){
    swal({
            title: "¿Está seguro?",
            text: "No podrá recuperar la publicación una vez eliminada.",
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
                        text: "Su publicación ha sido eliminada.",
                        type: "success",
                    },function (e) {
                        location.reload();
                    });
                } else{
                    swal("Error", "No ha sido posible eliminar la publicación", "error");
                }
            })
            .fail(function () {
                swal("Cancelled", "No ha sido posible eliminar la publicación", "error");
            });

        });
    e.preventDefault();
}





