package ar.com.utn.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by julian on 10/08/17.
 */
public class TelefonoForm {
    private String codArea;
    @NotBlank
    private String telefono;
    @NotBlank
    private String codPais;


    public TelefonoForm() {
    }

    public TelefonoForm(String codArea, String codPais, String telefono) {
        this.codArea = codArea;
        this.codPais = codPais;
        this.telefono = telefono;
    }

    public String getCodArea() {
        return codArea;
    }

    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
