package ar.com.utn.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by iaruedel on 16/08/17.
 */
@Embeddable
@Access(AccessType.FIELD)
public class PublicacionMultimedia {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacion")
    private List<PublicacionPhoto> photos = new ArrayList<PublicacionPhoto>();

    @Column(name = "multimedia_folder", unique = true)
    private String folder;

    protected PublicacionMultimedia() {
    }

    public PublicacionMultimedia(String folder) {
        this.folder = folder;
    }
}
