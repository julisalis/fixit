package ar.com.utn.afip.domain;

import ar.com.utn.afip.enums.EstadoClave;
import ar.com.utn.afip.enums.TipoClave;
import ar.com.utn.afip.enums.TipoDocumento;
import ar.com.utn.afip.enums.TipoPersona;
import sr.puc.server.ws.soap.a10.Domicilio;

import java.util.List;

public class Persona {
    private String apellido;
    private String nombre;
    private List<Actividad> actividades;
    private List<Domicilio> domicilio;
    private TipoClave tipoClave;
    private EstadoClave estadoClave;
    private Long idPersona; //CUIT
    private TipoDocumento tipoDocumento;
    private Long numeroDocumento;
    private TipoPersona tipoPersona;
}
