package ar.com.utn.mercadopago;

import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Usuario;
import com.google.gson.Gson;
import com.mercadopago.MP;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;

/**
 * Created by julian on 12/10/17.
 */
@Component
public class MercadoPagoApiImpl implements MercadoPagoApi {
    static final Logger logger = Logger.getLogger(MercadoPagoApiImpl.class);

    private final String PAYMENTS_URL = "/v1/payments";

    private String accessToken;

    private MP getMPInstance(){
        MP mp = new MP(accessToken);
        return mp;
    }

    @Override
    public UserMP buildUserMP(Usuario user){
        UserMP userMP = new UserMP(user.getEmail());
        return userMP;
    }

    @Override
    public AdditionalInfoMP buildAdditionalInfoMP(Usuario usuario) {
        return new AdditionalInfoMP(usuario);
    }

    @Override
    public PaymentMP buildPaymentMP(BigDecimal presupAprox, String tokenMP, String title, int installments, String paymentMethodId, UserMP userMP, AdditionalInfoMP additionalInfoMP, double commission) {
        PaymentMP paymentMP = new PaymentMP(presupAprox.doubleValue(), tokenMP, title,installments, paymentMethodId, userMP,additionalInfoMP,commission);
        return paymentMP;
    }


    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getPaymentMPId(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("response").getString("id");
    }

    @Override
    public JSONObject makePayment(PaymentMP paymentMP) throws Exception{
        MP mp = getMPInstance();
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("transaction_amount",paymentMP.getTransaction_amount());
        map.put("token",paymentMP.getToken());
        map.put("description",paymentMP.getDescription());
        map.put("installments",paymentMP.getInstallments());
        map.put("payment_method_id",paymentMP.getPayment_method_id());
        map.put("binary_mode",paymentMP.isBinary_mode());
        map.put("payer",paymentMP.getPayer());
        map.put("additional_info",paymentMP.getAdditional_info());
        map.put("application_fee",paymentMP.getApplication_fee());
        String paymentMPJson = gson.toJson(map);
        JSONObject payment = mp.post(PAYMENTS_URL, paymentMPJson);
        controlResponse(payment);
        controlResponsePayment(payment);
        return payment;
    }

    @Override
    public String getDescription(JSONObject payment) throws JSONException {
        return "La calificación se ha generado con éxito.\nSu pago de  "
                + payment.getJSONObject("response").getJSONObject("transaction_details").getString("total_paid_amount")
                + " ARS, mediante su tarjeta "
                +payment.getJSONObject("response").getString("payment_method_id").toUpperCase()
                + " **** "
                +payment.getJSONObject("response").getJSONObject("card").getString("last_four_digits")
                +" ya se acreditó en la cuenta del profesional.\nPara más información acceder a "
                +payment.getJSONObject("response").getString("statement_descriptor");
    }

    private void controlResponsePayment(JSONObject jsonObject)throws Exception{
        if(!jsonObject.getJSONObject("response").getString("status").equals("approved")){
            logger.error(jsonObject);
            throw new MercadoPagoException();
        }
    }


    private void controlResponse(JSONObject jsonObject)throws Exception{
        if(jsonObject.getInt("status") == 400 || jsonObject.getInt("status") == 401 ||
                jsonObject.getInt("status") == 404){
            logger.error(jsonObject);
            throw new MercadoPagoException();
        }
    }


}
