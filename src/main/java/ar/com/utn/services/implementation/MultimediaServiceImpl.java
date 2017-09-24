package ar.com.utn.services.implementation;

import java.io.File;
import java.io.IOException;

import ar.com.utn.models.PublicacionMultimedia;
import ar.com.utn.models.PublicacionPhoto;
import ar.com.utn.services.MultimediaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by iaruedel on 24/09/17.
 */
@Service
public class MultimediaServiceImpl implements MultimediaService {

    @Value("${app.file-system.image.root}")
    private String imageRoot;

    @Override
    @Transactional
    public void saveEcommerceImage(PublicacionPhoto publicacionPhoto, MultipartFile image, PublicacionMultimedia multimedia) throws IllegalStateException, IOException{

        File folder = publicacionPhoto.getDirectory(imageRoot, multimedia.getFolder());
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file = publicacionPhoto.getFile(imageRoot, multimedia.getFolder());
        image.transferTo(file);
    }
}
