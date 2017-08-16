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
    private Boolean deleted = false;
    @Column(nullable = false)
    private LocalDateTime creation;
    @Column(name="file_name",nullable = false)
    private String fileName;
    @Column(name="content_type",nullable = false)
    private String contentType;

    @PrePersist
    protected void onCreate() {
        this.creation = LocalDateTime.now();
    }
}
