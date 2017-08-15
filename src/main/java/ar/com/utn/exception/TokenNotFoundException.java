package ar.com.utn.exception;

/**
 * Created by julian on 15/08/17.
 */
public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super();
    }

    public TokenNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(Throwable cause) {
        super(cause);
    }

}

