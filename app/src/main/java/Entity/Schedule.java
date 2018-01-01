package Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by kim on 2017. 12. 8..
 */

public class Schedule implements Serializable {

    private static final long serialVersionUID = -8890958564840273255L;

    private long id;
    private Member member;
    private String date;
    private String title;
    private String startPoint;
    private String endPoint;
    private String startTime;
    private String endTime;
    private String descriptions;
    private String startSpot;
    private String endSpot;
    private int status;
    private String createdDateTime;
    private String lastModifiedDateTime;

    public static Schedule builder() {
        return new Schedule();
    }

    public Schedule setId(long id) {
        this.id = id;
        return this;
    }

    public long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Schedule setMember(Member member) {
        this.member = member;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Schedule setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Schedule setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public Schedule setStartPoint(String startPoint) {
        this.startPoint = startPoint;
        return this;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public Schedule setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Schedule setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Schedule setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public Schedule setDescriptions(String descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    public String getStartSpot() {
        return startSpot;
    }

    public Schedule setStartSpot(String startSpot) {
        this.startSpot = startSpot;
        return this;
    }

    public String getEndSpot() {
        return endSpot;
    }

    public Schedule setEndSpot(String endSpot) {
        this.endSpot = endSpot;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Schedule setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public Schedule setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public Schedule setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
        return this;
    }
}
