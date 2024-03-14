package huynhph30022.fpoly.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "duanmau.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Loại Sách
        String createLoaiSachTable = "CREATE TABLE LoaiSach(MaLoai INTEGER PRIMARY KEY AUTOINCREMENT, TenLoai TEXT NOT NULL)";
        db.execSQL(createLoaiSachTable);

        // Tạo bảng Thành Viên
        String createThanhVienTable = "CREATE TABLE ThanhVien(MaTV INTEGER PRIMARY KEY AUTOINCREMENT, HoTenTV TEXT NOT NULL, NamSinhTV TEXT NOT NULL)";
        db.execSQL(createThanhVienTable);

        // Tạo bảng Thủ Thư
        String createThuThuTable = "CREATE TABLE ThuThu(MaTT TEXT PRIMARY KEY, MatKhauTT TEXT NOT NULL, HoTenTT TEXT NOT NULL, loaiTaiKhoan TEXT)";
        db.execSQL(createThuThuTable);

        // Tạo bảng Sách với khoá ngoại MaLoai tham chiếu đến bảng LoaiSach
        String createSachTable = "CREATE TABLE Sach(MaSach INTEGER PRIMARY KEY AUTOINCREMENT, MaLoai INTEGER NOT NULL, TenSach TEXT NOT NULL, GiaThueSach INTEGER NOT NULL, CONSTRAINT FK_MaLoai FOREIGN KEY (MaLoai) REFERENCES LoaiSach (MaLoai))";
        db.execSQL(createSachTable);

        // Tạo bảng Phiếu Mượn với các khoá ngoại MaTV, MaSach, MaTT tham chiếu đến bảng ThanhVien và ThuThu
        String createPhieuMuonTable = "CREATE TABLE PhieuMuon(MaPM INTEGER PRIMARY KEY AUTOINCREMENT, MaTV INTEGER NOT NULL, MaSach INTEGER NOT NULL, MaTT TEXT NOT NULL, Ngay TEXT NOT NULL, TienThue INTEGER NOT NULL, TraSach INTEGER NOT NULL, CONSTRAINT FK_MaSach FOREIGN KEY (MaSach) REFERENCES Sach (MaSach), CONSTRAINT FK_MaTT FOREIGN KEY (MaTT) REFERENCES ThuThu (MaTT), CONSTRAINT FK_MaTV FOREIGN KEY (MaTV) REFERENCES ThanhVien (MaTV))";
        db.execSQL(createPhieuMuonTable);

        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO Sach VALUES (1, 1,'Hãy đợi đấy', 2500), (2, 1, 'Thằng cuội', 1000), (3, 3, 'Lập trình Android', 2000)");
        db.execSQL("INSERT INTO ThuThu VALUES ('admin','admin','Nguyễn Hải Huy','Admin'),('thuthu02','admin','Trần Văn Mạnh','Thủ thư')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PhieuMuon VALUES (1, 1, 1,'admin', '24/05/2023',2500, 1 ),(2, 2, 3,'admin', '24/05/2023',2000, 0 )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}

