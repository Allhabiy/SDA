package allhabiy.sda.models;

import java.io.Serializable;


public class DonationDistribute implements Serializable {

    private String name;
    private String priority1;
    private String priority2;
    private String priority3;
    private String latitude;
    private String longitude;

    public DonationDistribute(String name, String priority1, String priority2, String priority3, String latitude, String longitude) {
        this.name = name;
        this.priority1 = priority1;
        this.priority2 = priority2;
        this.priority3 = priority3;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority1() {
        return priority1;
    }

    public void setPriority1(String priority1) {
        this.priority1 = priority1;
    }

    public String getPriority2() {
        return priority2;
    }

    public void setPriority2(String priority2) {
        this.priority2 = priority2;
    }

    public String getPriority3() {
        return priority3;
    }

    public void setPriority3(String priority3) {
        this.priority3 = priority3;
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
