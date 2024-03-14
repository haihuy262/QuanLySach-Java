package huynhph30022.fpoly.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.database.DbHelper;
import huynhph30022.fpoly.assignment.model.LoaiSach;

public class LoaiSachDAO {
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiSach> getDanhSachLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LoaiSach", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean addLoaiSach(String themLoaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai", themLoaiSach);
        long check = db.insert("LoaiSach", null, values);
        return check != -1;
    }

    public int deleteLoaiSach(int idLoaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sach WHERE MaLoai =?", new String[]{String.valueOf(idLoaiSach)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int check = db.delete("LoaiSach", "MaLoai =?", new String[]{String.valueOf(idLoaiSach)});
        if (check == -1)
            return 0;
        return 1;
    }

    public boolean editLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSach.getTenLoaiSach());
        int check = db.update("LoaiSach", values, "MaLoai =?", new String[]{String.valueOf(loaiSach.getId())});
        return check != -1;
    }
}
