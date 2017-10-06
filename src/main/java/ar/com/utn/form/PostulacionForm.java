package ar.com.utn.form;

import ar.com.utn.models.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iaruedel on 16/08/17.
 */
public class PostulacionForm {
    private Long id;
    @NotBlank
    private String descripcion;
    @NotNull
    private BigDecimal presupAprox;
    @NotNull
    private String currencyCode;
    @NotNull
    private BigDecimal duracionAprox;
    private String comentarios;

    public PostulacionForm() {
    }

    public PostulacionForm(Postulacion postulacion) {
        this.descripcion = postulacion.getDescripcion();
        this.presupAprox = postulacion.getPresupAprox();
        this.currencyCode = postulacion.getCurrency().getCurrencyCode();
        this.duracionAprox = postulacion.getDuracionAprox();
        this.comentarios = postulacion.getComentarios();
        this.id = postulacion.getId();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPresupAprox() {
        return presupAprox;
    }

    public void setPresupAprox(BigDecimal presupAprox) {
        this.presupAprox = presupAprox;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDuracionAprox() {
        return duracionAprox;
    }

    public void setDuracionAprox(BigDecimal duracionAprox) {
        this.duracionAprox = duracionAprox;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
