package Entity;

/**
 * Created by KIM on 2018-01-16.
 */

public class GalleryPicture {
    private Long id;
    private Gallery gallery;
    private String imageUrl;
    private int sort;

    public GalleryPicture(Gallery gallery, String imageUrl, int sort) {
        this.gallery = gallery;
        this.imageUrl = imageUrl;
        this.sort = sort;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }
}
