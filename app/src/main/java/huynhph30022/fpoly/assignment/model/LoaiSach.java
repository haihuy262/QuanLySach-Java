package huynhph30022.fpoly.assignment.model;

public class LoaiSach {
    private int id;
    private String tenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(int id, String tenLoaiSach) {
        this.id = id;
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
