package huynhph30022.fpoly.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.database.DbHelper;
import huynhph30022.fpoly.assignment.model.ThanhVien;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDanhSachThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThanhVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaThanhVien(cursor.getInt(cursor.getColumnIndexOrThrow("MaTV")));
                thanhVien.setHoTenThanhVien(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTV")));
                thanhVien.setNamSinhThanhVien(cursor.getString(cursor.getColumnIndexOrThrow("NamSinhTV")));
                list.add(thanhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean addThanhVien(String hoTen, String namSinh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTenTV", hoTen);
        values.put("NamSinhTV", namSinh);
        long check = db.insert("ThanhVien", null, values);
        return check != -1;
    }

    public boolean updateThanhVien(int maTV, String hoTenTV, String namSinhTV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTenTV", hoTenTV);
        values.put("NamSinhTV", namSinhTV);
        int check = db.update("ThanhVien", values, "MaTv =?", new String[]{String.valueOf(maTV)});
        return check != -1;
    }

    public int deleteThanhVien(int maTV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE MaTV =?", new String[]{String.valueOf(maTV)});
        if (cursor.getCount() != 0) {
            return -1; // Thành viên đang có phiếu mượn
        }
        int check = db.delete("ThanhVien", "MaTV =?", new String[]{String.valueOf(maTV)});
        if (check == -1)
            return 0; // Xoá thất bại
        return 1; // Xoá thành công
    }
}
