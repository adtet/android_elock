package com.example.e_lock;

public class postdaftar {
    private String plat_nomor;
    private String nik;
    private String nama;
    private String password;
    private String jenis_kendaraan;
    private String message;

    public postdaftar(String plat_nomor,String nik,String nama,String password,String jenis_kendaraan){
        this.plat_nomor = plat_nomor;
        this.nik = nik;
        this.nama = nama;
        this.password = password;
        this.jenis_kendaraan =jenis_kendaraan;
    }

    public String getMessage() {
        return message;
    }
}
