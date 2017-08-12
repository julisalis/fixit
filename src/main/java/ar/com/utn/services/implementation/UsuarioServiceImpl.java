package ar.com.utn.services.implementation;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.*;
import ar.com.utn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by julis on 26/5/2017.
 */
@Service
public class UsuarioServiceImpl extends BaseService  implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private TomadorRepository tomadorRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    @Transactional
    public Usuario registrarPrestador(PrestadorForm prestadorForm, Prestador prestador) {
        Provincia provincia=provinciaRepository.findOne(prestadorForm.getProvincia());
        Localidad localidad=localidadRepository.findOne(prestadorForm.getLocalidad());
        Telefono telefono= new Telefono(prestadorForm.getTelefono().getCodPais(),prestadorForm.getTelefono().getCodArea(),prestadorForm.getTelefono().getTelefono());
        Ubicacion ubicacion=new Ubicacion(provincia,localidad);
        Usuario usuario = new Usuario(prestadorForm.getUsername(),prestadorForm.getNombre(),prestadorForm.getApellido(),prestadorForm.getDocumento(),
                prestadorForm.getTipoDoc(),prestadorForm.getPassword(),telefono,prestadorForm.getCuit(),prestador,ubicacion,prestadorForm.getEmail());
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario registrarTomador(TomadorForm tomadorForm) {
        Provincia provincia=provinciaRepository.findOne(tomadorForm.getProvincia());
        Localidad localidad=localidadRepository.findOne(tomadorForm.getLocalidad());
        Ubicacion ubicacion=new Ubicacion(provincia,localidad);
        ubicacionRepository.save(ubicacion);
        Telefono telefono= new Telefono(tomadorForm.getTelefono().getCodPais(),tomadorForm.getTelefono().getCodArea(),tomadorForm.getTelefono().getTelefono());
        telefonoRepository.save(telefono);
        Tomador tomador = tomadorRepository.save(new Tomador());
        Usuario usuario = new Usuario(tomadorForm.getUsername(),tomadorForm.getNombre(),tomadorForm.getApellido(),tomadorForm.getDocumento(),
                tomadorForm.getTipoDoc(),encoder.encode(tomadorForm.getPassword()),telefono,ubicacion,tomadorForm.getEmail(),tomador);
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public List<Provincia> getProvincias() {
        return provinciaRepository.findAllProvincias();
    }

    @Override
    public List<SelectorForm> findAllLocalidadByProvince(Long provinceId) {
        Provincia provincia = provinciaRepository.findOne(provinceId);
        return localidadRepository.findAllByProvinciaOrderByNombre(provincia).stream()
                .map(localidad -> new SelectorForm(localidad.getId(),localidad.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(usuario);
    }


    @Override
    public boolean usernameUnique(String username){
        Usuario user = usuarioRepository.findByUsernameIgnoreCase(username);
        Usuario userlogged = getUserLogged();
        if(userlogged != null && user != null){
            return userlogged.equals(user);
        }
        return user == null;
    }

    @Override
    public boolean emailUnique(String email){
        Usuario user = usuarioRepository.findByEmail(email);
        Usuario userlogged = getUserLogged();
        if(userlogged != null && user != null){
            return userlogged.equals(user);
        }
        return user == null;
    }
}
