package ar.com.utn.mercadopago.model;

/**
 * Created by julian on 12/10/17.
 */
public class UserMP {

    private String email;

    public UserMP(String email){
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

