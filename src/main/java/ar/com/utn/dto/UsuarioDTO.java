package ar.com.utn.dto;

import ar.com.utn.models.Telefono;
import ar.com.utn.models.Ubicacion;
import ar.com.utn.models.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioDTO {

    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private LocalDate fechaCreacion;
    private Telefono telefono;
    private Double calificacionPromedio;
    private Ubicacion ubicacion;
    private PrestadorDTO prestador;
    private TomadorDTO tomador;

    public UsuarioDTO(Usuario usuario, boolean soyPrestador) {
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.fechaCreacion = usuario.getFechaCreacion().toLocalDate();
        this.telefono = usuario.getTelefono();
        this.calificacionPromedio = usuario.getCalificacionPromedio();
        this.ubicacion = usuario.getUbicacion();
        if (soyPrestador) {
            this.prestador = new PrestadorDTO(usuario.getPrestador());
        }
        else {
            this.tomador = new TomadorDTO(usuario);
        }
    }

    public UsuarioDTO() {
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public PrestadorDTO getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorDTO prestadorDTO) {
        this.prestador = prestadorDTO;
    }

    public TomadorDTO getTomador() {
        return tomador;
    }

    public void setTomador(TomadorDTO tomador) {
        this.tomador = tomador;
    }
}
