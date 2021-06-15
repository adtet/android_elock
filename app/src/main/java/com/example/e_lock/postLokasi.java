package com.example.e_lock;

public class postLokasi {
    private String plat_nomor;
    private String lat;
    private String lng;
    private String message;

    public postLokasi(String plat_nomor,String lat,String lng){
        this.plat_nomor = plat_nomor;
        this.lat = lat;
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }
}
