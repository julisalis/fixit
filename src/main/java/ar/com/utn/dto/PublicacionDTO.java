package ar.com.utn.dto;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Created by iaruedel on 12/09/17.
 */
public class PublicacionDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private BigDecimal presupMax;
    private String currency;
    private TipoTrabajo tipoTrabajo;
    private Localidad localidad;
    private Provincia provincia;
    private String urgencia;
    private LocalDate fecha;
    private EstadoPublicacion estado;
    private PublicacionFotoForm primaryImage;
    private List<PublicacionFotoForm> publicacionFotoForms = new ArrayList<>();

    public PublicacionDTO(Publicacion publicacion,List<PublicacionFotoForm> publicacionFotoForms, PublicacionFotoForm primaryImage) {
        this.id = publicacion.getId();
        this.titulo = publicacion.getTitulo();
        this.descripcion = publicacion.getDescripcion();
        this.presupMax = publicacion.getPresupMax();
        this.currency = publicacion.getCurrency().getCurrencyCode();
        this.tipoTrabajo = publicacion.getTipoTrabajo();
        this.localidad = publicacion.getLocalidad();
        this.provincia = publicacion.getLocalidad().getProvincia();
        this.urgencia = publicacion.getUrgencia().name();
        this.fecha = publicacion.getFecha();
        this.estado = publicacion.getEstadoPublicacion();
        this.primaryImage = primaryImage;
        this.publicacionFotoForms = publicacionFotoForms;
    }

    public PublicacionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public PublicacionFotoForm getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(PublicacionFotoForm primaryImage) {
        this.primaryImage = primaryImage;
    }

    public List<PublicacionFotoForm> getPublicacionFotoForms() {
        return publicacionFotoForms;
    }

    public void setPublicacionFotoForms(List<PublicacionFotoForm> publicacionFotoForms) {
        this.publicacionFotoForms = publicacionFotoForms;
    }
}
