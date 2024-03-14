package huynhph30022.fpoly.assignment.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import huynhph30022.fpoly.assignment.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    public boolean checkLogin(String userName, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND MatKhauTT =?", new String[]{userName, password});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("MaTT", cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
            editor.putString("loaiTaiKhoan", cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
            editor.putString("HoTenTT", cursor.getString(cursor.getColumnIndexOrThrow("HoTenTT")));
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public boolean doiMatKhau(String userName, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND MatKhauTT =?", new String[]{userName, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("MatKhauTT", newPass);
            int check = db.update("ThuThu", values, "MaTT =?", new String[]{userName});
            if (check == -1)
                return false;
            return true;
        }
        return false;
    }
}
