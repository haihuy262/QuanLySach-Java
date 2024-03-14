package huynhph30022.fpoly.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.database.DbHelper;
import huynhph30022.fpoly.assignment.model.Sach;

public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(" SELECT SC.MaSach, SC.TenSach, SC.GiaThueSach, SC.MaLoai, LS.TenLoai FROM Sach SC, LoaiSach LS WHERE SC.MaLoai = LS.MaLoai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaSach")));
                sach.setMaLoaiSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                sach.setGiaThue(cursor.getInt(cursor.getColumnIndexOrThrow("GiaThueSach")));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow("TenSach")));
                sach.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean addSach(String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSach", tenSach);
        values.put("GiaThueSach", giaThue);
        values.put("MaLoai", maLoai);
        long check = db.insert("Sach", null, values);
        return check != -1;
    }

    public boolean updateSach(int maSach, String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSach", tenSach);
        values.put("GiaThueSach", giaThue);
        values.put("MaLoai", maLoai);
        int check = db.update("Sach", values, "MaSach =?", new String[]{String.valueOf(maSach)});
        return check != -1;
    }

    public int deleteSach(int maSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE MaSach =?", new String[]{String.valueOf(maSach)});
        if (cursor.getCount() != 0) {
            return -1; // Không được xoá vì sách có trong phiếu mượn
        }
        int check = db.delete("Sach", "MaSach =?", new String[]{String.valueOf(maSach)});
        if (check == -1)
            return 0; // xoá thất bại
        return 1; // xoá thành công
    }
}
