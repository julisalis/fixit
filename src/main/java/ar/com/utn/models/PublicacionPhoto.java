package ar.com.utn.models;

import javax.persistence.*;
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
    @Column(name = "order_view")
    private Integer orderInView;
    @Column(name = "is_deleted")
    private boolean deleted = false;
    @Column(name="file_name",nullable = false)
    private String fileName;
    @Column(name="content_type",nullable = false)
    private String contentType;
    private boolean cover;

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Integer getOrderInView() {
        return orderInView;
    }

    public void setOrderInView(Integer orderInView) {
        this.orderInView = orderInView;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }
}
