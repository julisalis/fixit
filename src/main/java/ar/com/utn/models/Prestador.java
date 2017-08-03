package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
public class Prestador extends PersistentEntity {

    private Long cuit;
    private Double validado;

    @OneToMany(mappedBy = "prestador")
    private List<Postulacion> postulaciones;

    public Prestador(Long cuit, Double validationResult) {
        super();
        this.cuit = cuit;
        this.validado = validationResult;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public Double getValidado() {
        return validado;
    }

    public void setValidado(Double validado) {
        this.validado = validado;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }
}
