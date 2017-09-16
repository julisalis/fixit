package ar.com.utn.afip;

import ar.com.utn.models.PersistentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by jsalischiker on 16/06/17.
 */
@Entity
public class TicketAcceso extends PersistentEntity{

    @Column(length=800)
    private String token;
    @Column(length=800)
    private String sign;
    private Long cuitRepresentada;
    private LocalDateTime vencimiento;

    private TicketAcceso() {}

    public TicketAcceso(String token, String sign, LocalDateTime vencimiento) {
        if(token.isEmpty() || sign.isEmpty()){
            throw new RuntimeException("El Token o el Sign estan vacios.");
        }
        this.token = token;
        this.sign = sign;
        this.vencimiento = vencimiento;
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

    public Long getCuitRepresentada() {
        return cuitRepresentada;
    }

    public void setCuitRepresentada(Long cuitRepresentada) {
        this.cuitRepresentada = cuitRepresentada;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setVencimiento(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }
}
