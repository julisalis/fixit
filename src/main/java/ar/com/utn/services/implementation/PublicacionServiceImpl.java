package ar.com.utn.services.implementation;

import ar.com.utn.exception.PublicacionException;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.*;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * Created by iaruedel on 13/08/17.
 */
@Service
public class PublicacionServiceImpl implements PublicacionService {
    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;
    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private PublicacionPhotoRepository publicacionPhotoRepository;

    @Override
    public List<TipoTrabajo> getTipostrabajos() {
        return tipoTrabajoRepository.findAll();
    }

    @Override
    @Transactional
    public void createPublicacion(PublicacionForm publicacionForm) {
        Localidad localidad=localidadRepository.findOne(publicacionForm.getLocalidad());
        Usuario userlogged = currentSession.getUser();
        if(userlogged!=null) {
            Tomador tomador = userlogged.getTomador();
            Publicacion publicacion = new Publicacion(publicacionForm.getCurrencyCode(),publicacionForm.getTitulo(),publicacionForm.getDescripcion(),publicacionForm.getPresupMax(),
                    publicacionForm.getTipoTrabajo(),localidad,publicacionForm.getTiempoPublicacion(),
                    tomador,null,publicacionForm.getFecha(),publicacionForm.getUrgencia());
            publicacionRepository.save(publicacion);
        }
    }

    @Override
    public Integer countPublicaciones(TipoTrabajo tipoTrabajo) {
        return publicacionRepository.countByTipoTrabajo(tipoTrabajo);
    }

    @Override
    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }

    @Override
    public Publicacion findById(Long publicacionId) {
        return publicacionRepository.findOne(publicacionId);
    }

    @Override
    public TipoTrabajo findTipoTrabajoBySlug(String slug) {
        return tipoTrabajoRepository.findBySlug(slug);
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public void saveImage(MultipartFile file,long publicacionId) throws PublicacionException, IllegalStateException{
        Publicacion publicacion = publicacionRepository.findOne(publicacionId);
        if(publicacion.getMultimedia().getPhotos().size() > 4){
            throw new PublicacionException("El producto ha alcanzado el limite de imagenes");
        }
        String extension = file.getContentType().split("/")[1];
        if(publicacion.getMultimedia().getPhotos().stream().anyMatch(publicacionPhoto -> publicacionPhoto.isCover())){
           publicacionPhotoRepository.save(new PublicacionPhoto(publicacion,false,"hola",extension,true));
        }else{
            publicacionPhotoRepository.save(new PublicacionPhoto(publicacion,false,"hola",extension,false));
        }
    }

}
