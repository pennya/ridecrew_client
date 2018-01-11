package Entity;

import java.io.Serializable;

/**
 * Created by KJH on 2018-01-02.
 */

public class ScheduleDefaultEntitiy implements Serializable {

    private static final long serialVersionUID = 884527032694437967L;

    public ScheduleDefaultEntitiy()
    {
    }

    private Member member;
    private String date;
    private String dayOfWeek;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
