package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by iaruedel on 05/08/17.
 */
@Entity
@Table(name="provincia")
public class Provincia extends PersistentEntity {
    private String nombre;

    public Provincia(){

    }
    public Provincia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
