package ar.com.utn.mercadopago.model;

/**
 * Created by julian on 12/10/17.
 */
public class ClientCredentials {

    private String access_token;
    private String public_key;
    private String refresh_token;
    private boolean live_mode;
    private long user_id;
    private String token_type;
    private long expires_in;
    private String scope;

    public ClientCredentials(){

    }

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getPublic_key() {
        return public_key;
    }
    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public boolean isLive_mode() {
        return live_mode;
    }
    public void setLive_mode(boolean live_mode) {
        this.live_mode = live_mode;
    }
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public long getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
}

