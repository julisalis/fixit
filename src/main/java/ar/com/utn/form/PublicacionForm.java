package ar.com.utn.form;

import ar.com.utn.models.Publicacion;
import ar.com.utn.models.TiempoPublicacion;
import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.models.Urgencia;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by iaruedel on 16/08/17.
 */
public class PublicacionForm {

    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    @NotNull
    private BigDecimal presupMax;
    @NotNull
    private TipoTrabajo tipoTrabajo;
    @NotNull
    private String currencyCode;
    @NotNull
    private Long provincia;
    @NotNull
    private Long localidad;
    @NotNull
    private TiempoPublicacion tiempoPublicacion;
    @NotNull
    private Urgencia urgencia;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern="MM-dd-yyyy")
    private LocalDate fecha;

    public PublicacionForm() {
    }

    public PublicacionForm(Publicacion publicacion) {
        this.titulo = publicacion.getTitulo();
        this.descripcion = publicacion.getDescripcion();
        this.presupMax = publicacion.getPresupMax();
        this.currencyCode = publicacion.getCurrency().getCurrencyCode();
        this.tipoTrabajo = publicacion.getTipoTrabajo();
        this.localidad = publicacion.getLocalidad().getId();
        this.provincia = publicacion.getLocalidad().getProvincia().getId();
        this.urgencia = publicacion.getUrgencia();
        this.fecha = publicacion.getFecha();
    }

    public TiempoPublicacion getTiempoPublicacion() {
        return tiempoPublicacion;
    }

    public void setTiempoPublicacion(TiempoPublicacion tiempoPublicacion) {
        this.tiempoPublicacion = tiempoPublicacion;
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

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Urgencia urgencia) {
        this.urgencia = urgencia;
    }

//    public LocalDate getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(LocalDate fecha) {
//        this.fecha = fecha;
//    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
