package ar.com.utn.form;

import ar.com.utn.models.Telefono;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by julian on 10/08/17.
 */
public class TelefonoForm {
    @NotBlank
    private String codArea;
    @NotBlank
    private String telefono;
    @NotBlank
    private String codPais;


    public TelefonoForm() {
        this.codPais = "+54";
    }

    public TelefonoForm(String codArea, String codPais, String telefono) {
        this.codArea = codArea;
        this.codPais = codPais;
        this.telefono = telefono;
    }

    public TelefonoForm(Telefono telefono) {
        this.codArea = telefono.getCodArea();
        this.codPais = telefono.getCodPais();
        this.telefono = telefono.getTelefono();
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
