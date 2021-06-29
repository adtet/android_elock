package com.example.e_lock;

public class postLaporan {
    private String plat_nomor;
    private String laporan;
    private String latitude;
    private String longitude;
    private String message;

    public postLaporan(String plat_nomor,String laporan,String latitude,String longitude){
        this.plat_nomor = plat_nomor;
        this.laporan = laporan;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getMessage() {
        return message;
    }
}
