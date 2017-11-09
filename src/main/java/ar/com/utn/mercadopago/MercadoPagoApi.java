package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Usuario;

import java.math.BigDecimal;

/**
 * Created by julian on 12/10/17.
 */
public interface MercadoPagoApi {

    PaymentMP buildPaymentMP(BigDecimal presupAprox, String tokenMP, String title, int installments, String paymentMethodId, UserMP userMP, AdditionalInfoMP additionalInfoMP, double commission);

    void setAccessToken(String accessToken);

    UserMP buildUserMP(Usuario user);

    AdditionalInfoMP buildAdditionalInfoMP(Usuario usuario);

    String makePayment(PaymentMP paymentMP) throws Exception;
}
