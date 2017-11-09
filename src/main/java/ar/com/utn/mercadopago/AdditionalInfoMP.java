package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserAdditionalInfoMP;
import ar.com.utn.models.Usuario;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Created by julian on 22/10/17.
 */
@Embeddable
@Access(AccessType.FIELD)
public class AdditionalInfoMP {

    @Embedded
    private UserAdditionalInfoMP payer;

    public AdditionalInfoMP() {
    }

    public AdditionalInfoMP(Usuario usuario) {
        this.payer = new UserAdditionalInfoMP(usuario);
    }

    public UserAdditionalInfoMP getPayer() {
        return this.payer;
    }

    public void setPayer(UserAdditionalInfoMP payer) {
        this.payer = payer;
    }


}
