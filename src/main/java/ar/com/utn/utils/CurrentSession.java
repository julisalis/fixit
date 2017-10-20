package ar.com.utn.utils;

import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by julian on 13/08/17.
 */
@Component
public class CurrentSession {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (username == null)
            throw new RuntimeException("No hay usuario logueado");
        return usuarioRepository.findByUsernameIgnoreCase(username);
    }

    public Collection<? extends GrantedAuthority> getActualRol() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (username == null)
            throw new RuntimeException("No hay usuario logueado");
        return auth.getAuthorities();
    }


    public void logInUser(UserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}