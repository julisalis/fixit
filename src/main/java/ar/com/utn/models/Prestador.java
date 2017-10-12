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
    @Column(columnDefinition = "DATETIME")
    private LocalDate nacimiento;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Column(columnDefinition="boolean default false", nullable = false)
    private boolean validado;
    @OneToMany(mappedBy = "prestador")
    private List<Postulacion> postulaciones;
    @OneToOne(mappedBy="prestador",cascade=CascadeType.ALL)
    private MercadoPagoPrestador mpPrestador;

    private Prestador(){}

    public Prestador(Long cuit){
        super();
        this.cuit = cuit;
    }

    public Prestador(List<TipoTrabajo> tipos){
        super();
        this.tipos = tipos;
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

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public boolean isValidado() {
        return validado;
    }

    public MercadoPagoPrestador getMpPrestador() {
        return mpPrestador;
    }

    public void setMpPrestador(MercadoPagoPrestador mpPrestador) {
        this.mpPrestador = mpPrestador;
    }
}
