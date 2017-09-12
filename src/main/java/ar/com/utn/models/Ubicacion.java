package ar.com.utn.models;

import org.springframework.security.access.method.P;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by iaruedel on 05/08/17.
 */
@Entity
@Table (name="ubicacion")
public class Ubicacion extends PersistentEntity{
    @ManyToOne
    @JoinColumn(name="id_localidad",nullable = false)
    private Localidad localidad;

    public Ubicacion() {
    }

    public Ubicacion(Localidad localidad){
        this.localidad = localidad;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }
}
