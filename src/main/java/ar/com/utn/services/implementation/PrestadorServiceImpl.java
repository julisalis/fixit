package ar.com.utn.services.implementation;

import ar.com.utn.afip.TicketAcceso;
import ar.com.utn.models.ActividadAfip;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.repositories.ActividadAfipRepository;
import ar.com.utn.repositories.PrestadorRepository;
import ar.com.utn.repositories.TicketAccesoRepository;
import ar.com.utn.repositories.TipoTrabajoRepository;
import ar.com.utn.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
