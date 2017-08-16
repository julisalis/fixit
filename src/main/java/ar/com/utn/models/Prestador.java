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
    private boolean validado;

    @OneToMany(mappedBy = "prestador")
    private List<Postulacion> postulaciones;

    private Prestador(){}

    public Prestador(Long cuit){
        super();
        this.cuit = cuit;
    }

    public Prestador(Long cuit, boolean validationResult) {
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

    public boolean getValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }
}
