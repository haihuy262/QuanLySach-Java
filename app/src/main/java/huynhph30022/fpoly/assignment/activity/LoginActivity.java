package huynhph30022.fpoly.assignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edUserName = findViewById(R.id.edUserName);
        EditText edPassword = findViewById(R.id.edPassword);
        Button btnLogin = findViewById(R.id.button_login);
        CheckBox cboLuuTaiKhoan = findViewById(R.id.cboLuuTaiKhoan);

        ThuThuDAO thuThuDAO = new ThuThuDAO(LoginActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", MODE_PRIVATE);
        boolean isAccountSaved = sharedPreferences.getBoolean("IsAccountSaved", false);
        String saveUserName = sharedPreferences.getString("MaTT", null);
        String savePassword = sharedPreferences.getString("MatKhauTT", null);

        cboLuuTaiKhoan.setChecked(isAccountSaved);
        if (isAccountSaved && saveUserName != null && savePassword != null) {
            edUserName.setText(saveUserName);
            edPassword.setText(savePassword);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUserName.getText().toString().trim();
                String pass = edPassword.getText().toString().trim();

                if (thuThuDAO.checkLogin(user, pass)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (cboLuuTaiKhoan.isChecked()) {
                        editor.putBoolean("IsAccountSaved", true);
                        editor.putString("MaTT", user);
                        editor.putString("MatKhauTT", pass);
                    } else {
                        editor.putBoolean("IsAccountSaved", false);
                        editor.remove("MaTT");
                        editor.remove("MatKhauTT");
                    }
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}