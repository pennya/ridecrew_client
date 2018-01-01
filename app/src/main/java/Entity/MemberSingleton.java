package Entity;

/**
 * Created by KJH on 2018-01-01.
 */

public class MemberSingleton {

    private static MemberSingleton instance;
    private Member member;

    private MemberSingleton() {}

    public static MemberSingleton getInstance() {
        if (instance == null)
            instance = new MemberSingleton();
        return instance;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
