package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum AfipWs {
    FACTURA_ELECTRONICA_A_B("wsmtxca"),
    FACTURA_ELECTRONICA_E("wsfexv1"),
    PADRON_CUATRO("ws_sr_padron_a4"),
    PADRON_CINCO("ws_sr_padron_a5"),
    PADRON_DIEZ("ws_sr_padron_a10");

    private final String text;

    private AfipWs(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getName(){
        return name();
    }
}
