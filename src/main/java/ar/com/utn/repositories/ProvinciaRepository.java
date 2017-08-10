package ar.com.utn.repositories;

import ar.com.utn.models.Provincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by iaruedel on 05/08/17.
 */
public interface ProvinciaRepository  extends CrudRepository<Provincia, Long> {

    @Query("SELECT p FROM Provincia p")
    public List<Provincia> findAllProvincias();
}

