package ar.com.utn.models;

import ar.com.utn.mercadopago.model.ClientCredentials;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by julian on 07/10/17.
 */
@Entity
public class MercadoPagoPrestador extends PersistentEntity{
    @OneToOne
    @JoinColumn(name="prestador")
    private Prestador prestador;
    @Column(name="access_token")
    private String accessToken;
    @Column(name="public_key")
    private String publicKey;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="user_id")
    private Long userId;
    @Column(name="expires_in")
    private Long expiresIn;
    @Column(name="renew_time")
    private Date renewTime;

    public MercadoPagoPrestador() {
    }

    public MercadoPagoPrestador(Prestador prestador,ClientCredentials clientCredentials){
        this.accessToken = clientCredentials.getAccess_token();
        this.publicKey = clientCredentials.getPublic_key();
        this.refreshToken = clientCredentials.getPublic_key();
        this.userId = clientCredentials.getUser_id();
        this.expiresIn = clientCredentials.getExpires_in();
        this.prestador = prestador;
        this.renewTime = new Date();
    }

    public boolean isCredentialsExpired(){
        Date today = new Date();
        long secondsBetween = getSecondBetween(this.renewTime, today);
        if(secondsBetween >= expiresIn){
            return true;
        }
        return false;
    }

    public static long getSecondBetween(Date d1, Date d2){
        long seconds = (d2.getTime()-d1.getTime())/1000;
        return seconds;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getRenewTime() {
        return renewTime;
    }

    public void setRenewTime(Date renewTime) {
        this.renewTime = renewTime;
    }
}
