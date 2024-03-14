package huynhph30022.fpoly.assignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.fragment.DoanhThuFragment;
import huynhph30022.fpoly.assignment.fragment.DoiMatKhauFragment;
import huynhph30022.fpoly.assignment.fragment.QuanLyLoaiSachFragment;
import huynhph30022.fpoly.assignment.fragment.QuanLyPhieuMuonFragment;
import huynhph30022.fpoly.assignment.fragment.QuanLySachFragment;
import huynhph30022.fpoly.assignment.fragment.QuanLyThanhVienFragment;
import huynhph30022.fpoly.assignment.fragment.TaoTaiKhoanFragment;
import huynhph30022.fpoly.assignment.fragment.Top10SachMuonNhieuNhatFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int FRAGMENT_QUAN_LY_PHIEU_MUON = 0;
    public static final int FRAGMENT_QUAN_LY_LOAI_SACH = 1;
    public static final int FRAGMENT_QUAN_LY_SACH = 2;
    public static final int FRAGMENT_QUAN_LY_THANH_VIEN = 3;
    public static final int FRAGMENT_TOP10_SACH_MUON_NHIEU_NHAT = 4;
    public static final int FRAGMENT_DOANH_THU = 5;
    public static final int FRAGMENT_TAO_TAI_KHOAN = 6;
    public static final int FRAGMENT_DOI_MAT_KHAU = 7;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private int currentFragment = FRAGMENT_QUAN_LY_PHIEU_MUON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý phiếu mượn");
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        View headerLayout = navigationView.getHeaderView(0); // Lấy text trên headerLayout
        TextView tvHeader = headerLayout.findViewById(R.id.tvNameHeader); // ánh xạ đến nó

        replaceFragment(new QuanLyPhieuMuonFragment());
        navigationView.getMenu().findItem(R.id.navQuanLyPhieuMuon).setChecked(true);

        // Phân loại người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaiTaiKhoan = sharedPreferences.getString("loaiTaiKhoan", null);
        if (!loaiTaiKhoan.equals("Admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.navDoanhThu).setVisible(false);
            menu.findItem(R.id.navTop10DanhSachMuonNhieuNhat).setVisible(false);
            menu.findItem(R.id.navTaoTaiKhoan).setVisible(false);
        }
        String hoTen = sharedPreferences.getString("HoTenTT", null);
        tvHeader.setText(hoTen);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navQuanLyPhieuMuon) {
            if (currentFragment != FRAGMENT_QUAN_LY_PHIEU_MUON) {
                replaceFragment(new QuanLyPhieuMuonFragment());
                currentFragment = FRAGMENT_QUAN_LY_PHIEU_MUON;
            }
        } else if (id == R.id.navQuanLyLoaiSach) {
            if (currentFragment != FRAGMENT_QUAN_LY_LOAI_SACH) {
                replaceFragment(new QuanLyLoaiSachFragment());
                currentFragment = FRAGMENT_QUAN_LY_LOAI_SACH;
            }
        } else if (id == R.id.navQuanLySach) {
            if (currentFragment != FRAGMENT_QUAN_LY_SACH) {
                replaceFragment(new QuanLySachFragment());
                currentFragment = FRAGMENT_QUAN_LY_SACH;
            }
        } else if (id == R.id.navQuanLyThanhVien) {
            if (currentFragment != FRAGMENT_QUAN_LY_THANH_VIEN) {
                replaceFragment(new QuanLyThanhVienFragment());
                currentFragment = FRAGMENT_QUAN_LY_THANH_VIEN;
            }
        } else if (id == R.id.navTop10DanhSachMuonNhieuNhat) {
            if (currentFragment != FRAGMENT_TOP10_SACH_MUON_NHIEU_NHAT) {
                replaceFragment(new Top10SachMuonNhieuNhatFragment());
                currentFragment = FRAGMENT_TOP10_SACH_MUON_NHIEU_NHAT;
            }
        } else if (id == R.id.navDoanhThu) {
            if (currentFragment != FRAGMENT_DOANH_THU) {
                replaceFragment(new DoanhThuFragment());
                currentFragment = FRAGMENT_DOANH_THU;
            }
        } else if (id == R.id.navTaoTaiKhoan) {
            if (currentFragment != FRAGMENT_TAO_TAI_KHOAN) {
                replaceFragment(new TaoTaiKhoanFragment());
                currentFragment = FRAGMENT_TAO_TAI_KHOAN;
            }
        } else if (id == R.id.navDoiMatKhau) {
            if (currentFragment != FRAGMENT_DOI_MAT_KHAU) {
                replaceFragment(new DoiMatKhauFragment());
                currentFragment = FRAGMENT_DOI_MAT_KHAU;
            }
        } else if (id == R.id.navDangXuat) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setTitle(item.getTitle().toString());
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }
}