package ar.com.utn.dto;

import ar.com.utn.models.EstadoPublicacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.Tomador;
import ar.com.utn.models.Usuario;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by julian on 30/07/17.
 */
public class TomadorDTO {

    private List<Publicacion> publicaciones;
    private Integer cantPublicaciones;
    private Integer cantContrataciones;

    public TomadorDTO(Tomador tomador) {
        this.publicaciones = tomador.getPublicaciones();
        this.cantContrataciones = tomador.getPublicaciones().stream().filter(publicacion -> publicacion.getEstadoPublicacion() == EstadoPublicacion.CONTRATADA).collect(Collectors.toList()).size();
        this.cantPublicaciones = tomador.getPublicaciones().size();
    }

    public TomadorDTO(Usuario usuario) {
        this.publicaciones = usuario.getTomador().getPublicaciones();
        this.cantContrataciones = usuario.getTomador().getPublicaciones().stream().filter(publicacion -> publicacion.getEstadoPublicacion() == EstadoPublicacion.CONTRATADA).collect(Collectors.toList()).size();
        this.cantPublicaciones = usuario.getTomador().getPublicaciones().size();
    }

    public TomadorDTO() {
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public Integer getCantContrataciones() {
        return cantContrataciones;
    }

    public void setCantContrataciones(Integer cantContrataciones) {
        this.cantContrataciones = cantContrataciones;
    }

    public Integer getCantPublicaciones() {
        return cantPublicaciones;
    }

    public void setCantPublicaciones(Integer cantPublicaciones) {
        this.cantPublicaciones = cantPublicaciones;
    }
}
