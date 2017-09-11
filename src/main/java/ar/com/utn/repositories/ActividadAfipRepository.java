package ar.com.utn.repositories;

import ar.com.utn.models.ActividadAfip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface ActividadAfipRepository extends CrudRepository<ActividadAfip, Long> {
    List<ActividadAfip> findAll();
}
