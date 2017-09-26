package ar.com.utn.form;

import ar.com.utn.models.TipoDoc;
import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.models.Usuario;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by julian on 30/07/17.
 */
public class PerfilForm {

    private Long id;
    private String username;
    private String email;

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    private TipoDoc tipoDoc;
    @NotBlank
    private String documento;
    @Valid
    private TelefonoForm telefono;
    @NotNull
    private Long provincia;
    @NotNull
    private Long localidad;

    private List<TipoTrabajo> tipos;

    private Boolean validar;

    public PerfilForm(Usuario user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.documento = user.getDocumento();
        this.email = user.getEmail();
        this.tipoDoc = user.getTipoDoc();
        if(user.getUbicacion()!=null && user.getUbicacion().getLocalidad()!=null && user.getUbicacion().getLocalidad().getProvincia()!=null){
            this.provincia = user.getUbicacion().getLocalidad().getProvincia().getId();
            this.localidad = user.getUbicacion().getLocalidad().getId();
        }
        this.telefono = new TelefonoForm(user.getTelefono());
        this.username = user.getUsername();
        if(user.getPrestador() != null) {
            this.tipos = user.getPrestador().getTipos();
            this.validar = user.getPrestador().getValidado();
        }
    }

    public PerfilForm() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TelefonoForm getTelefono() {
        return telefono;
    }

    public void setTelefono(TelefonoForm telefono) {
        this.telefono = telefono;
    }

    public Long getProvincia() {
        return provincia;
    }

    public void setProvincia(Long provincia) {
        this.provincia = provincia;
    }

    public Long getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Long localidad) {
        this.localidad = localidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TipoTrabajo> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoTrabajo> tipos) {
        this.tipos = tipos;
    }

    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }
}
