package ar.com.utn.form;

import ar.com.utn.models.PublicacionPhoto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iaruedel on 13/09/17.
 */
public class PublicacionFotoForm {

    private boolean deleted;
    private boolean cover;
    private String folder;
    private String name;

    public PublicacionFotoForm() {
    }

    public PublicacionFotoForm(PublicacionPhoto publicacionPhoto, String folder) {
        this.cover= publicacionPhoto.isCover();
        this.deleted = publicacionPhoto.isDeleted();
        this.folder = folder;
        this.name = publicacionPhoto.getFileName();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
