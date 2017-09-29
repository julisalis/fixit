package ar.com.utn.form;

import ar.com.utn.models.PublicacionPhoto;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by iaruedel on 13/09/17.
 */
public class PublicacionFotoForm {

    private long id;
    private byte[] content;
    private String extension;
    private String name;

    public PublicacionFotoForm() {
    }


    public PublicacionFotoForm(PublicacionPhoto publicacionPhoto, File file) throws IOException {
        this.id = publicacionPhoto.getId();
        this.name = publicacionPhoto.getName();
        this.extension = publicacionPhoto.getExtension();
        this.content = IOUtils.toByteArray( new FileInputStream( file ));
    }

    public PublicacionFotoForm(PublicacionPhoto publicacionPhoto) {
        this.id = publicacionPhoto.getId();
        this.name = publicacionPhoto.getName();
        this.extension = publicacionPhoto.getExtension();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
