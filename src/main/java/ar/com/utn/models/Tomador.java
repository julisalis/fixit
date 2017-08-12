package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
public class Tomador extends PersistentEntity {

    @OneToMany(mappedBy = "tomador")
    private List<Publicacion> publicaciones;

    public Tomador() {
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
}
