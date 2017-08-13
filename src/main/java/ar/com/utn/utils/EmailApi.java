package ar.com.utn.utils;

/**
 * Created by julian on 13/08/17.
 */
public interface EmailApi {

    void sendMessage(String subject, String emailDestination, String emailFrom, String content);
}
