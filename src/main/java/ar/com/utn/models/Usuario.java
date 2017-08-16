package ar.com.utn.models;

import javax.persistence.*;
import java.util.*;

/**
 * Created by julis on 17/5/2017.
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends PersistentEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nombre;
    private String apellido;

    private Date fechaCreacion;

    @Enumerated (EnumType.STRING)
    private TipoDoc tipoDoc;

    private String documento;

    @ManyToOne
    @JoinColumn(name="id_telefono",nullable = false)
    private Telefono telefono;

    private Double calificacionPromedio;

    @ManyToOne
    @JoinColumn(name="id_ubicacion",nullable = false)
    private Ubicacion ubicacion;

    @OneToOne
    private Tomador tomador;

    @OneToOne
    private Prestador prestador;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USER;

    @Column(columnDefinition="boolean default false", nullable = false)
    private  boolean activado= false;

    @Column(name="activation_token")
    private String activationToken;

    @PrePersist
	protected void onCreate() {
        this.fechaCreacion = new Date();
    }
    
    public Usuario(){

    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Long cuit, Prestador prestador, Ubicacion ubicacion,String emails) {
        super();
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDoc = tipoDoc;
        this.password= password;
        this.telefono = telefono;
        this.prestador = prestador;
        this.ubicacion=ubicacion;
        this.email = email;
        //falta el rol
    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Ubicacion ubicacion,String email,Tomador tomador) {
        super();
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDoc = tipoDoc;
        this.password= password;
        this.telefono = telefono;
        this.tomador = tomador;
        this.ubicacion=ubicacion;
        this.email = email;
        //falta el rol
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public Tomador getTomador() {
        return tomador;
    }

    public void setTomador(Tomador tomador) {
        this.tomador = tomador;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }
}
