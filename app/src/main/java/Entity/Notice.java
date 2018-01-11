package Entity;

import java.io.Serializable;

/**
 * Created by JooHyeong on 2018. 1. 11..
 */

public class Notice implements Serializable {
    private static final long serialVersionUID = -8890958564840273255L;

    private String title;
    private String contents;

    public static Notice builder() {
        return new Notice();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {

        return title;
    }

    public String getContents() {
        return contents;
    }
}
