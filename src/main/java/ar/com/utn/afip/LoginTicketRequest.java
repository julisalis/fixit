package ar.com.utn.afip;

/**
 * Created by jsalischiker on 16/06/17.
 */
public class LoginTicketRequest {

    private byte [] data;

    public LoginTicketRequest(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}
