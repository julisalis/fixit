package ar.com.utn.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
@Table(name = "mensajes")
public class Mensaje extends PersistentEntity {

    private String mensaje;
    private LocalDateTime fecha;
    private Boolean enviaTomador;
    @ManyToOne
    @JoinColumn(name = "postulacion", nullable = false)
    private Postulacion postulacion;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    public Mensaje() {
    }

    public Mensaje(String mensaje, Boolean enviaTomador, Postulacion postulacion) {
        this.mensaje = mensaje;
        this.enviaTomador = enviaTomador;
        this.postulacion = postulacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getEnviaTomador() {
        return enviaTomador;
    }

    public void setEnviaTomador(Boolean enviaTomador) {
        this.enviaTomador = enviaTomador;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }
}
