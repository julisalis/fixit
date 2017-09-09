package ar.com.utn.services.implementation;

import ar.com.utn.models.Rol;
import ar.com.utn.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by julis on 26/5/2017.
 */
public class UserDetailsImpl implements UserDetails {

    private Usuario usuario;
    private List<Rol> roles;

    public UserDetailsImpl(Usuario usuario,List<Rol> rol) {
        this.usuario = usuario;
        this.roles=rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        roles.forEach(rol -> authorities.add( new SimpleGrantedAuthority(rol.name())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
