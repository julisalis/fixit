package ar.com.utn.services.implementation;

import java.io.File;
import java.io.IOException;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.PublicacionMultimedia;
import ar.com.utn.models.PublicacionPhoto;
import ar.com.utn.repositories.PublicacionPhotoRepository;
import ar.com.utn.repositories.PublicacionRepository;
import ar.com.utn.services.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PublicacionPhotoRepository publicacionPhotoRepository;

    @Override
    @Transactional
    public void saveEcommerceImage(PublicacionPhoto publicacionPhoto, MultipartFile image) throws IllegalStateException, IOException{

        File folder = publicacionPhoto.getDirectory(imageRoot);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File file = publicacionPhoto.getFile(imageRoot);
        image.transferTo(file);
    }

    @Override
    public PublicacionFotoForm findEcommerceImage(Long imageId) throws IOException {
        if(imageId!=null){
            PublicacionPhoto publicacionPhoto = publicacionPhotoRepository.findOne(imageId);
            if(publicacionPhoto!=null){
                File file = publicacionPhoto.getFile(imageRoot);
                return new PublicacionFotoForm(publicacionPhoto, file);
            }else{
                return null;
            }
        }else{
            return null;
        }

    }
}
