package ar.com.utn.services;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.PublicacionMultimedia;
import ar.com.utn.models.PublicacionPhoto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by iaruedel on 24/09/17.
 */
public interface MultimediaService {

    @Transactional
    void saveEcommerceImage(PublicacionPhoto publicacionPhoto, MultipartFile image) throws IllegalStateException, IOException;

    PublicacionFotoForm findEcommerceImage(long imageId) throws IOException;
}
