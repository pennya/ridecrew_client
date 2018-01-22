package Entity;

import java.io.Serializable;

/**
 * Created by KJH on 2017-09-06.
 */

public class LocationInfo implements Serializable{

    private static final long serialVersionUID = 5340329595934350485L;

    private String title;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isRoundSearch;

    public LocationInfo() {
        title = "";
        address = "";
        latitude = 0.0;
        longitude = 0.0;
        isRoundSearch = false;
    }

    public LocationInfo build() {
        /* 다른 초기화 작업이 있다면 여기서*/
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LocationInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public LocationInfo setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocationInfo setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public boolean isRoundSearch() {
        return isRoundSearch;
    }

    public LocationInfo setRoundSearch(boolean roundSearch) {
        isRoundSearch = roundSearch;
        return this;
    }
}
