package ar.com.utn.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by julis on 17/5/2017.
 */
@MappedSuperclass
public class PersistentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /* Setter methods */
    public void setId(Long id) {
        this.id = id;
    }

    /* Getter methods */
    public Long getId() {
        return id;
    }

    public boolean isNew() {
        return id == null;
    }

}