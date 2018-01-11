package Entity;

import java.io.Serializable;

/**
 * Created by kim on 2018. 1. 11..
 */

public class ScheduleMember implements Serializable {

    private static final long serialVersionUID = 2747860731378875866L;

    private Long id;
    private Member member;
    private Schedule schedule;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
