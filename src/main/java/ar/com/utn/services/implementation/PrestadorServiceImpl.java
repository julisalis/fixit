package ar.com.utn.services.implementation;

import ar.com.utn.afip.TicketAcceso;
import ar.com.utn.mercadopago.model.ClientCredentials;
import ar.com.utn.models.*;
import ar.com.utn.repositories.*;
import ar.com.utn.services.PrestadorService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PrestadorServiceImpl implements PrestadorService {

    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;

    @Autowired
    PrestadorRepository prestadorRepository;

    @Autowired
    ActividadAfipRepository actividadAfipRepository;

    @Autowired
    TicketAccesoRepository ticketAccesoRepository;

    @Autowired
    private CurrentSession currentSession;

    @Autowired
    MercadoPagoPrestadorRepository mercadoPagoPrestadorRepository;

    @Override
    public boolean cuitUnique(Long cuit){
        Prestador prestador = prestadorRepository.findByCuit(cuit);
        return prestador == null;
    }

    @Override
    public List<TipoTrabajo> getTiposTrabajos() {
        return tipoTrabajoRepository.findAll();
    }

    @Override
    public List<ActividadAfip> getActividadesAfip() {
        return actividadAfipRepository.findAll();
    }

    @Override
    public TicketAcceso getLastTicketAcceso() { return ticketAccesoRepository.findTicketAccesoByCuitRepresentada(20389962237l); }

    @Override
    public void saveTicketAcceso(TicketAcceso ta) {
        TicketAcceso old = getLastTicketAcceso();
        if(old == null) {
            old = ta;
        }else{
            old.setSign(ta.getSign());
            old.setToken(ta.getToken());
            old.setVencimiento(ta.getVencimiento());
            old.setCuitRepresentada(20389962237l);
        }
        ticketAccesoRepository.save(old);
    }


    @Override
    @Transactional(rollbackFor={Exception.class})
    public void completeCredentials(ClientCredentials clientCredentials){
        Usuario user = currentSession.getUser();
        Prestador prestador = user.getPrestador();
        if(prestador.getMpPrestador()!=null){
            MercadoPagoPrestador mpPrestador = prestador.getMpPrestador();
            mpPrestador.setAccessToken(clientCredentials.getAccess_token());
            mpPrestador.setPublicKey(clientCredentials.getPublic_key());
            mpPrestador.setRefreshToken(clientCredentials.getRefresh_token());
            mpPrestador.setUserId(clientCredentials.getUser_id());
            mpPrestador.setRenewTime(new Date());
        }else{
            MercadoPagoPrestador mpSeller = new MercadoPagoPrestador(prestador, clientCredentials);
            mercadoPagoPrestadorRepository.save(mpSeller);
        }
    }
}
