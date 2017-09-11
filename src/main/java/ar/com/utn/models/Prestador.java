package ar.com.utn.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by julis on 14/7/2017.
 */
@Entity
public class Prestador extends PersistentEntity {

    @ManyToMany
    private List<TipoTrabajo> tipos;

    private Long cuit;
    private LocalDate nacimiento;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(columnDefinition="boolean default false", nullable = false)
    private boolean validado;

    @OneToMany(mappedBy = "prestador")
    private List<Postulacion> postulaciones;

    private Prestador(){}

    public Prestador(Long cuit){
        super();
        this.cuit = cuit;
    }

    public Prestador(Long cuit, boolean validationResult, List<TipoTrabajo> tipos, LocalDate nacimiento, Sexo sexo) {
        super();
        if(validationResult) {
            this.validado = true;
            this.cuit = cuit;
            this.nacimiento = nacimiento;
            this.sexo = sexo;
        }else{
            this.validado = false;
        }
        this.tipos = tipos;
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

    public List<TipoTrabajo> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoTrabajo> tipos) {
        this.tipos = tipos;
    }
}
