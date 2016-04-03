package allhabiy.sda.models;

import java.io.Serializable;


public class User implements Serializable {

    private String name;
    private String phone;
    private String latitude;
    private String longitude;
    private int family_member;
    private int income;

    public User(String name, String phone, String latitude, String longitude, int family_member, int income) {
        this.name = name;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.family_member = family_member;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getFamily_member() {
        return family_member;
    }

    public void setFamily_member(int family_member) {
        this.family_member = family_member;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
