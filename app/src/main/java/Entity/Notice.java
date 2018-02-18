package Entity;

import java.io.Serializable;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class Notice implements Serializable {
    private static final long serialVersionUID = -8890958564840273255L;

    private Long id;
    private int type;
    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;
    private String createdDateTime;
    private String lastModifiedDateTime;

    public static Notice builder() { return new Notice(); }

    public Long getId() {
        return id;
    }

    public Notice setId(Long id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public Notice setType(int type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Notice setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Notice setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getCreatedDateTime() {
        return createdDateTime.substring(0,10);
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }
}
