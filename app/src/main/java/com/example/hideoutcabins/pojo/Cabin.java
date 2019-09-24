package com.example.hideoutcabins.pojo;

public class Cabin {
    String  Address;
    String  Email;
    String  Name;
    String  TP;
    String  pasword;
    Double Room_Single_Price;
    Double Location_lat;
    Double Location_lon;
    Double Room_Double_Price;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public Double getRoom_Single_Price() {
        return Room_Single_Price;
    }

    public void setRoom_Single_Price(Double room_Single_Price) {
        Room_Single_Price = room_Single_Price;
    }

    public Double getLocation_lat() {
        return Location_lat;
    }

    public void setLocation_lat(Double location_lat) {
        Location_lat = location_lat;
    }

    public Double getLocation_lon() {
        return Location_lon;
    }

    public void setLocation_lon(Double location_lon) {
        Location_lon = location_lon;
    }

    public Double getRoom_Double_Price() {
        return Room_Double_Price;
    }

    public void setRoom_Double_Price(Double room_Double_Price) {
        Room_Double_Price = room_Double_Price;
    }
}
