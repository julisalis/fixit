package ar.com.utn.services.implementation;
import ar.com.utn.mercadopago.MercadoPagoApi;
import ar.com.utn.mercadopago.MoneyFlowService;
import ar.com.utn.mercadopago.PaymentMP;
import ar.com.utn.mercadopago.PaymentMPRepository;
import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.ContratacionRepository;
import ar.com.utn.services.ContratacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContratacionServiceImpl implements ContratacionService {
    @Autowired
    ContratacionRepository contratacionRepository;
    @Autowired
    MoneyFlowService moneyFlowService;

    @Value("${app.mercadopago.application_fee}")
    private Double commission;

    @Autowired
    private MercadoPagoApi mercadoPagoApi;

    @Override
    public String efectuarPago(PaymentMP paymentMP,Prestador prestador) throws Exception {
      mercadoPagoApi.setAccessToken(prestador.getMpPrestador().getAccessToken());
      return mercadoPagoApi.makePayment(paymentMP);
    }

    @Override
    public Contratacion findByPostulacion(Postulacion mipostulacion) {
        return contratacionRepository.findByPostulacion(mipostulacion);
    }

    @Override
    public PaymentMP completePayment(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario tomador) {
        PaymentMP paymentMP =  moneyFlowService.makePaymentMP(postulacion,tokenMP,paymentMethodId,tomador);
        return paymentMP;
    }
}
