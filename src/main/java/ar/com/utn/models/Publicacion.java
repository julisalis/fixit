package ar.com.utn.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by julis on 13/7/2017.
 */
@Entity
@Table(name="publicacion")
public class Publicacion extends PersistentEntity{

    private String titulo;
    private String descripcion;
    private BigDecimal presupMax;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "experiencia_tipotrabajo",
            joinColumns = {@JoinColumn(name = "publicacion_fk", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "tipoTrabajo_fk", nullable = false, updatable = false) })
    private List<TipoTrabajo> tiposTrabajo;
    @ManyToOne
    @JoinColumn(name="ubicacion_fk",nullable = false)
    private Ubicacion ubicacion;
    @Enumerated
    private TipoPublicacion tipoPublicacion = TipoPublicacion.REGULAR;
    @Enumerated
    private TiempoPublicacion tiempoPublicacion;
    @OneToMany(mappedBy = "publicacion")
    private Set<Postulacion> postulaciones;
    @OneToMany(mappedBy = "publicacion")
    private List<Mensaje> mensajes;
    @ManyToOne
    @JoinColumn(name = "tomador", nullable = false)
    private Tomador tomador;
    @Embedded
    private PublicacionMultimedia multimedia;
    @Enumerated
    private Urgencia urgencia;
    private LocalDateTime fecha;
    private LocalDateTime fechaCreacion;

    public Publicacion() {
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public Publicacion(String titulo, String descripcion, BigDecimal presupMax, List<TipoTrabajo> tipoTrabajo, Ubicacion ubicacion, TiempoPublicacion tiempoPublicacion, Tomador tomador, PublicacionMultimedia multimedia,LocalDateTime fecha,Urgencia urgencia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupMax = presupMax;
        this.tiposTrabajo = tipoTrabajo;
        this.ubicacion = ubicacion;
        this.tiempoPublicacion = tiempoPublicacion;
        this.tomador = tomador;
        this.multimedia = multimedia;
        this.fecha=fecha;
        this.urgencia=urgencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPresupMax() {
        return presupMax;
    }

    public void setPresupMax(BigDecimal presupMax) {
        this.presupMax = presupMax;
    }

    public Set<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(Set<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public List<TipoTrabajo> getTiposTrabajo() {
        return tiposTrabajo;
    }

    public void setTiposTrabajo(List<TipoTrabajo>  tipoTrabajo) {
        this.tiposTrabajo = tipoTrabajo;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Tomador getTomador() {
        return tomador;
    }

    public void setTomador(Tomador tomador) {
        this.tomador = tomador;
    }

    public TipoPublicacion getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TiempoPublicacion getTiempoPublicacion() {
        return tiempoPublicacion;
    }

    public void setTiempoPublicacion(TiempoPublicacion tiempoPublicacion) {
        this.tiempoPublicacion = tiempoPublicacion;
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Urgencia urgencia) {
        this.urgencia = urgencia;
    }

    public PublicacionMultimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(PublicacionMultimedia multimedia) {
        this.multimedia = multimedia;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
