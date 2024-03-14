package huynhph30022.fpoly.assignment.model;

public class Sach {
    private int maSach;
    private int maLoaiSach;
    private String tenSach;
    private int giaThue;
    private int soLuongSachMuon;

    private String tenLoai;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int soLuongSachMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongSachMuon = soLuongSachMuon;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getSoLuongSachMuon() {
        return soLuongSachMuon;
    }

    public void setSoLuongSachMuon(int soLuongSachMuon) {
        this.soLuongSachMuon = soLuongSachMuon;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
