package ar.com.utn.repositories;

import ar.com.utn.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by julis on 17/5/2017.
 */
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    /**
     * This method will find an User instance in the database by its email.
     * Note that this method is not implemented and its working code will be
     * automagically generated from its signature by Spring Data JPA.
     */
    public Usuario findByEmail(String email);

    public Usuario findByUsernameIgnoreCase(String username);
}
