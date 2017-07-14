package ar.com.utn.models;

import javax.persistence.*;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
@Table(name = "calificaciones")
public class Calificacion extends PersistentEntity {

    @ManyToOne
    @JoinColumn(name = "calificador", nullable = false)
    private Usuario calificador;

    @ManyToOne
    @JoinColumn(name = "calificado", nullable = false)
    private Usuario calificado;

    private Double puntaje;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "publicacion", nullable = false)
    private Publicacion publicacion;

    public Usuario getCalificador() {
        return calificador;
    }

    public void setCalificador(Usuario calificador) {
        this.calificador = calificador;
    }

    public Usuario getCalificado() {
        return calificado;
    }

    public void setCalificado(Usuario calificado) {
        this.calificado = calificado;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
