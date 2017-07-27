package ar.com.utn.afip;

import java.time.LocalDateTime;

/**
 * Created by scamisay on 16/06/17.
 */
public class TicketAcceso {

    private String token;
    private String sign;
    private LocalDateTime vencimiento;

    public TicketAcceso(String token, String sign) {
        if(token.isEmpty() || sign.isEmpty()){
            throw new RuntimeException("El Token o el Sign estan vacios.");
        }
        this.token = token;
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public String getSign() {
        return sign;
    }

    public LocalDateTime getVencimiento() {
        return vencimiento;
    }
}
