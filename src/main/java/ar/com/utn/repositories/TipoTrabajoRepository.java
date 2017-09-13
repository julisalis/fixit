package ar.com.utn.repositories;

import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iaruedel on 13/08/17.
 */
@Repository
public interface TipoTrabajoRepository extends CrudRepository<TipoTrabajo,Long> {
    List<TipoTrabajo> findAll();
    TipoTrabajo findBySlug(String slug);
}
