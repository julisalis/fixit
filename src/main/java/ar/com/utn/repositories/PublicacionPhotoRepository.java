package ar.com.utn.repositories;

import ar.com.utn.models.Publicacion;
import ar.com.utn.models.PublicacionPhoto;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iaruedel on 23/09/17.
 */
public interface PublicacionPhotoRepository extends CrudRepository<PublicacionPhoto, Long> {
    PublicacionPhoto findByPublicacionAndCover(Publicacion publicacion, boolean iscover);
}
