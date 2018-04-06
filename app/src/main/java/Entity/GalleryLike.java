package Entity;

import java.io.Serializable;

/**
 * Created by kim on 2018. 4. 6..
 */

public class GalleryLike implements Serializable {
    private static final long serialVersionUID = -5191530079321831079L;
    private Long id;
    private Member member;
    private Gallery gallery;

    public static GalleryLike builder() {
        return new GalleryLike();
    }

    public Long getId() {
        return id;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public GalleryLike setGallery(Gallery gallery) {
        this.gallery = gallery;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public GalleryLike setMember(Member member) {
        this.member = member;
        return this;
    }
}
