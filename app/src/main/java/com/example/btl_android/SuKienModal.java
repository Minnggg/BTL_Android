package com.example.btl_android;

public class SuKienModal {
    int ID_SuKien;
    String tenSuKien;
    int gio;
    int phut;
    public String getTenSuKien() {
        return tenSuKien;
    }

    public void setTenSuKien(String tenSuKien) {
        this.tenSuKien = tenSuKien;
    }

    public SuKienModal(String tenSuKien, int gio, int phut) {
        this.tenSuKien = tenSuKien;
        this.gio = gio;
        this.phut = phut;
    }
}
