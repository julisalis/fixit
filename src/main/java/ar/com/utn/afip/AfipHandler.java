package ar.com.utn.afip;

import ar.com.utn.afip.domain.Persona;
import ar.com.utn.afip.enums.AfipWs;
import ar.com.utn.services.PrestadorService;
import org.springframework.beans.factory.annotation.Value;
import sr.puc.server.ws.soap.a4.GetPersona;
import sr.puc.server.ws.soap.a4.PersonaReturn;
import sr.puc.server.ws.soap.a4.PersonaServiceA4;
import sr.puc.server.ws.soap.a4.PersonaServiceA4_Service;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Created by jsalischiker on 16/06/17.
 */
public class AfipHandler {

    private Autenticador autenticador;
    private TicketAcceso ta = null;
    private AfipWs service;
    private Long cuit;
    private PrestadorService prestadorService;


    @Value("${app.afip.ws.endpoint}")
    private String endpoint;
    @Value("${app.afip.ws.dstdn}")
    private String dstdn;
    @Value("${app.afip.ws.p12file}")
    private String p12file;
    @Value("${app.afip.ws.signer}")
    private String signer;
    @Value("${app.afip.ws.p12pass}")
    private String p12pass;
    @Value("${app.afip.ws.ticketTime}")
    private Long ticketTime;

    /*public static void main(String[] args){
        AfipHandler afip = new AfipHandler(AfipWs.PADRON_CUATRO,20389962237L);
        afip.getPersona(20389962237L);
    }*/

    /*private static final int TTL = 5;
    public TicketAcceso getTicketAcceso() {
        int time = 1;
        while(time <= TTL){
            try{
                if (this.ta == null){
                    TicketAcceso taNuevo = this.autenticar();
                    taNuevo.setCuitRepresentada(cuit);
                    this.setTicketAcceso(taNuevo);
                }
                return ta;
            }catch (Exception e){
                e.printStackTrace();
                time++;
            }
        }
        throw new RuntimeException("Problemas en la conexión con AFIP");
    }*/

    public TicketAcceso getTicketAcceso() {
        if (this.ta == null){
            TicketAcceso taNuevo = this.autenticar();
            taNuevo.setCuitRepresentada(cuit);
            this.setTicketAcceso(taNuevo);
            prestadorService.saveTicketAcceso(taNuevo);
        }else{
            if(!this.ta.getVencimiento().isAfter(LocalDateTime.now())) {
                TicketAcceso taNuevo = this.autenticar();
                taNuevo.setCuitRepresentada(cuit);
                this.setTicketAcceso(taNuevo);
                prestadorService.saveTicketAcceso(taNuevo);
            }
        }
        return ta;
    }

    public void setTicketAcceso(TicketAcceso at) {
        this.ta = at;
    }

    public AfipHandler(AfipWs service, Long cuitRepresentada, PrestadorService prestadorService) {
        this.autenticador = new Autenticador();
        this.service = service;
        this.cuit = cuitRepresentada;
        this.prestadorService = prestadorService;
        TicketAcceso taOld = prestadorService.getLastTicketAcceso();
        if(taOld!=null){
            if(taOld.getVencimiento().isAfter(LocalDateTime.now())) {
                this.ta = taOld;
            }
        }
    }

    private TicketAcceso autenticar(){

        //Properties config = new Properties();

        /*try {
            config.load(new FileInputStream("./src/main/resources/afip/wsaa_client.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*String endpoint = config.getProperty("endpoint");
        //String service = config.getProperty("service");
        String dstDN = config.getProperty("dstdn");

        String p12file = config.getProperty("keystore");
        String signer = config.getProperty("keystore-signer");
        String p12pass = config.getProperty("keystore-password");

        // Set the keystore used by SSL
       // System.setProperty("javax.net.ssl.trustStore", config.getProperty("trustStore"));
       // System.setProperty("javax.net.ssl.trustStorePassword",config.getProperty("trustStore_password",""));

        Long TicketTime = new Long(config.getProperty("TicketTime"));*/

        AutenticadorConfig autConfig =
                new AutenticadorConfig(p12file, p12pass,
                        signer, dstdn, this.service.getText(), ticketTime);

        LoginTicketRequest loginTicketRequest = autenticador.buildLoginTicketRequest(autConfig);

        LoginTicketResponse loginTicketResponse = autenticador.invokeWSAA(loginTicketRequest, endpoint);

        return autenticador.obtenerTA(loginTicketResponse);
    }

    public Persona getPersona(Long idPersona) {
        PersonaReturn personaReturn = new PersonaReturn();

        GetPersona personaRequest = new GetPersona();
        personaRequest.setSign(getTicketAcceso().getSign());
        personaRequest.setToken(getTicketAcceso().getToken());
        personaRequest.setCuitRepresentada(getTicketAcceso().getCuitRepresentada());
        personaRequest.setIdPersona(idPersona);

        try {
            //PersonaServiceA10Impl ws = new PersonaServiceA10Impl();
            PersonaServiceA4_Service s = new PersonaServiceA4_Service();
            PersonaServiceA4 psa4 = s.getPersonaServiceA4Port();
            personaReturn = psa4.getPersona(personaRequest.getToken(), personaRequest.getSign(), personaRequest.getCuitRepresentada(), personaRequest.getIdPersona());
            return Persona.buildPersonaFromAfip(personaReturn);

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
