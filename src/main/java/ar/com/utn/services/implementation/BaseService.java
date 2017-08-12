package ar.com.utn.services.implementation;

import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
public abstract class BaseService {

    @Autowired
    protected UsuarioRepository userRepository;

    /**
     * get the user from the security context
     * @return
     */
    Usuario getUserLogged(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = userRepository.findByUsernameIgnoreCase(auth.getName());
        return user;
    }

}