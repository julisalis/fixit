package ar.com.utn.afip;

/**
 * Created by jsalischiker on 16/06/17.
 */
public class AutenticadorConfig {

    private String p12Filename;
    private String p12pass;
    private String signer;
    private String dstDN;
    private String service;
    private Long ticketTime;

    public AutenticadorConfig(String p12Filename, String p12pass, String signer, String dstDN, String service, Long ticketTime) {
        this.p12Filename = p12Filename;
        this.p12pass = p12pass;
        this.signer = signer;
        this.dstDN = dstDN;
        this.service = service;
        this.ticketTime = ticketTime;
    }

    public String getP12Filename() {
        return p12Filename;
    }

    public String getP12pass() {
        return p12pass;
    }

    public String getSigner() {
        return signer;
    }

    public String getDstDN() {
        return dstDN;
    }

    public String getService() {
        return service;
    }

    public Long getTicketTime() {
        return ticketTime;
    }

}
