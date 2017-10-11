package ar.com.utn.services.implementation;

import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.repositories.PostulacionRepository;
import ar.com.utn.services.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    PostulacionRepository postulacionRepository;

    @Override
    public Postulacion createPostulacion(Postulacion postulacionNueva) {
        postulacionNueva = postulacionRepository.save(postulacionNueva);
        return postulacionNueva;
    }

    @Override
    public List<Postulacion> findByPublicacion(Publicacion mipublicacion) {
        return postulacionRepository.findByPublicacion(mipublicacion);
    }
}
