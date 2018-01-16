package Entity;

/**
 * Created by KIM on 2018-01-16.
 */

public class Gallery {
    private Long id;
    private Member member;
    private String title;
    private String content;
    private String imageUrl;
    private String createdDateTime;
    private String lastModifiedDateTime;

    public static Gallery builder() {
        return new Gallery();
    }

    public Member getMember() {
        return member;
    }

    public Gallery setMember(Member member) {
        this.member = member;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Gallery setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Gallery setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Gallery setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }
}
