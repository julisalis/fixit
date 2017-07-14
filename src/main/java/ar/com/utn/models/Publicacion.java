package ar.com.utn.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by julis on 13/7/2017.
 */
@Entity
@Table(name="publicaciones")
public class Publicacion extends PersistentEntity{

    private String titulo;
    private String descripcion;
    private Double presupMax;

    @Enumerated(EnumType.STRING)
    private TipoTrabajo tipo;

    @OneToMany(mappedBy = "publicacion")
    private Set<Postulacion> postulaciones;

    @OneToMany(mappedBy = "publicacion")
    private List<Mensaje> mensajes;

    @ManyToOne
    @JoinColumn(name = "tomador", nullable = false)
    private Tomador tomador;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPresupMax() {
        return presupMax;
    }

    public void setPresupMax(Double presupMax) {
        this.presupMax = presupMax;
    }

    public Set<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(Set<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public TipoTrabajo getTipo() {
        return tipo;
    }

    public void setTipo(TipoTrabajo tipo) {
        this.tipo = tipo;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Tomador getTomador() {
        return tomador;
    }

    public void setTomador(Tomador tomador) {
        this.tomador = tomador;
    }
}
