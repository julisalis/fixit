package ar.com.utn.services.implementation;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.LocalidadRepository;
import ar.com.utn.repositories.ProvinciaRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by julis on 26/5/2017.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    @Transactional
    public Usuario registrarPrestador(PrestadorForm prestadorForm, Prestador prestador,Telefono telefono) {
        Provincia provincia=provinciaRepository.findOne(prestadorForm.getProvincia());
        Localidad localidad=localidadRepository.findOne(prestadorForm.getLocalidad());
        Ubicacion ubicacion=new Ubicacion(provincia,localidad);
        Usuario usuario = new Usuario(prestadorForm.getUsername(),prestadorForm.getNombre(),prestadorForm.getApellido(),prestadorForm.getDocumento(),
                prestadorForm.getTipoDoc(),prestadorForm.getPassword(),prestadorForm.getFechaNacimiento(),telefono,prestadorForm.getCuit(),prestador,ubicacion);
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario registrarTomador(TomadorForm tomadorForm,Telefono telefono) {
        Provincia provincia=provinciaRepository.findOne(tomadorForm.getProvincia());
        Localidad localidad=localidadRepository.findOne(tomadorForm.getLocalidad());
        Ubicacion ubicacion=new Ubicacion(provincia,localidad);
        Usuario usuario = new Usuario(tomadorForm.getUsername(),tomadorForm.getNombre(),tomadorForm.getApellido(),tomadorForm.getDocumento(),
                tomadorForm.getTipoDoc(),tomadorForm.getPassword(),tomadorForm.getFechaNacimiento(),telefono,ubicacion);
        usuarioRepository.save(usuario);
        return usuario;
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
