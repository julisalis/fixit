package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
@Table(name="tipotrabajo")
public class TipoTrabajo extends PersistentEntity{

    private String nombre;
    private String slug;
    private String imagen;

    public TipoTrabajo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
