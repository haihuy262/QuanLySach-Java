package huynhph30022.fpoly.assignment.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.database.DbHelper;
import huynhph30022.fpoly.assignment.model.Sach;

public class ThongKeDAO {
    DbHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10MuonSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PM.MaSach, SC.TenSach, COUNT(PM.MaSach) FROM PhieuMuon PM, Sach SC WHERE PM.MaSach = SC.MaSach GROUP BY PM.MaSach, SC.TenSach ORDER BY COUNT(PM.MaSach) DESC LIMIT 10", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String ngayBatDau, String ngayKetThuc) {
        ngayBatDau = ngayBatDau.replace("/", "");
        ngayKetThuc = ngayKetThuc.replace("/", "");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(TienThue) FROM PhieuMuon WHERE substr(Ngay,7)||substr(Ngay,4,2)||substr(Ngay,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau, ngayKetThuc});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
