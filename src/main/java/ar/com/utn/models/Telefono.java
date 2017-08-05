package ar.com.utn.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by iaruedel on 05/08/17.
 */
@Entity
@Table(name="telefono")
public class Telefono extends PersistentEntity{
    private String codPais;
    private String codArea;
    private String telefono;

    public Telefono(String codPais, String codArea, String telefono) {
        this.codPais = codPais;
        this.codArea = codArea;
        this.telefono = telefono;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getCodArea() {
        return codArea;
    }

    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
