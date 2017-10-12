package ar.com.utn.exception;

/**
 * Created by julian on 12/10/17.
 */
public class MercadoPagoException extends Exception {

    private static final long serialVersionUID = 4513324783295297901L;

    public MercadoPagoException() {
        super();
    }

    public MercadoPagoException(String message) {
        super(message);
    }
}
