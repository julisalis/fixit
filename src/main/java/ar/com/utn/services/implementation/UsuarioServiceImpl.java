package ar.com.utn.services.implementation;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by julis on 26/5/2017.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public void registrarPrestador(PrestadorForm prestadorForm, Prestador prestador) {
        new Usuario(prestadorForm.getUsername(),prestadorForm.getNombre(),prestadorForm.getApellido(),prestadorForm.getDocumento(),
                prestadorForm.getTipoDoc(),prestadorForm.getPassword(),prestadorForm.getFechaNacimiento(),prestadorForm.getTelefono(),prestadorForm.getCuit(),prestador);
    }

    @Override
    public void registrarTomador(TomadorForm tomadorForm) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(usuario);
    }
}
