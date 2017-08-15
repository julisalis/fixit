package ar.com.utn.services.implementation;

import ar.com.utn.exception.TokenNotFoundException;
import ar.com.utn.exception.UserNotActiveException;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.*;
import ar.com.utn.services.MailService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import ar.com.utn.utils.URLBuilder;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.IOP.ExceptionDetailMessage;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private URLBuilder urlBuilder;

    @Autowired
    private CurrentSession currentSession;

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
        Usuario usuarioGenerado = usuarioRepository.save(usuario);

        String token = RandomStringUtils.random(50, 64, 168, true, true);
        String link = urlBuilder.makeOfflineAbsolutePathLink("/signup/activate/"+token);
        usuarioGenerado.setActivationToken(token);
        mailService.sendRegistrationMailTomador(tomadorForm,link);
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
        if(!usuario.isActivado()){
            throw new UserNotActiveException("El usuario se encuentra inactivo");
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

    @Override
    public void activateUser(String token) {
        Usuario user = usuarioRepository.findByActivationToken(token);
        if(user != null){
            user.setActivado(true);
            user.setActivationToken(null);
            logInUser(user.getUsername());
            return;
        }
        throw new TokenNotFoundException("The token does not exists");
    }

    private void logInUser(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        currentSession.logInUser(userDetails);
    }
}

