package Entity;

import java.io.Serializable;

/**
 * Created by kim on 2017. 12. 25..
 */

public class Member implements Serializable {

    private static final long serialVersionUID = -5373157748362802770L;

    private Long id;
    private String pwd;
    private String email;
    private int sex;
    private String nickName;
    private String deviceId;
    private int memberType;

    public static Member builder() {
        return new Member();
    }

    public Member setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public Member setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public Member setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public Member setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Member setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public int getMemberType() {
        return memberType;
    }

    public Member setMemberType(int memberType) {
        this.memberType = memberType;
        return this;
    }
}
