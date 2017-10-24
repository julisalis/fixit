package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MoneyFlowServiceImpl implements MoneyFlowService {

    @Value("${app.mercadopago.application_fee}")
    private BigDecimal commission;

    @Autowired
    private MercadoPagoApi mercadoPagoApi;

    @Autowired
    private PaymentMPRepository paymentMPRepository;


    @Override
    public void makePaymentMP(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario usuario) {
        UserMP userMP = mercadoPagoApi.buildUserMP(usuario);
        AdditionalInfoMP additionalInfoMP = mercadoPagoApi.buildAdditionalInfoMP(usuario);
        PaymentMP paymentMP = mercadoPagoApi.buildPaymentMP(postulacion.getPresupAprox(), tokenMP,"Contratación FiIT",1, paymentMethodId, userMP,additionalInfoMP,commission);
        paymentMPRepository.save(paymentMP);
    }
}
