package ar.com.utn.form;

import ar.com.utn.models.TiempoPublicacion;
import ar.com.utn.models.TipoTrabajo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by iaruedel on 16/08/17.
 */
public class PublicacionForm {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    private BigDecimal presupMax;
    @NotEmpty
    private List<TipoTrabajo> tiposTrabajo;
    @NotNull
    private Long provincia;
    @NotNull
    private Long localidad;
    @NotNull
    private TiempoPublicacion tiempoPublicacion;

    public PublicacionForm() {
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

    public List<TipoTrabajo> getTiposTrabajo() {
        return tiposTrabajo;
    }

    public void setTiposTrabajo(List<TipoTrabajo> tiposTrabajo) {
        this.tiposTrabajo = tiposTrabajo;
    }
}
