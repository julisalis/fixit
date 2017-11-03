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

    private String mensaje;
    private Date fecha;
    private Boolean enviaTomador;

    @ManyToOne
    @JoinColumn(name = "postulacion", nullable = false)
    private Postulacion postulacion;


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
