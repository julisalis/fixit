package ar.com.utn.services.implementation;

import ar.com.utn.form.PostulacionForm;
import ar.com.utn.models.EstadoPostulacion;
import ar.com.utn.models.EstadoPublicacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.repositories.PostulacionRepository;
import ar.com.utn.services.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public Postulacion findById(Long postulacionId) {
        return postulacionRepository.findOne(postulacionId);
    }

    @Override
    public Postulacion setContratada(Postulacion postulacion) {
        try {
            postulacion.getPublicacion().getPostulaciones().stream().filter(p -> p != postulacion).forEach(p -> p.setEstadoPostulacion(EstadoPostulacion.RECHAZADA));

            postulacion.setEstadoPostulacion(EstadoPostulacion.CONTRATADA);
            postulacion.setElegida(true);

            //postulacion = postulacionRepository.save(postulacion);
            return postulacion;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public void editPostulacion(PostulacionForm postulacionForm) {
        Postulacion postulacion = findById(postulacionForm.getId());
        if(postulacion!=null){
            postulacion.update(postulacionForm);
        }
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public void deletePostulacion(Postulacion postulacion) {
        postulacionRepository.delete(postulacion);
    }
}
