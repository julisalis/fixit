package ar.com.utn.mercadopago;

import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by julian on 22/10/17.
 */
public interface MoneyFlowService {

    JSONObject efectuarPago(PaymentMP paymentMP, Prestador prestador) throws Exception;

    PaymentMP completePayment(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario tomador);

    String getPaymentId(JSONObject payment) throws JSONException;

    String getDescription(JSONObject payment) throws JSONException;
}
