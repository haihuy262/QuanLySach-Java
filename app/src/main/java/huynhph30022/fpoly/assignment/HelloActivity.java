package huynhph30022.fpoly.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import huynhph30022.fpoly.assignment.activity.LoginActivity;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        // Sử dụng thư viện Glide chạy ảnh GIF https://github.com/bumptech/glide.git
        Glide.with(HelloActivity.this).load(R.mipmap.giphy).into(imgLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelloActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}