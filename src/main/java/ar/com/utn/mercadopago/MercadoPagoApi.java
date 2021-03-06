package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Usuario;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by julian on 12/10/17.
 */
public interface MercadoPagoApi {

    PaymentMP buildPaymentMP(BigDecimal presupAprox, String tokenMP, String title, int installments, String paymentMethodId, UserMP userMP, AdditionalInfoMP additionalInfoMP, double commission);

    void setAccessToken(String accessToken);

    UserMP buildUserMP(Usuario user);

    AdditionalInfoMP buildAdditionalInfoMP(Usuario usuario);

    String getPaymentMPId(JSONObject jsonObject) throws JSONException;

    JSONObject makePayment(PaymentMP paymentMP) throws Exception;

    String getDescription(JSONObject payment) throws JSONException;
}
