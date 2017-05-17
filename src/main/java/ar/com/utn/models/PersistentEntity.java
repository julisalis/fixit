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
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Integer id;

    /* Setter methods */
    public void    setId(Integer id) { this.id = id; }

    /* Getter methods */
    public Integer getId()           { return id; }

    public boolean isNew()           { return id == null; }

}