package com.example.btl_android;

public class NhiemVuModel {
    String TenNhiemVu;
    String GhiChu;
    int ID_NhiemVu;
    int hstart;
    int mstart;
    int hend;
    int mend;


    public String getTenNhiemVu() {
        return TenNhiemVu;
    }

    public void setTenNhiemVu(String tenNhiemVu) {
        TenNhiemVu = tenNhiemVu;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public NhiemVuModel(String tenNhiemVu, String ghiChu, int hstart, int mstart, int hend, int mend) {
        TenNhiemVu = tenNhiemVu;
        GhiChu = ghiChu;
        this.hstart = hstart;
        this.mstart = mstart;
        this.hend = hend;
        this.mend = mend;
    }
}
