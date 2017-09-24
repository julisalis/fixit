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
    private String folder;
    private String name;
    private long id;

    public PublicacionFotoForm() {
    }

    public PublicacionFotoForm(PublicacionPhoto publicacionPhoto, String folder) {
        this.deleted = publicacionPhoto.isDeleted();
        this.folder = folder;
        this.name = publicacionPhoto.getName();
        this.id = publicacionPhoto.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
