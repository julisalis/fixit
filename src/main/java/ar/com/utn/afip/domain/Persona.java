package ar.com.utn.afip.domain;

import ar.com.utn.afip.enums.*;
import sr.puc.server.ws.soap.a4.*;
import sr.puc.server.ws.soap.a4.Actividad;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Persona {
    private String apellido;
    private String nombre;
    private Sexo sexo;
    private Date nacimiento;
    //private String actividadPrincipal;
    //private Long idActividadPrincipal;
    private List<Actividad> actividades;
    private List<Domicilio> domicilio;
    private TipoClave tipoClave;
    private EstadoClave estadoClave;
    private Long idPersona; //CUIT
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private TipoPersona tipoPersona;

    public Persona() {
    }

    public void buildPersonaFromAfip(PersonaReturn pr) {
        sr.puc.server.ws.soap.a4.Persona p = new sr.puc.server.ws.soap.a4.Persona();
        p = pr.getPersona();

        try {
            this.setNombre(new String(p.getNombre().getBytes("ISO-8859-1"),"utf-8"));
            this.setApellido(new String(p.getApellido().getBytes("ISO-8859-1"),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Sexo
        this.setSexo(Sexo.afipValue(p.getSexo()));
        //Nacimiento
        this.setNacimiento(toDate(p.getFechaNacimiento()));
        this.setIdPersona(p.getIdPersona());
        this.setTipoDocumento(TipoDocumento.getByCodigo(p.getTipoDocumento()));
        this.setNumeroDocumento(p.getNumeroDocumento());
        //this.setTipoPersona(TipoPersona.getByNombre(p.getTipoPersona()));
        //this.setTipoClave(TipoClave.valueOf(p.getTipoClave()));
        //this.setEstadoClave(EstadoClave.valueOf(p.getEstadoClave()));
        //Actividades
        this.setDomicilio(p.getDomicilio());
    }

    private Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Domicilio> getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(List<Domicilio> domicilio) {
        this.domicilio = domicilio;
    }

    public TipoClave getTipoClave() {
        return tipoClave;
    }

    public void setTipoClave(TipoClave tipoClave) {
        this.tipoClave = tipoClave;
    }

    public EstadoClave getEstadoClave() {
        return estadoClave;
    }

    public void setEstadoClave(EstadoClave estadoClave) {
        this.estadoClave = estadoClave;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getNombreCompleto() {
        if(getNombre() == null) {
            return getApellido().trim().toUpperCase();
        }

        return getApellido().trim().toUpperCase() + " " + getNombre().trim().toUpperCase();
    }
}
