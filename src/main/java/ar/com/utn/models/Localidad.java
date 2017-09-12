package ar.com.utn.models;

import javax.persistence.*;

/**
 * Created by iaruedel on 05/08/17.
 */
@Entity
@Table(name="localidad")
public class Localidad extends PersistentEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_provincia",nullable=false)
    private Provincia provincia;
    private String nombre;

    public Localidad(){

    }
    public Localidad(Provincia idProvincia, String nombre) {
        this.provincia = idProvincia;
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
