package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by julis on 25/5/2017.
 */

@Entity
@Table(name = "roles")
public class Rol extends PersistentEntity{

    private String rol;

    /*@ManyToMany( mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<Usuario>();*/

    private Rol(){}

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
