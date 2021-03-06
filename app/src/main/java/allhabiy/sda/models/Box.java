package allhabiy.sda.models;

import java.io.Serializable;


public class Box implements Serializable {

    private String name;
    private String status;
    private String latitude;
    private String longitude;

    public Box(String name, String status, String latitude, String longitude) {
        this.name = name;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
