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

    @Override
    public Contratacion findByPostulacion(Postulacion mipostulacion) {
        return contratacionRepository.findByPostulacion(mipostulacion);
    }


}
