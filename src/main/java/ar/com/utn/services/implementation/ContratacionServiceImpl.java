package ar.com.utn.services.implementation;

import ar.com.utn.mercadopago.MercadoPagoApi;
import ar.com.utn.mercadopago.PaymentMPRepository;
import ar.com.utn.models.Contratacion;
import ar.com.utn.repositories.ContratacionRepository;
import ar.com.utn.repositories.PostulacionRepository;
import ar.com.utn.services.ContratacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContratacionServiceImpl implements ContratacionService {
    @Autowired
    PostulacionRepository postulacionRepository;
    @Autowired
    PaymentMPRepository paymentMPRepository;
    @Autowired
    ContratacionRepository contratacionRepository;

    @Value("${app.mercadopago.application_fee}")
    private Double commission;

    @Autowired
    private MercadoPagoApi mercadoPagoApi;

    @Override
    public String efectuarPago(Contratacion contratacion) throws Exception {

       return mercadoPagoApi.makePayment(contratacion.getPaymentMP());

    }
}
