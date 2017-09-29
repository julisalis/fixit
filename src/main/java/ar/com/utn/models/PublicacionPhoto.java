package ar.com.utn.models;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

/**
 * Created by iaruedel on 16/08/17.
 */
@Entity
@Table(name="publicacion_photo")
public class PublicacionPhoto extends PersistentEntity {

    @ManyToOne
    @JoinColumn(name = "publicacion_fk", nullable = false)
    private Publicacion publicacion;
    @Column(name = "is_deleted")
    private boolean deleted = false;
    private String extension;
    private LocalDateTime uploadedDate;
    private boolean cover;

    public PublicacionPhoto() {
    }

    public PublicacionPhoto(Publicacion publicacion, boolean deleted, String extension, boolean cover) {
        this.publicacion = publicacion;
        this.deleted = deleted;
        this.extension = extension;
        this.uploadedDate = LocalDateTime.now();
        this.cover = cover;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName(){
        return getId().toString()+"."+getExtension();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public File getDirectory(String imageRoot) {
        return new File(buildDirectory(imageRoot));
    }
    private String buildDirectory(String imageRoot){
        return imageRoot+this.publicacion.getMultimedia().getFolder()+'/'+this.publicacion.getId();
    }

    public File getFile(String imageRoot){
        File destination = new File(buildDirectory(imageRoot), getName());
        return destination;
    }
}
