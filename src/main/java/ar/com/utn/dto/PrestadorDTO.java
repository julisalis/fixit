package ar.com.utn.dto;

import ar.com.utn.models.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by julian on 30/07/17.
 */
public class PrestadorDTO {
    private Sexo sexo;
    private boolean validado;
    private List<Postulacion> postulaciones;
    private MercadoPagoPrestador mpPrestador;
    private Integer cantContrataciones;

    public PrestadorDTO(Prestador prestador) {
        this.sexo = prestador.getSexo();
        this.validado = prestador.getValidado();
        this.postulaciones = prestador.getPostulaciones();
        this.mpPrestador = prestador.getMpPrestador();
        this.cantContrataciones = prestador.getPostulaciones().stream().filter(postulacion -> postulacion.getEstadoPostulacion() == EstadoPostulacion.CONTRATADA).collect(Collectors.toList()).size();
    }

    public PrestadorDTO() {
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

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public MercadoPagoPrestador getMpPrestador() {
        return mpPrestador;
    }

    public void setMpPrestador(MercadoPagoPrestador mpPrestador) {
        this.mpPrestador = mpPrestador;
    }

    public Integer getCantContrataciones() {
        return cantContrataciones;
    }

    public void setCantContrataciones(Integer cantContrataciones) {
        this.cantContrataciones = cantContrataciones;
    }
}
