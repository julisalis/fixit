package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum TipoDocumento {
    CUIT(80),
    CUIL(86),
    CDI(87),
    LE(89),
    LC(90),
    CI_EXTRANJERA(91),
    EN_TRAMITE(92),
    ACTA_NACIMIENTO(93),
    CI_BS_AS(95),
    DNI(96),
    PASAPORTE(94),
    CI_POLICIA(0);

    private final Integer codigo;

    private TipoDocumento(final Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getName(){
        return name();
    }
}