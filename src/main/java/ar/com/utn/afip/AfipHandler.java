package ar.com.utn.afip;

import ar.com.utn.afip.domain.Persona;
import ar.com.utn.afip.enums.AfipWs;

import java.io.FileInputStream;
import java.util.Properties;

import sr.puc.server.ws.soap.a10.*;

/**
 * Created by jsalischiker on 16/06/17.
 */
public class AfipHandler {

    private Autenticador autenticador;
    private TicketAcceso ta = null;
    private AfipWs service;
    private Long cuit;

    public TicketAcceso getTicketAcceso() {
        if (this.ta == null){
            TicketAcceso taNuevo = this.autenticar();
            taNuevo.setCuitRepresentada(cuit);
            this.setTicketAcceso(taNuevo);
        }
        return ta;
    }

    public void setTicketAcceso(TicketAcceso at) {
        this.ta = at;
    }

    public AfipHandler(AfipWs service, Long cuitRepresentada) {
        this.autenticador = new Autenticador();
        this.service = service;
        this.cuit = cuitRepresentada;
    }

    private TicketAcceso autenticar(){

        Properties config = new Properties();

        try {
            config.load(new FileInputStream("./src/main/resources/afip/wsaa_client.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String endpoint = config.getProperty("endpoint");
        //String service = config.getProperty("service");
        String dstDN = config.getProperty("dstdn");

        String p12file = config.getProperty("keystore");
        String signer = config.getProperty("keystore-signer");
        String p12pass = config.getProperty("keystore-password");

        // Set the keystore used by SSL
       // System.setProperty("javax.net.ssl.trustStore", config.getProperty("trustStore"));
       // System.setProperty("javax.net.ssl.trustStorePassword",config.getProperty("trustStore_password",""));

        Long TicketTime = new Long(config.getProperty("TicketTime"));

        AutenticadorConfig autConfig =
                new AutenticadorConfig(p12file, p12pass,
                        signer, dstDN, this.service.getText(), TicketTime);

        LoginTicketRequest loginTicketRequest = autenticador.buildLoginTicketRequest(autConfig);

        LoginTicketResponse loginTicketResponse = autenticador.invokeWSAA(loginTicketRequest, endpoint);

        return autenticador.obtenerTA(loginTicketResponse);
    }

    public Persona getPersona(Long idPersona) {
        PersonaReturn personaReturn = new PersonaReturn();

        GetPersona personaRequest = new GetPersona();
        personaRequest.setSign(ta.getSign());
        personaRequest.setToken(ta.getToken());
        personaRequest.setCuitRepresentada(ta.getCuitRepresentada());
        personaRequest.setIdPersona(idPersona);

        Persona persona = new Persona();

        try {
            PersonaServiceA10Impl ws = new PersonaServiceA10Impl();
            personaReturn = ws.getPersona(personaRequest.getToken(), personaRequest.getSign(), personaRequest.getCuitRepresentada(), personaRequest.getIdPersona());
            persona.buildPersonaFromAfip(personaReturn);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;

    }

    /*public ArrayCodigosDescripcionesType consultarTiposComprobante() {
        ConsultarTiposComprobanteResponseType r = new ConsultarTiposComprobanteResponseType();

        ConsultarTiposComprobanteRequestType ctc = new ConsultarTiposComprobanteRequestType();
        ctc.setAuthRequest(this.getAuthRequest());
        try {
            MTXCAService ss = new MTXCAService();
            MTXCAServicePortType port = ss.getMTXCAServiceHttpSoap11Endpoint();

            r = port.consultarTiposComprobante(ctc);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return r.getArrayTiposComprobante();
    }

    public List<CodigoDescripcionType> findSubtotalIVATypeList(){
        ConsultarAlicuotasIVAResponseType r = new ConsultarAlicuotasIVAResponseType();

        ConsultarAlicuotasIVARequestType ctc = new ConsultarAlicuotasIVARequestType();
        ctc.setAuthRequest(this.getAuthRequest());
        try {
            MTXCAService ss = new MTXCAService();
            MTXCAServicePortType port = ss.getMTXCAServiceHttpSoap11Endpoint();

            r = port.consultarAlicuotasIVA(ctc);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return r.getArrayAlicuotasIVA().getCodigoDescripcion();
    }

    public List<CodigoDescripcionType> consultarTiposTributo(){
        ConsultarTiposTributoResponseType r = new ConsultarTiposTributoResponseType();

        ConsultarTiposTributoRequestType ctt = new ConsultarTiposTributoRequestType();
        ctt.setAuthRequest(this.getAuthRequest());
        try {
            MTXCAService ss = new MTXCAService();
            MTXCAServicePortType port = ss.getMTXCAServiceHttpSoap11Endpoint();

            r = port.consultarTiposTributo(ctt);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return r.getArrayTiposTributo().getCodigoDescripcion();
    }*/
}