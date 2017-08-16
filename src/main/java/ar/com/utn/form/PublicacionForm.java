package ar.com.utn.form;

import ar.com.utn.models.TipoTrabajo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    private TipoTrabajo TipoTrabajo;
    @NotNull
    private Long provincia;
    @NotNull
    private Long localidad;

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

    public ar.com.utn.models.TipoTrabajo getTipoTrabajo() {
        return TipoTrabajo;
    }

    public void setTipoTrabajo(ar.com.utn.models.TipoTrabajo tipoTrabajo) {
        TipoTrabajo = tipoTrabajo;
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
}
