package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by julis on 13/7/2017.
 */
@Entity
@Table(name="postulaciones")
public class Postulacion extends PersistentEntity {

    private String solucion;
    private Double presupOfrecido;
    private Double duracionEst;
    private String recursos;
    private Boolean elegida;

    @ManyToOne
    @JoinColumn(name = "publicacion", nullable = false)
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "prestador", nullable = false)
    private Prestador prestador;

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Double getPresupOfrecido() {
        return presupOfrecido;
    }

    public void setPresupOfrecido(Double presupOfrecido) {
        this.presupOfrecido = presupOfrecido;
    }

    public Double getDuracionEst() {
        return duracionEst;
    }

    public void setDuracionEst(Double duracionEst) {
        this.duracionEst = duracionEst;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public Boolean getElegida() {
        return elegida;
    }

    public void setElegida(Boolean elegida) {
        this.elegida = elegida;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }
}
