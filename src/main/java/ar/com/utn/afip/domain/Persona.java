//package ar.com.utn.afip.domain;
//
//import ar.com.utn.afip.enums.EstadoClave;
//import ar.com.utn.afip.enums.TipoClave;
//import ar.com.utn.afip.enums.TipoDocumento;
//import ar.com.utn.afip.enums.TipoPersona;
//import sr.puc.server.ws.soap.a10.Domicilio;
//import sr.puc.server.ws.soap.a10.PersonaReturn;
//
//import java.util.List;
//
//public class Persona {
//    private String apellido;
//    private String nombre;
//    private String actividadPrincipal;
//    private Long idActividadPrincipal;
//    private List<Domicilio> domicilio;
//    private TipoClave tipoClave;
//    private EstadoClave estadoClave;
//    private Long idPersona; //CUIT
//    private TipoDocumento tipoDocumento;
//    private String numeroDocumento;
//    private TipoPersona tipoPersona;
//
//    public void buildPersonaFromAfip(PersonaReturn pr) {
//        sr.puc.server.ws.soap.a10.Persona p = new sr.puc.server.ws.soap.a10.Persona();
//        p = pr.getPersona();
//
//        this.setNombre(p.getNombre());
//        this.setApellido(p.getApellido());
//        this.setIdPersona(p.getIdPersona());
//        this.setTipoDocumento(TipoDocumento.getByCodigo(p.getTipoDocumento()));
//        this.setNumeroDocumento(p.getNumeroDocumento());
//        this.setTipoPersona(TipoPersona.getByNombre(p.getTipoPersona()));
//        this.setTipoClave(TipoClave.valueOf(p.getTipoClave()));
//        this.setEstadoClave(EstadoClave.valueOf(p.getEstadoClave()));
//        this.setActividadPrincipal(p.getDescripcionActividadPrincipal());
//        this.setIdActividadPrincipal(p.getIdActividadPrincipal());
//        this.setDomicilio(p.getDomicilio());
//
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getActividadPrincipal() {
//        return actividadPrincipal;
//    }
//
//    public void setActividadPrincipal(String actividadPrincipal) {
//        this.actividadPrincipal = actividadPrincipal;
//    }
//
//    public Long getIdActividadPrincipal() {
//        return idActividadPrincipal;
//    }
//
//    public void setIdActividadPrincipal(Long idActividadPrincipal) {
//        this.idActividadPrincipal = idActividadPrincipal;
//    }
//
//    public List<Domicilio> getDomicilio() {
//        return domicilio;
//    }
//
//    public void setDomicilio(List<Domicilio> domicilio) {
//        this.domicilio = domicilio;
//    }
//
//    public TipoClave getTipoClave() {
//        return tipoClave;
//    }
//
//    public void setTipoClave(TipoClave tipoClave) {
//        this.tipoClave = tipoClave;
//    }
//
//    public EstadoClave getEstadoClave() {
//        return estadoClave;
//    }
//
//    public void setEstadoClave(EstadoClave estadoClave) {
//        this.estadoClave = estadoClave;
//    }
//
//    public Long getIdPersona() {
//        return idPersona;
//    }
//
//    public void setIdPersona(Long idPersona) {
//        this.idPersona = idPersona;
//    }
//
//    public TipoDocumento getTipoDocumento() {
//        return tipoDocumento;
//    }
//
//    public void setTipoDocumento(TipoDocumento tipoDocumento) {
//        this.tipoDocumento = tipoDocumento;
//    }
//
//    public String getNumeroDocumento() {
//        return numeroDocumento;
//    }
//
//    public void setNumeroDocumento(String numeroDocumento) {
//        this.numeroDocumento = numeroDocumento;
//    }
//
//    public TipoPersona getTipoPersona() {
//        return tipoPersona;
//    }
//
//    public void setTipoPersona(TipoPersona tipoPersona) {
//        this.tipoPersona = tipoPersona;
//    }
//}
