package ar.com.utn.mercadopago;

import ar.com.utn.models.Usuario;

/**
 * Created by julian on 22/10/17.
 */
public class AdditionalInfoMP {

    private String first_name;
    private String last_name;

    public AdditionalInfoMP(Usuario user){
        this.first_name = user.getNombre();
        this.last_name = user.getApellido();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


}
