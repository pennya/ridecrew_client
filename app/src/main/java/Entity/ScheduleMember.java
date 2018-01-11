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

    public static ScheduleMember builder() {
        return new ScheduleMember();
    }

    public Member getMember() {
        return member;
    }

    public ScheduleMember setMember(Member member) {
        this.member = member;
        return this;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public ScheduleMember setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }
}
