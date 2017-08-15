package ar.com.utn.exception;

/**
 * Created by julian on 15/08/17.
 */
public class UserNotActiveException extends RuntimeException {

    public UserNotActiveException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserNotActiveException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public UserNotActiveException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public UserNotActiveException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public UserNotActiveException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
