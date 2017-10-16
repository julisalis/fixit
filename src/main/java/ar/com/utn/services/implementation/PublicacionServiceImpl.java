package ar.com.utn.services.implementation;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.exception.PublicacionException;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.*;
import ar.com.utn.services.MultimediaService;
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
public class PublicacionServiceImpl implements PublicacionService, ar.com.utn.services.implementation.PublicacionService {
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
    @Autowired
    private MultimediaService multimediaService;

    @Override
    public List<TipoTrabajo> getTipostrabajos() {
        return tipoTrabajoRepository.findAll();
    }

    @Override
    @Transactional
    public Publicacion createPublicacion(PublicacionForm publicacionForm) {
        Localidad localidad=localidadRepository.findOne(publicacionForm.getLocalidad());
        Usuario userlogged = currentSession.getUser();
        Tomador tomador = userlogged.getTomador();
        Publicacion publicacion = new Publicacion(publicacionForm.getCurrencyCode(),publicacionForm.getTitulo(),publicacionForm.getDescripcion(),publicacionForm.getPresupMax(),
                publicacionForm.getTipoTrabajo(),localidad,publicacionForm.getTiempoPublicacion(),
                tomador,null,publicacionForm.getFecha(),publicacionForm.getUrgencia());
        publicacionRepository.save(publicacion);
        publicacion.setMultimedia(new PublicacionMultimedia("/"+userlogged.getUsername()+"/"+publicacion.getId()+"/"));

        return publicacion;
    }

    @Override
    public Integer countPublicaciones(TipoTrabajo tipoTrabajo) {
        return publicacionRepository.countByTipoTrabajoAndEstadoPublicacionEquals(tipoTrabajo, EstadoPublicacion.NUEVA);
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
    public void saveImage(MultipartFile file,long publicacionId) throws PublicacionException, IllegalStateException, IOException {
        Publicacion publicacion = publicacionRepository.findOne(publicacionId);
        PublicacionPhoto publicacionPhoto;
        if(publicacion.getMultimedia().getPhotos().size() > 4){
            throw new PublicacionException("El producto ha alcanzado el limite de imagenes");
        }
        String extension = file.getContentType().split("/")[1];
        if(publicacion.getMultimedia().getPhotos()==null || publicacion.getMultimedia().getPhotos().stream().allMatch(foto -> !foto.isCover())){
           publicacionPhoto = publicacionPhotoRepository.save(new PublicacionPhoto(publicacion,false,extension,true));
        }else{
            publicacionPhoto =publicacionPhotoRepository.save(new PublicacionPhoto(publicacion,false,extension,false));
        }
        publicacion.getMultimedia().getPhotos().add(publicacionPhoto);
        multimediaService.saveEcommerceImage(publicacionPhoto, file);
        publicacion.setEstadoPublicacion(EstadoPublicacion.NUEVA);
    }

    public List<Publicacion> findAllByEstadoPublicacionEquals(EstadoPublicacion estadoPublicacion) {
        return publicacionRepository.findAllByEstadoPublicacionEquals(estadoPublicacion);
    }

    @Override
    public PublicacionPhoto getCover(Publicacion publicacion) {
        return publicacionPhotoRepository.findByPublicacionAndCover(publicacion,true);
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public void editPublicacion(PublicacionForm publicacionForm, Publicacion publicacion, Long primaryImage) {
        Localidad localidad = localidadRepository.findOne(publicacionForm.getLocalidad());
        publicacion.update(publicacionForm,localidad);
        publicacion.getMultimedia().getPhotos().forEach(publicacionPhoto -> publicacionPhoto.setCover(false));
        publicacionPhotoRepository.findOne(primaryImage).setCover(true);
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public void deletePublicacion(Publicacion publicacion) {
        publicacion.setEstadoPublicacion(EstadoPublicacion.ELIMINADA);
    }

    @Override
    @Transactional(rollbackOn={Exception.class})
    public Publicacion setContratada(Publicacion publicacion) {
        publicacion.setEstadoPublicacion(EstadoPublicacion.CONTRATADA);
        //publicacion = publicacionRepository.save(publicacion);
        return publicacion;
    }

    @Override
    public List<Publicacion> getTrabajosRecomendados(List<TipoTrabajo> tipos) {
        return publicacionRepository.findAllByTipoTrabajoInAndAndEstadoPublicacionEquals(tipos, EstadoPublicacion.NUEVA);
    }
}
