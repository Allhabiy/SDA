package allhabiy.sda.models;

import java.io.Serializable;


public class DonationCollection implements Serializable {

    private String name;
    private String date;
    private String time;
    private String type;
    private String latitude;
    private String longitude;

    public DonationCollection(String name, String date, String time, String type, String latitude, String longitude) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
