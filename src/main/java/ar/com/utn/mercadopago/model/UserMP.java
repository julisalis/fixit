package ar.com.utn.mercadopago.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * Created by julian on 12/10/17.
 */
@Embeddable
@Access(AccessType.FIELD)
public class UserMP {

    private String email;

    public UserMP() {
    }

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

