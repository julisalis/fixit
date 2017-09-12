package ar.com.utn.models;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;
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
    private Currency currency;
    @ManyToOne
    @JoinColumn(name="tipotrabajo_fk",nullable = false)
    private TipoTrabajo tipoTrabajo;
    @ManyToOne
    @JoinColumn(name="localidad_fk",nullable = false)
    private Localidad localidad;
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

    private Date fecha;

    private Date fechaCreacion;

    public Publicacion() {
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
    }

    public Publicacion(String currency,String titulo, String descripcion, BigDecimal presupMax, TipoTrabajo tipoTrabajo, Localidad localidad, TiempoPublicacion tiempoPublicacion, Tomador tomador, PublicacionMultimedia multimedia,Date fecha,Urgencia urgencia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupMax = presupMax;
        this.tipoTrabajo = tipoTrabajo;
        this.localidad = localidad;
        this.tiempoPublicacion = tiempoPublicacion;
        this.tomador = tomador;
        this.multimedia = multimedia;
        this.fecha=fecha;
        this.urgencia=urgencia;
        this.currency = Currency.getInstance(currency)!=null? Currency.getInstance(currency) : Currency.getInstance("ARS") ;
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

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
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

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
