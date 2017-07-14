package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
@Table(name = "mensajes")
public class Mensaje extends PersistentEntity {

    @ManyToOne
    @JoinColumn(name = "tomador", nullable = false)
    private Tomador tomador;

    @ManyToOne
    @JoinColumn(name = "prestador", nullable = false)
    private Prestador prestador;

    private String mensaje;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "publicacion", nullable = false)
    private Publicacion publicacion;

    public Tomador getTomador() {
        return tomador;
    }

    public void setTomador(Tomador tomador) {
        this.tomador = tomador;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
