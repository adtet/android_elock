package com.example.e_lock;

public class postlogin {
    private String nik;
    private String password;
    private String message;
    private String pesan;
    private String Lat;
    private String Lng;
    private String plat_nomor;


    public postlogin(String nik,String password){
        this.nik = nik;
        this.password = password;
    }


    public String getMessage() {
        return message;
    }

    public String getPesan() {
        return pesan;
    }

    public String getLat() {
        return Lat;
    }

    public String getLng() {
        return Lng;
    }

    public String getPlat_nomor() {
        return plat_nomor;
    }
}
