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

    @ElementCollection(targetClass=Rol.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_rol")
    @Column(name="rol")
    private List<Rol> roles;

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
    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Ubicacion ubicacion, String email) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDoc = tipoDoc;
        this.password= password;
        this.telefono = telefono;
        this.ubicacion=ubicacion;
        this.email = email;
    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Long cuit, Prestador prestador, Ubicacion ubicacion,String email) {
        this(username,nombre,apellido,documento,tipoDoc,password,telefono,ubicacion,email);
        this.prestador = prestador;
        this.roles = new ArrayList<>();
        this.roles.add(Rol.PRESTADOR);
    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Ubicacion ubicacion,String email,Tomador tomador) {
        this(username,nombre,apellido,documento,tipoDoc,password,telefono,ubicacion,email);
        this.tomador = tomador;
        this.roles = new ArrayList<>();
        this.roles.add(Rol.TOMADOR);
    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Telefono telefono, Ubicacion ubicacion,String email,Prestador prestador) {
        this(username,nombre,apellido,documento,tipoDoc,password,telefono,ubicacion,email);
        this.prestador = prestador;
        this.roles = new ArrayList<>();
        this.roles.add(Rol.PRESTADOR);
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
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
