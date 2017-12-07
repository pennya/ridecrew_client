package Entity;

import java.io.Serializable;

/**
 * Created by kim on 2017. 12. 8..
 */

public class User implements Serializable {

    private static final long serialVersionUID = 3274785991356899261L;

    private long id;
    private String loginId;
    private String pwd;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
