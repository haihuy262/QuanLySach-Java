package huynhph30022.fpoly.assignment.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.dao.PhieuMuonDAO;
import huynhph30022.fpoly.assignment.adapter.AdapterPhieuMuon;
import huynhph30022.fpoly.assignment.dao.SachDAO;
import huynhph30022.fpoly.assignment.dao.ThanhVienDAO;
import huynhph30022.fpoly.assignment.model.PhieuMuon;
import huynhph30022.fpoly.assignment.model.Sach;
import huynhph30022.fpoly.assignment.model.ThanhVien;

public class QuanLyPhieuMuonFragment extends Fragment {
    ArrayList<PhieuMuon> list;
    PhieuMuonDAO phieuMuonDAO;
    AdapterPhieuMuon adapter;
    RecyclerView recyclerView_qlpm;
    FloatingActionButton fab_qlpm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_phieu_muon, container, false);
        recyclerView_qlpm = view.findViewById(R.id.recyclerView_quan_ly_phieu_muon);
        fab_qlpm = view.findViewById(R.id.fab_quan_ly_phieu_muon);

        phieuMuonDAO = new PhieuMuonDAO(requireContext());
        list = new ArrayList<>();
        adapter = new AdapterPhieuMuon(requireContext());

        loadData();
        fab_qlpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = LayoutInflater.from(requireContext());
                View view = inflater.inflate(R.layout.dialog_them_phieu_muon, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

                Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
                getDataThanhVien(spnThanhVien);
                Spinner spnSach = view.findViewById(R.id.spnSach);
                getDataSach(spnSach);
                AppCompatButton btnThem = view.findViewById(R.id.btnThemPhieuMuon);
                AppCompatButton btnHuy = view.findViewById(R.id.btnHuyPhieuMuon);

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMapThanhVien = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                        int maThanhVien = (int) hashMapThanhVien.get("MaTV");

                        HashMap<String, Object> hashMapSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        int maSach = (int) hashMapSach.get("MaSach");

                        int tien = (int) hashMapSach.get("GiaThueSach");

                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                        String maThuThu = sharedPreferences.getString("MaTT", "");

                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
                        String ngay = dateFormat.format(date);

                        PhieuMuon phieuMuon = new PhieuMuon();
                        phieuMuon.setMaThanhVien(maThanhVien);
                        phieuMuon.setMaSach(maSach);
                        phieuMuon.setNgay(ngay);
                        phieuMuon.setMaThuThu(maThuThu);
                        phieuMuon.setTraSach(0);
                        phieuMuon.setTienThue(tien);
                        boolean check = phieuMuonDAO.themPhieuMuon(phieuMuon);
                        if (check) {
                            Toast.makeText(requireContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(requireContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView_qlpm.setLayoutManager(layoutManager);
        list = phieuMuonDAO.getDanhSachPhieuMuon();
        adapter.setData(list);
        recyclerView_qlpm.setAdapter(adapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDAO sachDAO = new SachDAO(requireContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (Sach sach : list) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("MaSach", sach.getMaSach());
            hashMap.put("TenSach", sach.getTenSach());
            hashMap.put("GiaThueSach", sach.getGiaThue());
            listHashMap.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(requireContext(), listHashMap, android.R.layout.simple_list_item_1, new String[]{"TenSach"}, new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(requireContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getDanhSachThanhVien();
        ArrayList<HashMap<String, Object>> listHashMap = new ArrayList<>();
        for (ThanhVien thanhVien : list) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("MaTV", thanhVien.getMaThanhVien());
            hashMap.put("HoTenTV", thanhVien.getHoTenThanhVien());
            listHashMap.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(requireContext(), listHashMap, android.R.layout.simple_list_item_1, new String[]{"HoTenTV"}, new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }
}