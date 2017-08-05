package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by iaruedel on 05/08/17.
 */
@Entity
@Table(name="ubicacion")
public class Localidad extends PersistentEntity{
    @ManyToOne
    @JoinColumn(name="id_provincia",nullable=false)
    private Provincia provincia;
    private String nombre;

    public Localidad(Provincia idProvincia, String nombre) {
        this.provincia = idProvincia;
        this.nombre = nombre;
    }

    public Provincia getIdProvincia() {
        return provincia;
    }

    public void setIdProvincia(Provincia idProvincia) {
        this.provincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
